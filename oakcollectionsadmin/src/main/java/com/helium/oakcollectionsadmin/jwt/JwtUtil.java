package com.helium.oakcollectionsadmin.jwt;

import com.helium.oakcollectionsadmin.serviceImpls.CustomUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;



@Component
@Slf4j
@RequiredArgsConstructor
    public class JwtUtil {
    private final CustomUserDetailsService detailsService;


    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken(UserDetails userDetails) {
        log.info("Generating JWT token for {}", userDetails.getUsername());
        // 1 hour
        long EXPIRATION = 1000 * 60 * 15;
        log.info("Expiration time is {}", EXPIRATION);
            return Jwts.builder()
                    .setSubject(userDetails.getUsername())
                    .claim("roles", userDetails.getAuthorities())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                    .signWith(SignatureAlgorithm.HS256, key)
                    .compact();
        }
        public String extractUsername (String token){
            return getClaims(token).getSubject();
        }

        public List<String> extractRoles (String token){
            return getClaims(token).get("roles", List.class);
        }

        public boolean validateToken (String token){
            try {
                return !getClaims(token).getExpiration().before(new Date());
            } catch (Exception e) {
                return false;
            }
        }

        private Claims getClaims (String token){
            return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        }
    }

