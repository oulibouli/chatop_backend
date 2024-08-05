package com.chatop.portal.dto;


import java.sql.Timestamp;
import java.time.Instant;

import com.chatop.portal.entity.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data // Generates automatically the getters and setters
@JsonIgnoreProperties(ignoreUnknown= true)
@JsonInclude(JsonInclude.Include.NON_NULL) // Don't include null values in the json response
public class AuthDTO {

    private String error;
    private String message;
    private String token;
    @JsonIgnore
    private int id;
    @NotNull(message = "Cannot be null")
    private String name;
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    private String role;
    @NotNull(message = "Cannot be null")
    private String password;
    @JsonProperty(access=Access.READ_ONLY)
    private Timestamp created_at;
    @JsonProperty(access=Access.READ_ONLY)
    private Timestamp updated_at;
    @JsonIgnore // Ignore this field when get the request
    private Users user;

    {
        this.created_at = Timestamp.from(Instant.now());
        this.updated_at = Timestamp.from(Instant.now());
    }
}
