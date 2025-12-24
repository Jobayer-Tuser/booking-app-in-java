package org.booking.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.booking.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${spring.jwt.secretKey}")
    private String secretKey;

    @Value("${spring.jwt.accessTokenExpiration}")
    private long accessTokenExpiration;

    @Value("${spring.jwt.refreshTokenExpiration}")
    private long refreshTokenExpiration;

    public Jwt generateAccessToken(User user)
    {
        return generateToken(user, accessTokenExpiration);
    }

    public Jwt generateRefreshToken(User user)
    {
        return generateToken(user, refreshTokenExpiration);
    }

    public Jwt parseToken(String token){
        try {
            var claims = getClaims(token);
            return new Jwt(claims, encryptSecreteKey());
        } catch (JwtException exception){
            return null;
        }
    }

    private Jwt generateToken(User user, long tokenExpire) {
        Claims claims = Jwts.claims()
                .subject(user.getId().toString())
                .add("email", user.getEmail())
                .add("name", user.getName())
                .add("role", user.getRole().getName())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * tokenExpire))
                .build();

        return new Jwt(claims, encryptSecreteKey());
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
            .verifyWith(encryptSecreteKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    public SecretKey encryptSecreteKey()
    {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

}
