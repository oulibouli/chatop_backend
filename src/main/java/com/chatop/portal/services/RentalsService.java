package com.chatop.portal.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatop.portal.model.Rentals;
import com.chatop.portal.repository.RentalsRepository;

@Service // Declare this class as a service
public class RentalsService {
    @Autowired
    private RentalsRepository rentalsRepository;

    public Iterable<Rentals> getAllRentals() {
        return rentalsRepository.findAll();
    }

    public Optional<Rentals> getRental(Long id) {
        return rentalsRepository.findById(id);
    }

    public Rentals addRental(Rentals rental) {
        return rentalsRepository.save(rental);
    }

    public Rentals updateRental(Rentals rental) {
        Rentals savedRental = rentalsRepository.save(rental);
        return savedRental;
    }

    public void deleteRental(Long id) {
        rentalsRepository.deleteById(id);
    }
}
