package com.chatop.portal.dto;

import org.springframework.stereotype.Component;

import com.chatop.portal.entity.Users;

// Spring-managed component.
@Component
public class UserMapper {

    // Converts a Users entity to a UserDTO object.
    public UserDTO toDTO(Users user) {
        UserDTO userDTO = new UserDTO();
        
        // Mapping fields from Users entity to UserDTO.
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setCreated_at(user.getCreated_at());
        userDTO.setUpdated_at(user.getUpdated_at());
        
        return userDTO; // Returning the created UserDTO.
    }
}