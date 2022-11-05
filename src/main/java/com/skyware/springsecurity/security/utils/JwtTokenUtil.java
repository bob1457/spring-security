package com.skyware.springsecurity.security.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

@Component
public class JwtTokenUtil
{
    private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000; // 24 hour
    @Value("${app.jwt.secret}")
    private String SECRET_KEY;
    private SecretKey key;
    public String generateToken(UserDetails userDetails)
    {
        byte[] secretBytes = SECRET_KEY.getBytes();
        key = new SecretKeySpec(secretBytes, 0, secretBytes.length, "HmacSHA512");

        var token = Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuer("Skyware")
                .setAudience("skyware co.")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(key,SignatureAlgorithm.HS256)
                .compact();

        return token;
    }

    public boolean validate(String token)
    {
        if (getUsername(token) != null && isExpired(token)) {
            return true;
        }
        return false;
    }

    public String getUsername(String token) {
        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    public boolean isExpired(String token) {
        Claims claims = getClaims(token);
        return claims.getExpiration().after(new Date(System.currentTimeMillis()));
    }

    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
    }
}
