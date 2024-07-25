package com.chatop.portal.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data // Generates setters, getters
public class UserDTO {

    private int id;
    private String email;
    private String name;
    private Timestamp created_at;
    private Timestamp updated_at;
}
