package com.chatop.portal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.portal.dto.AuthDTO;
import com.chatop.portal.services.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

// Swagger annotation
@Tag(name = "Authentication")
// Define this class as a REST controller
@RestController
// Define the basic url for the authentication requests
@RequestMapping("/api/auth")
public class AuthController {

    // Inject the auth service
    @Autowired
    private AuthService authService;

    // Swagger Annotation
    @Operation(summary="Create a new user")
    @PostMapping("/register")
    public ResponseEntity<AuthDTO> signUp(@RequestBody AuthDTO signUpRequest) {
        // Call the auth service to register a new user
        return ResponseEntity.ok(authService.signUp(signUpRequest));
    }

    @Operation(summary="Login to an existing user")
    @PostMapping("/login")
    public ResponseEntity<AuthDTO> signIn(@RequestBody AuthDTO signInRequest) {
        // Call the auth service to connect a user
        return authService.signIn(signInRequest);
    }

    @Operation(summary="Get the user connected")
    @GetMapping("/me")
    public ResponseEntity<AuthDTO> getUserProfile(@Valid @AuthenticationPrincipal UserDetails userDetails) {
        // Call the auth service to request the connected user
        return ResponseEntity.ok(authService.getUserProfile(userDetails));
    }
}
