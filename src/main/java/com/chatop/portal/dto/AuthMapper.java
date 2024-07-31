package com.chatop.portal.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.chatop.portal.entity.Users;
import com.chatop.portal.repository.UsersRepository;

@Component
public class AuthMapper {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsersRepository usersRepository;

    public Users toEntity(AuthDTO authDTO) {
        Users user = new Users();
        user.setEmail(authDTO.getEmail());
        user.setName(authDTO.getName());
        user.setPassword(passwordEncoder.encode(authDTO.getPassword()));
        user.setRole("ROLE_USER");
        user.setCreated_at(authDTO.getCreated_at());
        user.setUpdated_at(authDTO.getUpdated_at());

        return user;
    }

    public AuthDTO toDTO(UserDetails userDetails) {
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
}
