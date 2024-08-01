package com.chatop.portal.dto;

import java.sql.Timestamp;
import java.time.Instant;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;

@Data // Generates automatically the getters and setters
public class RentalDTO {
    @JsonProperty(access = Access.READ_ONLY)
    private int id;
    private String name;
    private double surface;
    private double price;
    @JsonProperty(access = Access.READ_ONLY)
    private String picture;
    private String description;
    private int ownerId;
    @JsonProperty(access = Access.READ_ONLY) // Only display for the get requests
    private Timestamp created_at;
    @JsonProperty(access = Access.READ_ONLY)
    private Timestamp updated_at;
    @JsonProperty(access = Access.WRITE_ONLY) // Ignore this field when get the request
    private MultipartFile pictureFile;

    {
        this.created_at = Timestamp.from(Instant.now());
        this.updated_at = Timestamp.from(Instant.now());
    }
}
