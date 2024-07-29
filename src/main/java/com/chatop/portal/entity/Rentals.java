package com.chatop.portal.entity;


import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Rentals {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String name;
    private double surface;
    private double price;
    private String picture;
    private String description;
    private int owner_id;
    private Timestamp created_at;
    private Timestamp updated_at;
}
