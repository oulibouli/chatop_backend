package com.chatop.portal.controllers;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.portal.dto.RentalDTO;
import com.chatop.portal.dto.RentalMapper;
import com.chatop.portal.dto.RentalsResponseDTO;
import com.chatop.portal.entity.Rentals;
import com.chatop.portal.services.RentalsService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/rentals")
public class RentalsController {

    @Autowired
    private RentalsService rentalsService;
    private final RentalMapper rentalMapper = new RentalMapper();

    @GetMapping
    public RentalsResponseDTO getAllRentals() {
        List<RentalDTO> rentalDTOs = rentalsService.getAllRentals();
        RentalsResponseDTO response = new RentalsResponseDTO();
        response.setRentals(rentalDTOs);
        return response;
    }
    @Operation(summary="Get the rental by id")
    @GetMapping("/{id}")
    public ResponseEntity<RentalDTO> getRental(@PathVariable("id") int id) {
        Optional<Rentals> rentalOpt = rentalsService.getRental(id);
        if (rentalOpt.isPresent()) {
            RentalDTO rentalDTO = rentalMapper.toDTO(rentalOpt.get());
            return new ResponseEntity<>(rentalDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
