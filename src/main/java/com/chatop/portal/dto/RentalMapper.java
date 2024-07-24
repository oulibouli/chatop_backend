package com.chatop.portal.dto;

import org.springframework.stereotype.Component;

import com.chatop.portal.entity.Rentals;

@Component
public class RentalMapper {

    public RentalDTO toDTO(Rentals rental) {
        RentalDTO dto = new RentalDTO();
        dto.setId(rental.getId());
        dto.setName(rental.getName());
        dto.setSurface(rental.getSurface());
        dto.setPrice(rental.getPrice());
        dto.setPicture(rental.getPicture());
        dto.setDescription(rental.getDescription());
        dto.setOwnerId(rental.getOwnerId());
        dto.setCreatedAt(rental.getCreated_at());
        dto.setUpdatedAt(rental.getUpdated_at());
        return dto;
    }
}
