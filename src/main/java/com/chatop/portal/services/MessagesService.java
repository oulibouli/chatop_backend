package com.chatop.portal.services;

import java.sql.Timestamp;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatop.portal.dto.MessagesDTO;
import com.chatop.portal.entity.Messages;
import com.chatop.portal.repository.MessagesRepository;

@Service
public class MessagesService {

    @Autowired
    private MessagesRepository messagesRepository;

    public Messages addMessage(MessagesDTO messageDTO) {

        Timestamp timestamp = Timestamp.from(Instant.now());
        Messages message = new Messages();

        message.setMessage(messageDTO.getMessage());
        message.setRental_id(messageDTO.getRental_id());
        message.setUser_id(messageDTO.getUser_id());

        message.setCreated_at(timestamp);
        message.setUpdated_at(timestamp);

        return messagesRepository.save(message);
    }
}
