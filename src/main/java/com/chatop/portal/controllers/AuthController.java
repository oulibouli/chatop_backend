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

import com.chatop.portal.dto.ReqRes;
import com.chatop.portal.entity.Users;
import com.chatop.portal.repository.UsersRepository;
import com.chatop.portal.services.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name = "Authentication")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UsersRepository ourUserRepo;

    @Operation(summary="Create a new user")
    @PostMapping("/register")
    public ResponseEntity<ReqRes> signUp(@RequestBody ReqRes signUpRequest) {
        return ResponseEntity.ok(authService.signUp(signUpRequest));
    }

    @Operation(summary="Login to an existing user")
    @PostMapping("/login")
    public ResponseEntity<ReqRes> signIn(@RequestBody ReqRes signInRequest) {
        return ResponseEntity.ok(authService.signIn(signInRequest));
    }

    @Operation(summary="Get the user connected")
    @GetMapping("/me")
    public ResponseEntity<ReqRes> getUserProfile(@AuthenticationPrincipal UserDetails userDetails) {
        Users users = ourUserRepo.findByEmail(userDetails.getUsername()).orElseThrow();

        ReqRes response = new ReqRes();
        response.setStatusCode(200);
        response.setId(users.getId());
        response.setName(users.getName());
        response.setEmail(users.getEmail());
        response.setRole(users.getRole());
        response.setCreated_at(users.getCreated_at());
        response.setUpdated_at(users.getUpdated_at());

        return ResponseEntity.ok(response);
    }
    
}
