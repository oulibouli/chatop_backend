package com.chatop.portal.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.chatop.portal.model.Rentals;

@Repository // Declare this interface as a repository
public interface RentalsRepository extends CrudRepository<Rentals, Long> {

}
