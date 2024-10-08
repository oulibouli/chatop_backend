package com.chatop.portal.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.chatop.portal.dto.RentalDTO;
import com.chatop.portal.dto.RentalMapper;
import com.chatop.portal.entity.Rentals;
import com.chatop.portal.entity.Users;
import com.chatop.portal.repository.RentalsRepository;
import com.chatop.portal.repository.UsersRepository;

import jakarta.persistence.EntityNotFoundException;

// Declare the class as a Spring service
@Service
public class RentalsService {

    @Autowired
    private RentalsRepository rentalsRepository; // Inject the rentals repo

    @Autowired
    private UsersRepository usersRepository; // Inject the users repo

    @Autowired
    private RentalMapper rentalMapper; // Inject the rental mapper for converting between DTO and entity

    @Value("${file.upload-dir}")
    private String uploadDir; // Directory for storing uploaded files

    @Value("${app.baseUrl}")
    private String baseUrl; // Base URL for accessing images

    // Get all rentals and returns them in a map
    public ResponseEntity<Map<String, Object>> getAllRentals() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Rentals> rentals = rentalsRepository.findAll();
            response.put("rentals", rentals);
        }
        catch (Exception e) {
            // Handle any other exceptions by setting the error details in the response
            response.put("message", "Error retrieving rentals: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get a rental by its ID and returns it as a RentalDTO
    public ResponseEntity<RentalDTO> getRental(int id) {
        RentalDTO rentalDTO = new RentalDTO();
        try {
            rentalDTO = rentalMapper.toDTO(rentalsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Rental not found")));
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(rentalDTO, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(rentalDTO, HttpStatus.OK);
    }

    // Adds a new rental with the provided RentalDTO
    public ResponseEntity<Map<String, Object>> addRental(RentalDTO rentalDTO) {
        try {
            // Gets the currently authenticated user
            Users currentUser = usersRepository.findByEmail(
                SecurityContextHolder.getContext().getAuthentication().getName()
            ).orElseThrow(() -> new UsernameNotFoundException("User not found"));       

            // If a picture file is provided, save it and set the file path
            if (rentalDTO.getPictureFile() != null && !rentalDTO.getPictureFile().isEmpty()) {
                try {
                    rentalDTO.setPicture(savePictureFile(rentalDTO.getPictureFile()));
                } catch (IOException e) {
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error saving picture file");
                }
            } else {
                rentalDTO.setPicture(""); // Set an empty picture path if no file is provided
            }

            Rentals rental = rentalMapper.toEntity(rentalDTO);
            rental.setOwner_id(currentUser.getId()); // Set the owner ID to the current user

            rentalsRepository.save(rental);
            return ResponseEntity.ok(Collections.singletonMap("message", "Rental created!"));

        }
        catch(UsernameNotFoundException e) {
            return new ResponseEntity<>(Collections.singletonMap("message", e.getMessage()), HttpStatus.NOT_FOUND);
        }
        catch(ResponseStatusException e) {
            return new ResponseEntity<>(Collections.singletonMap("message", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch(Exception e) {
            return new ResponseEntity<>(Collections.singletonMap("message", "Error creating the rental"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Updates an existing rental by its ID with the provided RentalDTO
    public ResponseEntity<Map<String, Object>> updateRental(int id, RentalDTO rentalDTO) {
        try {
            Rentals existingRental = rentalsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rental not found"));

            rentalMapper.updateEntityFromDto(existingRental, rentalDTO);
            rentalsRepository.save(existingRental);

            return ResponseEntity.ok(Collections.singletonMap("message", "Rental updated successfully!"));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating rental: " + e.getMessage());
        }
    }

    // Saves the picture file to the specified directory and returns the URL of the saved picture
    private String savePictureFile(MultipartFile pictureFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        // Check if the directory exists, if not, create it
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Generate a unique filename and save the file
        String originalFilename = pictureFile.getOriginalFilename();
        String filename = System.currentTimeMillis() + "_" + originalFilename;
        Path filePath = uploadPath.resolve(filename);

        Files.copy(pictureFile.getInputStream(), filePath);
        return baseUrl + "/images/" + filename; // Return the URL to access the image
    }
}
