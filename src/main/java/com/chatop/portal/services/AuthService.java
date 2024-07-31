package com.chatop.portal.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.chatop.portal.dto.AuthDTO;
import com.chatop.portal.dto.AuthMapper;
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
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthMapper authMapper;

    public AuthDTO signUp(AuthDTO authDTO) {
        AuthDTO resp = new AuthDTO();
        try {
            Users newUser = authMapper.toEntity(authDTO);
            newUser = usersRepository.save(newUser);

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
        return authMapper.toDTO(userDetails);
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
