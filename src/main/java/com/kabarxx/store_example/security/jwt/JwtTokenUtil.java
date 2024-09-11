package com.kabarxx.store_example.security.jwt;

import com.kabarxx.store_example.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenUtil {

    @Value("{secret.key}")
    private final String SECRET = "RINAZARETSKAYAMOYALUBOVNAVCUJIZN";

    private SecretKey getSecretKey() {
        return new SecretKeySpec(Base64.getDecoder().decode(SECRET), SignatureAlgorithm.HS256.getJcaName());
    }

    public String generateToken(User user) {
        SecretKey secretKey = getSecretKey();

        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims getUsernameFromToken(String token) {
        SecretKey secretKey = getSecretKey();

        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


}
