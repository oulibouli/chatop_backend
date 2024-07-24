package com.chatop.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatop.portal.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
