package com.chatop.portal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.portal.dto.UserDTO;
import com.chatop.portal.services.UsersService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

// Swagger annotation
@Tag(name="Users")
@RestController
@RequestMapping("/api/user")
public class UserController {

    // Inject the users service
    @Autowired
    private UsersService usersService;

    @Operation(summary = "Get User", description = "Get a user by id", responses = {
        @ApiResponse(responseCode = "200", description = "User found", content = {@Content(mediaType = "application/json")}),
        @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
        })
    @GetMapping("/{id}")
    // This method manages a GET HTTP request to get a user per id
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") Integer id) {
        return usersService.getUserById(id);
    }
}
