package com.helium.oakcollectionsadmin.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
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

                // 4. Create authentication object (without fetching from DB here)
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(username, null, List.of());

                // 5. Set authentication in security context so Spring Security sees it as authenticated
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        // 6. Continue with the next filter in the chain
        filterChain.doFilter(request, response);
    }


}
