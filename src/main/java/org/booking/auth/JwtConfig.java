package org.booking.auth;

import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.jwt")
public class JwtConfig {

    private String secretKey;
    private int accessTokenExpiration;
    private int refreshTokenExpiration;

    public SecretKey encryptSecretKey()
    {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}
