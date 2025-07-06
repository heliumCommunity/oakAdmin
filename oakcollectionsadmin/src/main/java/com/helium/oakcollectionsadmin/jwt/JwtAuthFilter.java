package com.helium.oakcollectionsadmin.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Component
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {


        // 1. Get Authorization header from request
        String authHeader = request.getHeader("Authorization");

        // 2. Check if it contains a Bearer token
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Extract token from header

            // 3. Validate the token
            if (jwtUtil.validateToken(token)) {
                String username = jwtUtil.extractUsername(token); // Get username from token
                List<String> roles = jwtUtil.extractRoles(token); // You'll need this


                // Convert roles to GrantedAuthority
                List<GrantedAuthority> authorities = roles.stream()
                        .map(role -> new SimpleGrantedAuthority(role))
                        .collect(Collectors.toList());
                log.info("Authenticated user: {}, roles: {}", username, roles);

                // 3. Create authentication object
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(username, null, authorities);

                // 4. Set authentication in security context
                SecurityContextHolder.getContext().setAuthentication(auth);
            }


        }
        // 5. Continue with the next filter
        filterChain.doFilter(request, response);

//        // 1. Get Authorization header from request
//        String authHeader = request.getHeader("Authorization");
//
//        // 2. Check if it contains a Bearer token
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            String token = authHeader.substring(7); // Extract token from header
//
//            // 3. Validate the token
//            if (jwtUtil.validateToken(token)) {
//                String username = jwtUtil.extractUsername(token); // Get username from token
//
//                // 4. Create authentication object (without fetching from DB here)
//                UsernamePasswordAuthenticationToken auth =
//                        new UsernamePasswordAuthenticationToken(username, null, List.of());
//
//                // 5. Set authentication in security context so Spring Security sees it as authenticated
//                SecurityContextHolder.getContext().setAuthentication(auth);
//            }
//        }
//
//        // 6. Continue with the next filter in the chain
//        filterChain.doFilter(request, response);
//    }


    }
}
