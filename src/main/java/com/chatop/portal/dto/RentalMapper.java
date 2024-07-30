package com.chatop.portal.dto;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.chatop.portal.entity.Rentals;

@Component
public class RentalMapper {
// *** MAPPERS *** //
    // Convert Rentals to RentalDTO
    public RentalDTO toDTO(Rentals rental) {
        RentalDTO dto = new RentalDTO();
        dto.setId(rental.getId());
        dto.setName(rental.getName());
        dto.setSurface(rental.getSurface());
        dto.setPrice(rental.getPrice());
        dto.setPicture(rental.getPicture());
        dto.setDescription(rental.getDescription());
        dto.setOwnerId(rental.getOwner_id());
        dto.setCreated_at(rental.getCreated_at());
        dto.setUpdated_at(rental.getUpdated_at());
        return dto;
    }
    
    public RentalDTO toDTOParams(String name, double surface, double price, String description, MultipartFile pictureFile) {
        RentalDTO rentalDTO = new RentalDTO();
        rentalDTO.setName(name);
        rentalDTO.setSurface(surface);
        rentalDTO.setPrice(price);
        rentalDTO.setDescription(description);
        rentalDTO.setPictureFile(pictureFile);
        return rentalDTO;
    }

    // Convert RentalDTO to Rentals
    public Rentals toEntity(RentalDTO rentalDTO) {
        Rentals rental = new Rentals();
        rental.setName(rentalDTO.getName());
        rental.setSurface(rentalDTO.getSurface());
        rental.setPrice(rentalDTO.getPrice());
        rental.setPicture(rentalDTO.getPicture());
        rental.setDescription(rentalDTO.getDescription());
        rental.setCreated_at(rentalDTO.getCreated_at());
        rental.setUpdated_at(rentalDTO.getUpdated_at());

        return rental;
    }

    public void updateEntityFromDto(Rentals existingRental, RentalDTO rentalDTO) {
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
        existingRental.setUpdated_at(rentalDTO.getUpdated_at());
    }
}
