package com.chatop.portal.dto;

import java.sql.Timestamp;
import java.time.Instant;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MessagesDTO {

    @NotNull(message = "Cannot be null")
    private String message;
    @NotNull(message = "Cannot be null")
    private Integer rental_id;
    @NotNull(message = "Cannot be null")
    private Integer user_id;
    private Timestamp created_at;
    private Timestamp updated_at;
    
    {
        this.created_at = Timestamp.from(Instant.now());
        this.updated_at = Timestamp.from(Instant.now());
    }
}
