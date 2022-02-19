package com.yyatsiuk.api.core.security.jwt;

import com.yyatsiuk.api.core.security.SecurityUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@Slf4j
public class JwtTokenProvider {

    private static final String BEARER_TOKEN_PATTERN = "Bearer ";
    private static final String EMPTY_CREDENTIALS = "";
    private static final String CLAIM_EMAIL = "email";
    private static final String CLAIM_ROLE = "role";


    @Value("${jwt.hmac.secret}")
    private String secret;

    private SecurityUserDetailsService userDetailsService;

    @Autowired
    public void setSecurityUserDetailsService(SecurityUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(Long id, String email, List<String> roles) {

        Claims claims = Jwts.claims()
                .setSubject(String.valueOf(id));
        claims.put(CLAIM_ROLE, roles);
        claims.put(CLAIM_EMAIL, email);

        Date now = new Date();
        Date validity = new Date(now.getTime() + 3600000 * 24);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService
                .loadUserByUsername(getUsername(token));

        return new UsernamePasswordAuthenticationToken(userDetails, EMPTY_CREDENTIALS, userDetails.getAuthorities());
    }

    private String getUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        return (String) claims.get(CLAIM_EMAIL);
    }

    String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader(AUTHORIZATION);

        if (bearerToken != null && bearerToken.startsWith(BEARER_TOKEN_PATTERN)) {
            return bearerToken.substring(BEARER_TOKEN_PATTERN.length());
        }

        return null;
    }

    boolean validateToken(String token) {
        try {

            if (token == null) {
                return false;
            }

            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token);

            return !claimsJws.getBody()
                    .getExpiration()
                    .before(new Date());

        } catch (JwtException e) {
            log.warn("JWT token is expired or invalid", e);
        }

        return false;
    }


}
