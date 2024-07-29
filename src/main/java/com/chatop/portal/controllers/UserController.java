package com.chatop.portal.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.portal.dto.UserDTO;
import com.chatop.portal.dto.UserMapper;
import com.chatop.portal.entity.Users;
import com.chatop.portal.services.UsersService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name="Users")
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UsersService usersService;
    private final UserMapper userMapper = new UserMapper();

    @Operation(summary="Get a user by id")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") Integer id) {
        Optional<Users> user = usersService.getUserById(id);
        if(user.isPresent()) {
            UserDTO userDTO = userMapper.toDTO(user.get());
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
