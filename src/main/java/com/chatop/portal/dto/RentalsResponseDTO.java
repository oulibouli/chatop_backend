package com.chatop.portal.dto;

import java.util.List;

public class RentalsResponseDTO {
    private List<RentalDTO> rentals;

    // Getters and Setters
    public List<RentalDTO> getRentals() {
        return rentals;
    }

    public void setRentals(List<RentalDTO> rentals) {
        this.rentals = rentals;
    }
}