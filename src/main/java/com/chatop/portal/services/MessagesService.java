package com.chatop.portal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatop.portal.entity.Messages;
import com.chatop.portal.repository.MessagesRepository;

@Service
public class MessagesService {

    @Autowired
    private MessagesRepository messagesRepository;

    public Messages addMessage(Messages message) {
        return messagesRepository.save(message);
    }
}
