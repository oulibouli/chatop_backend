package com.chatop.portal.services;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.chatop.portal.dto.ApiResponse;
import com.chatop.portal.dto.RentalDTO;
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

    @Value("${file.upload-dir}")
    private String uploadDir;
    @Value("${app.baseUrl}")
    private String baseUrl;

    public List<RentalDTO> getAllRentals() {
        return ((List<Rentals>) rentalsRepository.findAll()).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public Rentals getRental(int id) {
        return rentalsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Rental not found"));
    }

    public ResponseEntity<ApiResponse> addRental(RentalDTO rentalDTO) {


        Timestamp timestamp = Timestamp.from(Instant.now());
        Rentals rental = new Rentals();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();
        Users currentUser = usersRepository.findByEmail(currentUserEmail).orElse(null);
        
        if (currentUser == null) {
            return new ResponseEntity<>(new ApiResponse("User not found"), HttpStatus.UNAUTHORIZED);
        }

        rental.setName(rentalDTO.getName());
        rental.setSurface(rentalDTO.getSurface());
        rental.setPrice(rentalDTO.getPrice());
        rental.setDescription(rentalDTO.getDescription());
        rental.setOwnerId(currentUser.getId());
        rental.setCreated_at(timestamp);
        rental.setUpdated_at(timestamp);


        if (rentalDTO.getPictureFile() != null && !rentalDTO.getPictureFile().isEmpty()) {
            try {
                String pictureUrl = savePictureFile(rentalDTO.getPictureFile());
                rental.setPicture(pictureUrl);
            } catch (IOException e) {
                return new ResponseEntity<>(new ApiResponse("Error saving picture file"), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            rental.setPicture(""); // Set a default value or leave empty if no file is provided
        }
        rentalsRepository.save(rental);
        return ResponseEntity.ok(new ApiResponse("Rental created successfully!"));
    }

    public RentalDTO updateRental(int id, RentalDTO rentalDTO) {
        Timestamp timestamp = Timestamp.from(Instant.now());
        Rentals rental = getRental(id);

        rental.setName(rentalDTO.getName());
        rental.setDescription(rentalDTO.getDescription());
        rental.setSurface(rentalDTO.getSurface());
        rental.setPrice(rentalDTO.getPrice());
        rental.setUpdated_at(timestamp);

        rentalsRepository.save(rental);
        return rentalDTO;
    }

    private RentalDTO convertToDTO(Rentals rental) {
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
