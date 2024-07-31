package com.chatop.portal.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.chatop.portal.dto.UserDTO;
import com.chatop.portal.dto.UserMapper;
import com.chatop.portal.entity.Users;
import com.chatop.portal.repository.UsersRepository;

@Service
public class UsersService implements UserDetailsService {

    // Inject the user repo
    // Inject the user repo
    @Autowired
    private UsersRepository usersRepository;

    // Inject the user mapper to convert between DTO and entity

    // Inject the user mapper to convert between DTO and entity
    @Autowired
    UserMapper userMapper;
    
    // Request a user by username (email)
    // Request a user by username (email)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Get the user by email from the repo / Return an exception if not found
        // Get the user by email from the repo / Return an exception if not found
        return usersRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    // Get a user by id
    // Get a user by id
    public ResponseEntity<UserDTO> getUserById(int id) {
        // Get the user by id from the repo
        // Get the user by id from the repo
        Optional<Users> user = usersRepository.findById(id);
        ResponseEntity<UserDTO> response;

        // If user exist, map it to DTO and return a HTTP code 200 : success
        if (user.isPresent()) {
        // If user exist, map it to DTO and return a HTTP code 200 : success
        if (user.isPresent()) {
            UserDTO userDTO = userMapper.toDTO(user.get());
            response = new ResponseEntity<>(userDTO, HttpStatus.OK);
        } else {
            // If user not found, return a HTTP code 404 : Not Found
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return response;
    }
}

}
