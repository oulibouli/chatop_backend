package com.chatop.portal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.chatop.portal.dto.MessagesDTO;
import com.chatop.portal.entity.Messages;
import com.chatop.portal.repository.MessagesRepository;

@Service
public class MessagesService {

    @Autowired
    private MessagesRepository messagesRepository;

    public ResponseEntity<String> addMessage(MessagesDTO messageDTO) {
        try {        
            Messages message = new Messages();

            message.setMessage(messageDTO.getMessage());
            message.setRental_id(messageDTO.getRental_id());
            message.setUser_id(messageDTO.getUser_id());

            message.setCreated_at(messageDTO.getCreated_at());
            message.setUpdated_at(messageDTO.getUpdated_at());

            messagesRepository.save(message);
            
            return ResponseEntity.ok("Message sent successfully");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error occurred while sending message");
        }
    }
}
