package com.chatop.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chatop.portal.entity.Messages;

@Repository
public interface MessagesRepository extends JpaRepository<Messages, Integer>{

}
