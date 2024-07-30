package com.chatop.portal.services;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.chatop.portal.dto.MessagesDTO;
import com.chatop.portal.entity.Messages;
import com.chatop.portal.repository.MessagesRepository;

@Service
public class MessagesService {

    @Autowired
    private MessagesRepository messagesRepository;

    public ResponseEntity<Map<String, Object>> addMessage(MessagesDTO messageDTO) {
        try {        
            Messages message = new Messages();

            message.setMessage(messageDTO.getMessage());
            message.setRental_id(messageDTO.getRental_id());
            message.setUser_id(messageDTO.getUser_id());

            message.setCreated_at(messageDTO.getCreated_at());
            message.setUpdated_at(messageDTO.getUpdated_at());

            messagesRepository.save(message);
            
            return ResponseEntity.ok(Collections.singletonMap("message", "Message sent successfully"));

        } catch (Exception e) {
            return new ResponseEntity<>(Collections.singletonMap("message", "Error occurred while sending message"), HttpStatus.UNAUTHORIZED);
        }
    }
}
