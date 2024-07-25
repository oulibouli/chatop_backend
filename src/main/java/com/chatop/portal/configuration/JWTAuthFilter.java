package com.chatop.portal.configuration;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.chatop.portal.services.UsersService;
import com.chatop.portal.utils.JWTUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthFilter extends OncePerRequestFilter{

    private static final Logger logger = LoggerFactory.getLogger(JWTAuthFilter.class);

    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private UsersService ourUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String userEmail;

        if(authHeader == null || authHeader.isBlank() || !authHeader.startsWith("Bearer ")) {
            logger.warn("Missing or malformed Authorization header");
            filterChain.doFilter(request, response);
            return;
        }

        jwtToken = authHeader.substring(7);
        try {
            userEmail = jwtUtils.extractUsername(jwtToken);
            logger.info("JWT: " + jwtToken);
            logger.info("Username from JWT: " + userEmail);
            if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = ourUserDetailsService.loadUserByUsername(userEmail);

                if(jwtUtils.isTokenValid(jwtToken, userDetails)) {
                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                    token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(token);
                    logger.info("Authenticated user: " + userEmail);
                }
            }
        } catch (Exception e) {
            logger.error("JWT token processing failed", e);
        }

        filterChain.doFilter(request, response);
    }

}
