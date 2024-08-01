package com.chatop.portal.services;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.chatop.portal.dto.MessagesDTO;
import com.chatop.portal.dto.MessagesMapper;
import com.chatop.portal.entity.Messages;
import com.chatop.portal.entity.Users;
import com.chatop.portal.repository.MessagesRepository;
import com.chatop.portal.repository.UsersRepository;

// Define the annotated class as a spring service
@Service
public class MessagesService {

    // Inject the message repository
    @Autowired
    private MessagesRepository messagesRepository;
    @Autowired UsersRepository usersRepository;

    // Inject the mapper to convert between DTO and entity
    @Autowired
    private MessagesMapper messagesMapper;

    // Add a new message
    public ResponseEntity<Map<String, Object>> addMessage(MessagesDTO messageDTO) {
        try {
            if(messageDTO.getUser_id() == null || messageDTO.getUser_id() == 0) {
                Users currentUser = usersRepository.findByEmail(
                    SecurityContextHolder.getContext().getAuthentication().getName()
                ).orElseThrow(() -> new UsernameNotFoundException("User not found"));

                messageDTO.setUser_id(currentUser.getId()); // Set the owner ID to the current user
            }
            // Mapping from DTO to the messages entity
            Messages message = messagesMapper.toEntity(messageDTO);
            // Save the message entity to the repository
            messagesRepository.save(message);
            
            // Return a HTTP code 200 : success
            return ResponseEntity.ok(Collections.singletonMap("message", "Message sent successfully"));

        } catch (Exception e) {
            // Return a HTTP code 401 Unauthorized
            return new ResponseEntity<>(Collections.singletonMap("message", "Error occurred while sending message"), HttpStatus.UNAUTHORIZED);
        }
    }
}
