package com.chatop.portal.services;


import java.sql.Timestamp;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.chatop.portal.dto.AuthDTO;
import com.chatop.portal.entity.Users;
import com.chatop.portal.exception.AuthFailedException;
import com.chatop.portal.repository.UsersRepository;
import com.chatop.portal.utils.JWTUtils;

@Service
public class AuthService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthDTO signUp(AuthDTO registrationrequest) {
        AuthDTO resp = new AuthDTO();
        try {
            Users user = new Users();
            user.setEmail(registrationrequest.getEmail());
            user.setName(registrationrequest.getName());
            user.setPassword(passwordEncoder.encode(registrationrequest.getPassword()));
            user.setRole("ROLE_USER");
            user.setCreated_at(Timestamp.from(Instant.now()));
            user.setUpdated_at(Timestamp.from(Instant.now()));
            Users newUser = usersRepository.save(user);

            if(newUser != null && newUser.getId() > 0) {
                resp.setOurUsers(newUser);
                resp.setMessage("User saved successfully");
                String jwt = jwtUtils.generateToken(newUser.getEmail());
                resp.setStatusCode(200);
                resp.setToken(jwt);
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public ResponseEntity<AuthDTO> signIn(AuthDTO signinRequest) {
        AuthDTO response = new AuthDTO();
        try {
            Authentication authentication = authenticateUser(signinRequest.getEmail(), signinRequest.getPassword());
            String jwt = jwtUtils.generateToken(authentication.getName());
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setExpirationTime("24Hrs");
            response.setMessage("Successfully signed in");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMessage("error");
            response.setStatusCode(401);
            
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    public AuthDTO getUserProfile(UserDetails userDetails) {
        AuthDTO authDTO = new AuthDTO();
        try {
            Users user = usersRepository.findByEmail(userDetails.getUsername()).orElseThrow();
            authDTO.setId(user.getId());
            authDTO.setName(user.getName());
            authDTO.setEmail(user.getEmail());
            authDTO.setRole(user.getRole());
            authDTO.setCreated_at(user.getCreated_at());
            authDTO.setUpdated_at(user.getUpdated_at());
            authDTO.setStatusCode(200);
        } catch (Exception e) {
            authDTO.setStatusCode(500);
            authDTO.setError(e.getMessage());
        }
        return authDTO;
    }

    // Helper method to authenticate a user with the authentication manager.
    private Authentication authenticateUser(String email, String password) {
        try {
            // Attempts to authenticate the user with provided credentials.
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
        } catch (BadCredentialsException e) {
            // Throws a custom exception if authentication fails due to bad credentials.
            throw new AuthFailedException("Invalid email or password.");
        }
    }
}
