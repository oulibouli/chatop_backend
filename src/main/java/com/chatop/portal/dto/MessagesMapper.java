package com.chatop.portal.dto;

import org.springframework.stereotype.Component;

import com.chatop.portal.entity.Messages;

@Component
public class MessagesMapper {
    public Messages toEntity(MessagesDTO messageDTO) {
        Messages message = new Messages();

        message.setMessage(messageDTO.getMessage());
        message.setRental_id(messageDTO.getRental_id());
        message.setUser_id(messageDTO.getUser_id());

        message.setCreated_at(messageDTO.getCreated_at());
        message.setUpdated_at(messageDTO.getUpdated_at());

        return message;
    }
}
