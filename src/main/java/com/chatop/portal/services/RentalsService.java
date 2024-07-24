package com.chatop.portal.services;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatop.portal.dto.RentalDTO;
import com.chatop.portal.entity.Rentals;
import com.chatop.portal.repository.RentalsRepository;

@Service
public class RentalsService {
    @Autowired
    private RentalsRepository rentalsRepository;

    public List<RentalDTO> getAllRentals() {
        return ((List<Rentals>) rentalsRepository.findAll()).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public Optional<RentalDTO> getRental(Long id) {
        return rentalsRepository.findById(id)
            .map(this::convertToDTO);
    }

    public RentalDTO addRental(Rentals rental) {
        Rentals savedRental = rentalsRepository.save(rental);
        return convertToDTO(savedRental);
    }

    public RentalDTO updateRental(Rentals rental) {
        Rentals savedRental = rentalsRepository.save(rental);
        return convertToDTO(savedRental);
    }

    public void deleteRental(Long id) {
        rentalsRepository.deleteById(id);
    }

    private RentalDTO convertToDTO(Rentals rental) {
        RentalDTO dto = new RentalDTO();
        dto.setId(rental.getId());
        dto.setName(rental.getName());
        dto.setSurface(rental.getSurface());
        dto.setPrice(rental.getPrice());
        dto.setPicture(rental.getPicture());
        dto.setDescription(rental.getDescription());
        dto.setOwnerId(rental.getOwnerId()); // Assurez-vous que ce champ correspond
        dto.setCreatedAt(rental.getCreated_at());
        dto.setUpdatedAt(rental.getUpdated_at());
        return dto;
    }
}
