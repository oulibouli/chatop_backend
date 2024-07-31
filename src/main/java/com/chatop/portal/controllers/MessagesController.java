package com.chatop.portal.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.portal.dto.MessagesDTO;
import com.chatop.portal.services.MessagesService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

// Swagger Annotation
@Tag(name = "Messages")
// Define this class as a REST controller
@RestController
// Define the basic url for the message requests
@RequestMapping("/api/messages")
public class MessagesController {

    @Autowired
    private MessagesService messagesService;

    // Swagger Annotation
    @Operation(summary = "Create message", description = "Create a new message", responses = 
    {
        @ApiResponse(responseCode = "200", description = "Message sent successfully", content = {@Content(mediaType = "application/json")}),
        @ApiResponse(responseCode = "401", description = "Error occurred while sending message", content = @Content),
    })
    @PostMapping("")
    public ResponseEntity<Map<String, Object>> addMessage(@Valid @RequestBody MessagesDTO messageDTO) {
        // Call the message service to add a new message
        return messagesService.addMessage(messageDTO);
    }
}
