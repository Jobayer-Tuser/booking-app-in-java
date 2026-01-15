package org.booking.Exceptions;

import java.time.LocalDateTime;

public record ErrorResponse(int status, String message, LocalDateTime timeStamp) {
}
