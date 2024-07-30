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
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Messages")
@RestController
@RequestMapping("/api/messages")
public class MessagesController {

    @Autowired
    private MessagesService messagesService;

    @Operation(summary="Create a new message")
    @PostMapping("")
    public ResponseEntity<Map<String, Object>> addMessage(@Valid @RequestBody MessagesDTO messageDTO) {
        return messagesService.addMessage(messageDTO);
    }
}
