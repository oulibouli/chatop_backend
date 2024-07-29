package com.chatop.portal.entity;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data // Automatically define the
@Entity
public class Messages {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private int rental_id;
    private int user_id;
    private String message;
    private Timestamp created_at;
    private Timestamp updated_at;
}
