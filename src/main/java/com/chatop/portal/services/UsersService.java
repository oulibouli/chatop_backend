package com.chatop.portal.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.chatop.portal.dto.UserDTO;
import com.chatop.portal.dto.UserMapper;
import com.chatop.portal.entity.Users;
import com.chatop.portal.repository.UsersRepository;

@Service
public class UsersService implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    UserMapper userMapper;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usersRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public ResponseEntity<UserDTO> getUserById(int id) {
        Optional<Users> user = usersRepository.findById(id);
        ResponseEntity<UserDTO> response;

        if(user.isPresent()) {
            UserDTO userDTO = userMapper.toDTO(user.get());
            response = new ResponseEntity<>(userDTO, HttpStatus.OK);
        } else {
            response =  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

     // Retrieves the username of the currently authenticated user.
    public String getCurrentAuthenticatedUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName(); // Returns the authenticated user's name.
    }
}