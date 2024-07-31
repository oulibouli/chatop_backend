package com.chatop.portal.dto;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.chatop.portal.entity.Rentals;

// Spring-managed component.
@Component
public class RentalMapper {

    // Converts a Rentals entity to a RentalDTO object.
    public RentalDTO toDTO(Rentals rental) {
        RentalDTO dto = new RentalDTO();
        
        // Mapping fields from Rentals entity to RentalDTO.
        dto.setId(rental.getId());
        dto.setName(rental.getName());
        dto.setSurface(rental.getSurface());
        dto.setPrice(rental.getPrice());
        dto.setPicture(rental.getPicture());
        dto.setDescription(rental.getDescription());
        dto.setOwnerId(rental.getOwner_id());
        dto.setCreated_at(rental.getCreated_at());
        dto.setUpdated_at(rental.getUpdated_at());
        
        return dto; // Returning the created RentalDTO.
    }
    
    // Converts parameters to a RentalDTO object.
    public RentalDTO toDTOParams(String name, double surface, double price, String description, MultipartFile pictureFile) {
        RentalDTO rentalDTO = new RentalDTO();
        
        // Setting properties of RentalDTO from parameters.
        rentalDTO.setName(name);
        rentalDTO.setSurface(surface);
        rentalDTO.setPrice(price);
        rentalDTO.setDescription(description);
        rentalDTO.setPictureFile(pictureFile);
        
        return rentalDTO; // Returning the created RentalDTO.
    }

    // Converts a RentalDTO object to a Rentals entity.
    public Rentals toEntity(RentalDTO rentalDTO) {
        Rentals rental = new Rentals();
        
        // Mapping fields from RentalDTO to Rentals entity.
        rental.setName(rentalDTO.getName());
        rental.setSurface(rentalDTO.getSurface());
        rental.setPrice(rentalDTO.getPrice());
        rental.setPicture(rentalDTO.getPicture());
        rental.setDescription(rentalDTO.getDescription());
        rental.setCreated_at(rentalDTO.getCreated_at());
        rental.setUpdated_at(rentalDTO.getUpdated_at());
        
        return rental; // Returning the created Rentals entity.
    }

    // Updates an existing Rentals entity from a RentalDTO object.
    public void updateEntityFromDto(Rentals existingRental, RentalDTO rentalDTO) {
        
        // Checking and updating individual fields if they are not null or zero.
        if (rentalDTO.getName() != null) {
            existingRental.setName(rentalDTO.getName());
        }
        if (rentalDTO.getDescription() != null) {
            existingRental.setDescription(rentalDTO.getDescription());
        }
        if (rentalDTO.getSurface() != 0) {
            existingRental.setSurface(rentalDTO.getSurface());
        }
        if (rentalDTO.getPrice() != 0) {
            existingRental.setPrice(rentalDTO.getPrice());
        }
        if (rentalDTO.getPicture() != null) {
            existingRental.setPicture(rentalDTO.getPicture());
        }
        
        // Setting the updated_at field to the current time.
        existingRental.setUpdated_at(rentalDTO.getUpdated_at());
    }
}