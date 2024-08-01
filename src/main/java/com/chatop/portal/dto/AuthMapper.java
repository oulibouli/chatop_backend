package com.chatop.portal.dto;

import java.sql.Timestamp;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.chatop.portal.entity.Users;
import com.chatop.portal.repository.UsersRepository;

@Component // Annotation for Spring-managed component.
public class AuthMapper {

    @Autowired // Inject dependency of the PasswordEncoder bean
    private PasswordEncoder passwordEncoder;
    
    @Autowired // Inject dependency of the UsersRepository bean
    private UsersRepository usersRepository;

    @Value("${default.role}") // Inject the default role for a new user from the properties file
    private String defaultRole;

    // Converts an AuthDTO object to a Users entity.
    public Users toEntity(AuthDTORegister authDTO) {
        Timestamp now = Timestamp.from(Instant.now());
        Users user = new Users();
        user.setEmail(authDTO.getEmail()); // Setting the email from authDTO to Users entity.
        user.setName(authDTO.getName()); // Setting the name from authDTO to Users entity.
        user.setPassword(passwordEncoder.encode(authDTO.getPassword())); // Encoding the password and setting it.
        user.setRole(defaultRole); // Setting a default role for the user.
        user.setCreated_at(now); // Setting the creation date from authDTO.
        user.setUpdated_at(now); // Setting the update date from authDTO.

        return user; // Returning the created Users entity.
    }

    // Converts a UserDetails object to an AuthDTO object.
    public AuthDTO toDTO(UserDetails userDetails) {
        AuthDTO authDTO = new AuthDTO();
        try {
            // Retrieving the Users entity by email (username) from the repository.
            Users user = usersRepository.findByEmail(userDetails.getUsername()).orElseThrow();
            // Setting the properties of authDTO from the retrieved Users entity.
            authDTO.setId(user.getId());
            authDTO.setName(user.getName());
            authDTO.setEmail(user.getEmail());
            authDTO.setRole(user.getRole());
            authDTO.setCreated_at(user.getCreated_at());
            authDTO.setUpdated_at(user.getUpdated_at());
            authDTO.setStatusCode(200); // Setting the status code to 200 (OK).
        } catch (Exception e) {
            authDTO.setStatusCode(500); // Setting the status code to 500 (Internal Server Error) in case of an exception.
            authDTO.setError(e.getMessage()); // Setting the error message from the exception.
        }
        return authDTO; // Returning the created AuthDTO object.
    }
}
