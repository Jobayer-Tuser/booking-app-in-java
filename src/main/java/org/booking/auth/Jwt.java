package org.booking.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.booking.role.UserRole;

import javax.crypto.SecretKey;
import java.util.Date;

public class Jwt {

    private final Claims claims;
    private final SecretKey secretKey;

    public Jwt(Claims claims, SecretKey secretKey) {
        this.claims = claims;
        this.secretKey = secretKey;
    }

    public boolean isExpire(){
        return claims.getExpiration().before(new Date());
    }

    public Long getUserId(){
        return Long.valueOf(
                claims.getSubject()
        );
    }

    public UserRole getRole(){
        return UserRole.valueOf(
                claims.get("role", String.class)
        );
    }

    public String toString() {
        return Jwts.builder()
                .claims(claims)
                .signWith(secretKey)
                .compact();
    }
}
