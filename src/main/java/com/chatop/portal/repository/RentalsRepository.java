package com.chatop.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chatop.portal.entity.Rentals;

@Repository // Declare this interface as a repository
public interface RentalsRepository extends JpaRepository<Rentals, Integer> {

}
