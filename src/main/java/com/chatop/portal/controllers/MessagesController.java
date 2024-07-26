package com.chatop.portal.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.portal.dto.ApiResponse;
import com.chatop.portal.dto.MessagesDTO;
import com.chatop.portal.services.MessagesService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Messages")
@RestController
@RequestMapping("/api/messages")
public class MessagesController {

    private static final Logger logger = LoggerFactory.getLogger(MessagesController.class);

    @Autowired
    private MessagesService messagesService;

    @PostMapping("/")
    public ResponseEntity<ApiResponse> addMessage(@Valid @RequestBody MessagesDTO messageDTO) {
        logger.info("Received request to add message: {}", messageDTO);
        try {
            messagesService.addMessage(messageDTO);
            ApiResponse apiResponse = new ApiResponse("Message sent with success");
            logger.info("Message sent successfully: {}", messageDTO);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            logger.error("Error occurred while sending message: ", e);
            ApiResponse apiResponse = new ApiResponse("Failed to send message");
            return ResponseEntity.status(500).body(apiResponse);
        }
    }
}
