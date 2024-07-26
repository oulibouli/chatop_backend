package com.chatop.portal.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.chatop.portal.dto.ApiResponse;
import com.chatop.portal.dto.RentalDTO;
import com.chatop.portal.dto.RentalMapper;
import com.chatop.portal.dto.RentalsResponseDTO;
import com.chatop.portal.entity.Rentals;
import com.chatop.portal.entity.Users;
import com.chatop.portal.repository.UsersRepository;
import com.chatop.portal.services.RentalsService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/rentals")
public class RentalsController {

    @Value("${file.upload-dir}")
    private String uploadDir;
    @Value("${app.baseUrl}")
    private String baseUrl;

    @Autowired
    private RentalsService rentalsService;

    @Autowired
    private UsersRepository usersRepository;

    private final RentalMapper rentalMapper = new RentalMapper();

    @Operation(summary="Get all the rentals")
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

    @Operation(summary = "Create a new rental")
    @PostMapping(value="", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public ResponseEntity<ApiResponse> addRental(
        @RequestParam("name") String name,
        @RequestParam("surface") int surface,
        @RequestParam("price") double price,
        @RequestParam("description") String description,
        @RequestParam("picture") MultipartFile pictureFile) {

    Timestamp timestamp = Timestamp.from(Instant.now());
    Rentals rental = new Rentals();

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentUserEmail = authentication.getName();
    Users currentUser = usersRepository.findByEmail(currentUserEmail).orElse(null);
    
    if (currentUser == null) {
        return new ResponseEntity<>(new ApiResponse("User not found"), HttpStatus.UNAUTHORIZED);
    }

    rental.setName(name);
    rental.setSurface(surface);
    rental.setPrice(price);
    rental.setDescription(description);
    rental.setOwnerId(currentUser.getId());
    rental.setCreated_at(timestamp);
    rental.setUpdated_at(timestamp);

    if (pictureFile != null && !pictureFile.isEmpty()) {
        try {
            String pictureUrl = savePictureFile(pictureFile);
            rental.setPicture(pictureUrl);
        } catch (IOException e) {
            return new ResponseEntity<>(new ApiResponse("Error saving picture file"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    } else {
        rental.setPicture(""); // Set a default value or leave empty if no file is provided
    }

    rentalsService.addRental(rental);
    return ResponseEntity.ok(new ApiResponse("Rental created successfully!"));
}

    private String savePictureFile(MultipartFile pictureFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
    
        String originalFilename = pictureFile.getOriginalFilename();
        String filename = System.currentTimeMillis() + "_" + originalFilename;
        Path filePath = uploadPath.resolve(filename);
    
        Files.copy(pictureFile.getInputStream(), filePath);
        return baseUrl + "/images/" + filename; // Returning filename only; you can adjust this as needed
    }
}
