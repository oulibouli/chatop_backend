package com.chatop.portal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.portal.dto.AuthDTO;
import com.chatop.portal.dto.AuthDTOLogin;
import com.chatop.portal.dto.AuthDTORegister;
import com.chatop.portal.services.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

// Swagger annotation
@Tag(name = "Authentication")
// Define this class as a REST controller
@RestController
@Validated
// Define the basic url for the authentication requests
@RequestMapping("/api/auth")
public class AuthController {

    // Inject the auth service
    @Autowired
    private AuthService authService;

    // Swagger Annotation
    @Operation(summary = "Create User", description = "Create a new user", responses = 
    {
        @ApiResponse(responseCode = "200", description = "User created", content = {@Content(mediaType = "application/json")}),
        @ApiResponse(responseCode = "400", description = "Name, password, or email cannot be empty.", content = @Content),
        @ApiResponse(responseCode = "403", description = "Validation failed", content = @Content),
        @ApiResponse(responseCode = "409", description = "A user with this email already exists.", content = @Content),
        @ApiResponse(responseCode = "500", description = "Server Error", content = @Content)
    })
    @PostMapping("/register")
    public ResponseEntity<AuthDTO> signUp(@Valid @RequestBody AuthDTORegister signUpRequest) {
        // Call the auth service to register a new user
        return authService.signUp(signUpRequest);
    }

    @Operation(summary = "Login User", description = "Login to an existing user", responses = 
    {
        @ApiResponse(responseCode = "200", description = "Successfully signed in", content = {@Content(mediaType = "application/json")}),
        @ApiResponse(responseCode = "400", description = "Password or email cannot be empty.", content = @Content),
        @ApiResponse(responseCode = "500", description = "Invalid email or password", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<AuthDTO> signIn(@RequestBody AuthDTOLogin signInRequest) {
        // Call the auth service to connect a user
        return authService.signIn(signInRequest);
    }

    @Operation(summary = "Logged user infos", description = "Get the user connected", responses = 
    {
        @ApiResponse(responseCode = "200", description = "Success", content = {@Content(mediaType = "application/json")}),
        @ApiResponse(responseCode = "500", description = "Server Error", content = @Content)
    })
    @GetMapping("/me")
    public ResponseEntity<AuthDTO> getUserProfile(@Valid @AuthenticationPrincipal UserDetails userDetails) {
        // Call the auth service to request the connected user
        return authService.getUserProfile(userDetails);
    }
}
