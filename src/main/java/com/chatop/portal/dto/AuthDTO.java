package com.chatop.portal.dto;


import java.sql.Timestamp;

import com.chatop.portal.entity.Users;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown= true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthDTO {

    private int statusCode;
    private String error;
    private String message;
    private String token;
    private String refreshToken;
    private String expirationTime;
    private int id;
    private String name;
    private String email;
    private String role;
    private String password;
    private Timestamp created_at;
    private Timestamp updated_at;
    private Users ourUsers;
}
