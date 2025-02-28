package com.surveymate.api.security;

import com.surveymate.api.common.exception.CustomRuntimeException;
import com.surveymate.api.domain.auth.model.CustomUserDetails;
import com.surveymate.api.domain.auth.service.CustomUserDetailsService;
import com.surveymate.api.domain.member.entity.Member;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private final CustomUserDetailsService userDetailsService;

    public String generateToken(String uuid, Member member) {
        Date now = new Date();
        int jwtExpirationInMs = 3600000;    // 1시간
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        // 권한 리스트를 문자열로 변환
        List<String> roles = member.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority) // ROLE_USER, ROLE_MANAGER 등을 추출
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(uuid)
                .claim("roles", roles)
                .claim("social", member.getSocialType().getValue())
                .claim("groupId", member.getGroup().getId())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(String uuid, Member member) {
        Date now = new Date();
        int refreshTokenExpirationInMs = 86400000; // 1일 (24시간)
        Date expiryDate = new Date(now.getTime() + refreshTokenExpirationInMs);

        // 권한 리스트를 문자열로 변환
        List<String> roles = member.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority) // ROLE_USER, ROLE_MANAGER 등을 추출
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(uuid)
                .claim("roles", roles)
                .claim("social", member.getSocialType().getValue())
                .claim("groupId", member.getGroup().getId())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }

    public Claims validateToken(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
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
