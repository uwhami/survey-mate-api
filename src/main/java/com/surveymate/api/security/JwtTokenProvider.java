package com.surveymate.api.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collections;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date now = new Date();
        int jwtExpirationInMs = 3600000;    // 1시간
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(Authentication authentication) {
        String username = authentication.getName();
        Date now = new Date();
        int refreshTokenExpirationInMs = 86400000; // 1일 (24시간)
        Date expiryDate = new Date(now.getTime() + refreshTokenExpirationInMs);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException ex) {
            throw new RuntimeException("Security exception occurred: Invalid signature");
        } catch (MalformedJwtException ex) {
            throw new RuntimeException("Malformed JWT token");
        } catch (ExpiredJwtException ex) {
            throw new RuntimeException("Token expired");
        } catch (UnsupportedJwtException ex) {
            throw new RuntimeException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("Token argument is invalid or missing");
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        String username = claims.getSubject();

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(username, "", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
    }
}
