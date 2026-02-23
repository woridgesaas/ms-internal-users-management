package org.woridge.ms_internal_users_management.service.implementation;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.woridge.ms_internal_users_management.enumeration.Role;
import org.woridge.ms_internal_users_management.service.JwtService;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${spring.security.jwt.secret-key}")
    private String secretKey;

    @Override
    public String extractCIN(String token) {

        return extractClaim(token, claims ->
                claims.get("CIN", String.class)
        );

    }

    @Override
    public Role extractRole(String token) {

        String role = extractClaim(token, claims ->
                claims.get("role", String.class)
        );

        return Role.valueOf(role);
    }

    @Override
    public Boolean isValidToken(String token) {

        return !isTokenExpired(token);
    }

    private SecretKey getSecretKey() {

        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    private Claims extractPayload(String token) {

        return Jwts
                .parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private <T> T extractClaim(String token, Function<Claims, T> functionResolver) {

        final Claims payload = extractPayload(token);

        return functionResolver.apply(payload);
    }

    private Date extractExpiration(String token) {

        return extractClaim(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {

        return extractExpiration(token).before(new Date());
    }
}
