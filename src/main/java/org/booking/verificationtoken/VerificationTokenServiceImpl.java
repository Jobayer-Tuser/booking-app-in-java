package org.booking.verificationtoken;

import lombok.RequiredArgsConstructor;
import org.booking.jwt.Jwt;
import org.booking.jwt.JwtService;
import org.booking.enums.VerificationType;
import org.booking.exceptions.ResourcesNotFoundException;
import org.booking.users.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VerificationTokenServiceImpl implements VerificationTokenService{

    private final JwtService jwtService;
    private final VerificationTokenRepository tokenRepository;

    @Override
    public void addVerificationToken(User user, String JwtToken) {

        var token = new VerificationToken();
        token.setUser(user);
        token.setType(VerificationType.EMAIL_VERIFICATION.name());
        token.setToken(JwtToken);
        tokenRepository.save(token);
    }

    @Override
    public Long updateVerificationTokenStatus(String token) {
        Jwt parseToken = jwtService.parseToken(token);

        VerificationToken confirmationToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new ResourcesNotFoundException("Token not found"));

        if (confirmationToken.getVerifiedAt() != null) {
            throw new IllegalArgumentException("Token already verified!");
        }

        if (confirmationToken.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("This Token is expired please request for new token!");
        }

        confirmationToken.setVerifiedAt(LocalDateTime.now());

        return confirmationToken.getUser().getId();
    }
}
