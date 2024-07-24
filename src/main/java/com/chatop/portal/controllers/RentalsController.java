package com.chatop.portal.controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.portal.dto.RentalDTO;
import com.chatop.portal.dto.RentalsResponseDTO;
import com.chatop.portal.services.RentalsService;

@RestController
@RequestMapping("/api/rentals")
public class RentalsController {

    @Autowired
    private RentalsService rentalsService;

    @GetMapping
    public RentalsResponseDTO getAllRentals() {
        List<RentalDTO> rentalDTOs = rentalsService.getAllRentals();
        RentalsResponseDTO response = new RentalsResponseDTO();
        response.setRentals(rentalDTOs);
        return response;
    }
}
