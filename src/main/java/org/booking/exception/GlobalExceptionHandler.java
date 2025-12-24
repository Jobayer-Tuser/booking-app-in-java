package org.booking.exception;

import org.booking.payment.PaymentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException exception)
    {
        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(
                        fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage())
                );
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ResourcesNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourcesNotFoundException(ResourcesNotFoundException exception) {
        return buildResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<ErrorResponse> handlePaymentGatewayException(PaymentException exception) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    // 3. Catch-all for unexpected server errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus httpStatus, String exception) {
        var error = new ErrorResponse(
                httpStatus.value(),
                exception,
                LocalDateTime.now()
        );
        return ResponseEntity.status(httpStatus).body(error);
    }

}
