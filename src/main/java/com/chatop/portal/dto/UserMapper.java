package com.chatop.portal.dto;

import org.springframework.stereotype.Component;

import com.chatop.portal.entity.Users;

@Component
public class UserMapper {

    public UserDTO toDTO(Users user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setCreated_at(user.getCreated_at());
        userDTO.setUpdated_at(user.getUpdated_at());

        return userDTO;
    }
}
