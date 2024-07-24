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
import com.chatop.portal.entity.OurUsers;
import com.chatop.portal.repository.OurUserRepo;
import com.chatop.portal.services.AuthService;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private OurUserRepo ourUserRepo;

    @PostMapping("/register")
    public ResponseEntity<ReqRes> signUp(@RequestBody ReqRes signUpRequest) {
        return ResponseEntity.ok(authService.signUp(signUpRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<ReqRes> signIn(@RequestBody ReqRes signInRequest) {
        return ResponseEntity.ok(authService.signIn(signInRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ReqRes> refreshToken(@RequestBody ReqRes refreshTokenRequest) {
        return ResponseEntity.ok(authService.signIn(refreshTokenRequest));
    }
    @GetMapping("/me")
    public ResponseEntity<ReqRes> getUserProfile(@AuthenticationPrincipal UserDetails userDetails) {
        OurUsers ourUser = ourUserRepo.findByEmail(userDetails.getUsername()).orElseThrow();

        ReqRes response = new ReqRes();
        System.out.println(ourUser);
        response.setStatusCode(200);
        response.setEmail(ourUser.getEmail());
        response.setRole(ourUser.getRole());

        return ResponseEntity.ok(response);
    }
    
}
