package com.chatop.portal.dto;

import org.springframework.stereotype.Component;

import com.chatop.portal.entity.Messages;

// Spring-managed component.
@Component
public class MessagesMapper {
    
    // Converts a MessagesDTO object to a Messages entity.
    public Messages toEntity(MessagesDTO messageDTO) {
        Messages message = new Messages();
        
        // Mapping fields from messageDTO to Messages entity.
        message.setMessage(messageDTO.getMessage());
        message.setRental_id(messageDTO.getRental_id());
        message.setUser_id(messageDTO.getUser_id());
        message.setCreated_at(messageDTO.getCreated_at());
        message.setUpdated_at(messageDTO.getUpdated_at());

        return message; // Returning the created Messages entity.
    }
}