package com.chatop.portal.configuration;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.chatop.portal.services.UsersService;
import com.chatop.portal.utils.JWTUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Annotation : Spring component
@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    // Log the debug informations
    private static final Logger logger = LoggerFactory.getLogger(JWTAuthFilter.class);

    // Spring dependency injection for the jwt tools
    @Autowired
    private JWTUtils jwtUtils;

    // Spring dependency injection for the users service
    @Autowired
    private UsersService usersService;

    // Main method requested for each HTTP request
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        // Get the authorization header for the request
        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String userEmail;

        // If the request is about images, don't filter
        if (request.getRequestURI().startsWith("/images/")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Check if the authorization header is missing or malformed
        if (authHeader == null || authHeader.isBlank() || !authHeader.startsWith("Bearer ")) {
            logger.warn("Missing or malformed Authorization header");
            filterChain.doFilter(request, response);
            return;
        }

        // Extract the JWT token from the authorization header
        jwtToken = authHeader.substring(7);
        try {
            // Extract the username from the JWT token
            userEmail = jwtUtils.extractUsername(jwtToken);
            logger.info("JWT: " + jwtToken);
            logger.info("Username from JWT: " + userEmail);

            // If the username is not null and the user is not authenticated
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Load the user details
                UserDetails userDetails = usersService.loadUserByUsername(userEmail);

                // Check if the JWT token is valid
                if (jwtUtils.isTokenValid(jwtToken, userDetails)) {
                    // Create an authentication token and pass it to the security
                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(token);
                    logger.info("Authenticated user: " + userEmail);
                    logger.info("Authorities: " + userDetails.getAuthorities());
                } else {
                    logger.warn("Invalid JWT token");
                }
            } else {
                logger.warn("Username is null or user is already authenticated");
            }
        } catch (UsernameNotFoundException e) {
            logger.error("JWT token processing failed", e);
        }

        // Pass the request and the response to the next filter
        filterChain.doFilter(request, response);
    }
}
