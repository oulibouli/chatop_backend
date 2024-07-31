package com.chatop.portal.dto;


import java.sql.Timestamp;
import java.time.Instant;

import com.chatop.portal.entity.Users;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data // Generates automatically the getters and setters
@JsonIgnoreProperties(ignoreUnknown= true)
@JsonInclude(JsonInclude.Include.NON_NULL) // Don't include null values in the json response
public class AuthDTO {

    private int statusCode;
    private String error;
    private String message;
    private String token;
    private String refreshToken;
    private String expirationTime;
    private int id;
    @NotNull(message = "Cannot be null")
    private String name;
    @Email(message = "Email should be valid")
    private String email;
    private String role;
    @NotNull(message = "Cannot be null")
    private String password;
    private Timestamp created_at;
    private Timestamp updated_at;
    private Users user;

    {
        this.created_at = Timestamp.from(Instant.now());
        this.updated_at = Timestamp.from(Instant.now());
    }
}
