package com.chatop.portal.services;


import java.sql.Timestamp;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.chatop.portal.dto.ReqRes;
import com.chatop.portal.entity.Users;
import com.chatop.portal.repository.UsersRepository;
import com.chatop.portal.utils.JWTUtils;

@Service
public class AuthService {
    @Autowired
    private UsersRepository ourUserRepo;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    public ReqRes signUp(ReqRes registrationrequest) {
        ReqRes resp = new ReqRes();
        try {
            Timestamp timestamp = Timestamp.from(Instant.now());
            Users ourUsers = new Users();
            ourUsers.setEmail(registrationrequest.getEmail());
            ourUsers.setName(registrationrequest.getName());
            ourUsers.setPassword(passwordEncoder.encode(registrationrequest.getPassword()));
            ourUsers.setRole("ROLE_USER");
            ourUsers.setCreated_at(timestamp);
            Users ourUserResult = ourUserRepo.save(ourUsers);

            if(ourUserResult != null && ourUserResult.getId() > 0) {
                resp.setOurUsers(ourUserResult);
                resp.setMessage("User saved successfully");
                var user = ourUserRepo.findByEmail(registrationrequest.getEmail()).orElseThrow();
                var jwt = jwtUtils.generateToken(user);
                resp.setStatusCode(200);
                resp.setToken(jwt);
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public ReqRes signIn(ReqRes signinRequest) {
        ReqRes response = new ReqRes();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getEmail(), signinRequest.getPassword()));
            var user = ourUserRepo.findByEmail(signinRequest.getEmail()).orElseThrow();
            System.out.println("USER is :" + user);
            var jwt = jwtUtils.generateToken(user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setExpirationTime("24Hrs");
            response.setMessage("Successfully signed in");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }
}
