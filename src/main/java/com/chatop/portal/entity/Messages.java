package com.chatop.portal.entity;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data // Automatically define the getters and setters
@Entity // Define the class as an entity
public class Messages {
    @Id // Primary key in the table
    @GeneratedValue(strategy=GenerationType.IDENTITY) // Generates the id automatically by the BD
    private int id;
    private int rental_id;
    private int user_id;
    private String message;
    private Timestamp created_at;
    private Timestamp updated_at;
}
