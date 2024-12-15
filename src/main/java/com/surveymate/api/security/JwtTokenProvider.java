package com.surveymate.api.security;

import com.surveymate.api.common.exception.CustomRuntimeException;
import com.surveymate.api.domain.auth.model.CustomUserDetails;
import com.surveymate.api.domain.auth.service.CustomUserDetailsService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private final CustomUserDetailsService userDetailsService;

    public String generateToken(String uuid) {
        Date now = new Date();
        int jwtExpirationInMs = 3600000;    // 1시간
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(uuid)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(String uuid) {
        Date now = new Date();
        int refreshTokenExpirationInMs = 86400000; // 1일 (24시간)
        Date expiryDate = new Date(now.getTime() + refreshTokenExpirationInMs);

        return Jwts.builder()
                .setSubject(uuid)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }

    public String validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
            return claims.getSubject();
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
        } catch (Exception ex){
            throw new CustomRuntimeException("JWT Error", ex);
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        String uuid = claims.getSubject();

        CustomUserDetails userDetails = userDetailsService.loadUserByUuid(uuid);
        return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
    }
}
