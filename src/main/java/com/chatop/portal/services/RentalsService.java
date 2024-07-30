package com.chatop.portal.services;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.chatop.portal.dto.RentalDTO;
import com.chatop.portal.dto.RentalMapper;
import com.chatop.portal.entity.Rentals;
import com.chatop.portal.entity.Users;
import com.chatop.portal.exception.NotFoundException;
import com.chatop.portal.repository.RentalsRepository;
import com.chatop.portal.repository.UsersRepository;

@Service
public class RentalsService {
    @Autowired
    private RentalsRepository rentalsRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private RentalMapper rentalMapper;

    @Value("${file.upload-dir}")
    private String uploadDir;
    @Value("${app.baseUrl}")
    private String baseUrl;

    public Map<String, Object> getAllRentals() {
        List<Rentals> rentals = rentalsRepository.findAll();
        Map<String, Object> response = new HashMap<>();
        response.put("rentals", rentals);
        return response;
    }

    public RentalDTO getRental(int id) {
        RentalDTO rentalDTO = rentalMapper.toDTO(rentalsRepository.findById(id).orElseThrow(() -> new NotFoundException("Rental not found")));

        return rentalDTO;
    }

    public ResponseEntity<String> addRental(RentalDTO rentalDTO) {
        Users currentUser = usersRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElse(null);
        
        if (currentUser == null) {
            return new ResponseEntity<>("User not found", HttpStatus.UNAUTHORIZED);
        }

        if (rentalDTO.getPictureFile() != null && !rentalDTO.getPictureFile().isEmpty()) {
            try {
                rentalDTO.setPicture(savePictureFile(rentalDTO.getPictureFile()));
            } catch (IOException e) {
                return new ResponseEntity<>("Error saving picture file", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            rentalDTO.setPicture(""); // Set a default value or leave empty if no file is provided
        }
        Rentals rental = rentalMapper.toEntity(rentalDTO);
        rental.setOwner_id(currentUser.getId());

        rentalsRepository.save(rental);
        return ResponseEntity.ok("Rental created successfully!");
    }

    public ResponseEntity<String> updateRental(int id, RentalDTO rentalDTO) {
        try {
            Rentals existingRental = rentalsRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Rental not found"));

            rentalMapper.updateEntityFromDto(existingRental, rentalDTO);
            
            rentalsRepository.save(existingRental);

            return ResponseEntity.ok("Rental updated successfully!");
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating rental: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String savePictureFile(MultipartFile pictureFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        // Check if directory exists otherwise creating it
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
    
        String originalFilename = pictureFile.getOriginalFilename();
        String filename = System.currentTimeMillis() + "_" + originalFilename;
        Path filePath = uploadPath.resolve(filename);
    
        Files.copy(pictureFile.getInputStream(), filePath);
        return baseUrl + "/images/" + filename; // Returning filename only; you can adjust this as needed
    }
}
