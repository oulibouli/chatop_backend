package com.chatop.portal.controllers;

import java.sql.Timestamp;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.portal.dto.ApiResponse;
import com.chatop.portal.dto.MessagesDTO;
import com.chatop.portal.entity.Messages;
import com.chatop.portal.services.MessagesService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Messages")
@RestController
public class MessagesController {

    @Autowired
    private MessagesService messagesService;

    @PostMapping("/api/messages/")
    public ResponseEntity<ApiResponse> addMessage(@RequestBody MessagesDTO messagesDTO){        
       if(messagesDTO.getMessage() == null || messagesDTO.getRental_id() == null  || messagesDTO.getUser_id() == null ) {
        return ResponseEntity.badRequest().body(new ApiResponse("Invalid input"));
       }
       
        Timestamp timestamp = Timestamp.from(Instant.now());
        Messages message = new Messages();

        message.setMessage(messagesDTO.getMessage());
        message.setRental_id(messagesDTO.getRental_id());
        message.setUser_id(messagesDTO.getUser_id());

        message.setCreated_at(timestamp);
        message.setUpdated_at(timestamp);

        messagesService.addMessage(message);

        ApiResponse apiResponse = new ApiResponse("Message sent with success");
        return ResponseEntity.ok(apiResponse);
    }
    
}
