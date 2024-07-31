package com.chatop.portal.services;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.chatop.portal.dto.MessagesDTO;
import com.chatop.portal.dto.MessagesMapper;
import com.chatop.portal.entity.Messages;
import com.chatop.portal.repository.MessagesRepository;

@Service
public class MessagesService {

    @Autowired
    private MessagesRepository messagesRepository;
    @Autowired
    private MessagesMapper messagesMapper;

    public ResponseEntity<Map<String, Object>> addMessage(MessagesDTO messageDTO) {
        try {        
            Messages message = messagesMapper.toEntity(messageDTO);

            messagesRepository.save(message);
            
            return ResponseEntity.ok(Collections.singletonMap("message", "Message sent successfully"));

        } catch (Exception e) {
            return new ResponseEntity<>(Collections.singletonMap("message", "Error occurred while sending message"), HttpStatus.UNAUTHORIZED);
        }
    }
}
