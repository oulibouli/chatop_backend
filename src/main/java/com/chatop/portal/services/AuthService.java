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
import com.chatop.portal.dto.AuthDTOLogin;
import com.chatop.portal.dto.AuthDTORegister;
import com.chatop.portal.dto.AuthMapper;
import com.chatop.portal.entity.Users;
import com.chatop.portal.repository.UsersRepository;
import com.chatop.portal.utils.JWTUtils;
import com.chatop.portal.utils.StringUtils;

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
    public ResponseEntity<AuthDTO> signUp(AuthDTORegister authDTO) {
        AuthDTO response = new AuthDTO();
        try {
            if(StringUtils.isNullOrEmpty(authDTO.getEmail()) || 
                StringUtils.isNullOrEmpty(authDTO.getName()) || 
                StringUtils.isNullOrEmpty(authDTO.getPassword())) {
                throw new IllegalArgumentException("Name, password, or email cannot be empty.");
            }
            if(usersRepository.existsByEmail(authDTO.getEmail())) {
                throw new IllegalStateException("A user with this email already exists.");
            }
            // Map AuthDTO to Users entity
            Users newUser = authMapper.toEntity(authDTO);
            // Save the new user to the repository
            newUser = usersRepository.save(newUser);

            // If the user is saved successfully, set the response details
            if(newUser != null && newUser.getId() > 0) {
                response.setUser(newUser);
                String jwt = jwtUtils.generateToken(newUser.getEmail());
                response.setToken(jwt);
            }
        }
        catch (IllegalArgumentException e) {
             // Return bad request if there is an illegal argument
            response.setError(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }catch (IllegalStateException e) {
            // Return conflict if the user already exists
            response.setError(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            // Handle any other exceptions by setting the error details in the response
            response.setError(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Method for user signin
    public ResponseEntity<AuthDTO> signIn(AuthDTOLogin authDTO) {
        AuthDTO response = new AuthDTO();
        try {
            if("".equals(authDTO.getEmail()) || "".equals(authDTO.getPassword())) {
                throw new IllegalArgumentException("Password or email cannot be empty.");
            }
            // Authenticate the user with the provided credentials
            Authentication authentication = authenticateUser(authDTO.getEmail(), authDTO.getPassword());
            // Generate a JWT token for the authenticated user
            String jwt = jwtUtils.generateToken(authentication.getName());
            // Set the response details
            response.setToken(jwt);
            response.setMessage("Successfully signed in");
        } catch (IllegalArgumentException e) {
            // Return bad request if there is an illegal argument
           response.setError(e.getMessage());
           return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
       } catch (Exception e) {
           // Handle any other exceptions by setting the error details in the response
           response.setError(e.getMessage());
           return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
       }

       return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Method to get the user profile based on user details
    public ResponseEntity<AuthDTO> getUserProfile(UserDetails userDetails) {
        AuthDTO response = new AuthDTO();
        try {
            response = authMapper.toDTO(userDetails);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e) {
            // Handle any other exceptions by setting the error details in the response
            response.setError(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
