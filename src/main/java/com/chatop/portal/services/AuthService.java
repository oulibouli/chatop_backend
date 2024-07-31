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
import com.chatop.portal.repository.UsersRepository;
import com.chatop.portal.utils.JWTUtils;

@Service
public class AuthService {
    
    // Inject the UsersRepository bean
    @Autowired
    private UsersRepository usersRepository;

    // Inject the JWTUtils bean for handling JWT tokens
    @Autowired
    private JWTUtils jwtUtils;

    // Inject the AuthenticationManager bean for handling authentication
    @Autowired
    private AuthenticationManager authenticationManager;

    // Inject the AuthMapper bean for mapping between AuthDTO and Users entity
    @Autowired
    private AuthMapper authMapper;

    // Method for user signup
    public AuthDTO signUp(AuthDTO authDTO) {
        AuthDTO response = new AuthDTO();
        try {
            // Map AuthDTO to Users entity
            Users newUser = authMapper.toEntity(authDTO);
            // Save the new user to the repository
            newUser = usersRepository.save(newUser);

            // If the user is saved successfully, set the response details
            if(newUser != null && newUser.getId() > 0) {
                response.setUser(newUser);
                response.setMessage("User saved successfully");
                String jwt = jwtUtils.generateToken(newUser.getEmail());
                response.setStatusCode(200);
                response.setToken(jwt);
            }
        } catch (Exception e) {
            // Handle any exceptions by setting the error details in the response
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }

    // Method for user signin
    public ResponseEntity<AuthDTO> signIn(AuthDTO signinRequest) {
        AuthDTO response = new AuthDTO();
        try {
            // Authenticate the user with the provided credentials
            Authentication authentication = authenticateUser(signinRequest.getEmail(), signinRequest.getPassword());
            // Generate a JWT token for the authenticated user
            String jwt = jwtUtils.generateToken(authentication.getName());
            // Set the response details
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setExpirationTime("24Hrs");
            response.setMessage("Successfully signed in");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Handle any exceptions by setting the error details in the response
            response.setMessage("error");
            response.setStatusCode(401);
            
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    // Method to get the user profile based on user details
    public AuthDTO getUserProfile(UserDetails userDetails) {
        return authMapper.toDTO(userDetails);
    }

    // Method to authenticate a user with the authentication manager.
    private Authentication authenticateUser(String email, String password) {
        try {
            // Attempts to authenticate the user with provided credentials.
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
        } catch (BadCredentialsException e) {
            // Throws an exception if authentication fails due to bad credentials.
            throw new BadCredentialsException("Invalid email or password.");
        }
    }
}
