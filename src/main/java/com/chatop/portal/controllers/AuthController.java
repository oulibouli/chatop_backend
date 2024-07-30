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


@Tag(name = "Authentication")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(summary="Create a new user")
    @PostMapping("/register")
    public ResponseEntity<AuthDTO> signUp(@RequestBody AuthDTO signUpRequest) {
        return ResponseEntity.ok(authService.signUp(signUpRequest));
    }

    @Operation(summary="Login to an existing user")
    @PostMapping("/login")
    public ResponseEntity<AuthDTO> signIn(@RequestBody AuthDTO signInRequest) {
        return authService.signIn(signInRequest);
    }

    @Operation(summary="Get the user connected")
    @GetMapping("/me")
    public ResponseEntity<AuthDTO> getUserProfile(@Valid @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(authService.getUserProfile(userDetails));
    }
}
