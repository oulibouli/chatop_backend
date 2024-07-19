package com.chatop.portal.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.chatop.portal.model.Users;

@Repository // Declare this interface as a repository
public interface UsersRepository extends CrudRepository<Users, Long> {
    public Users findByUsername(@Param("name") String username);
}
