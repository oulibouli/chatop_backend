package com.chatop.portal.dto;

import java.sql.Timestamp;
import java.time.Instant;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @JsonIgnore // Ignore this field when get the request
    private MultipartFile pictureFile;

    {
        this.created_at = Timestamp.from(Instant.now());
        this.updated_at = Timestamp.from(Instant.now());
    }
}
