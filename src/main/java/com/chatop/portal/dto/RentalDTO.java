package com.chatop.portal.dto;

import java.sql.Timestamp;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class RentalDTO {
    private int id;
    private String name;
    private double surface;
    private double price;
    private String picture;
    private String description;
    private int ownerId;
    private Timestamp created_at;
    private Timestamp updated_at;
    private MultipartFile pictureFile;
}
