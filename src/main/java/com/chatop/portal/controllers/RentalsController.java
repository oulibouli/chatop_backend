package com.chatop.portal.controllers;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<ApiResponse> addRental(@ModelAttribute RentalDTO rentalDTO) {
        Timestamp timestamp = Timestamp.from(Instant.now());

        Rentals rental = new Rentals();

        // Get the currently logged-in user's email
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName(); // User email as principal

        // Find the user by email
        Users currentUser = usersRepository.findByEmail(currentUserEmail).orElse(null);
        
        // Set the owner's ID
        rental.setOwnerId(currentUser.getId());

        rental.setName(rentalDTO.getName());
        rental.setSurface(rentalDTO.getSurface());
        rental.setPrice(rentalDTO.getPrice());
        rental.setPicture(rentalDTO.getPicture());
        rental.setDescription(rentalDTO.getDescription());
        rental.setOwnerId(currentUser.getId());
        rental.setCreated_at(timestamp);
        rental.setUpdated_at(timestamp);

        rentalsService.addRental(rental);

        ApiResponse apiResponse = new ApiResponse("Rental created !");
        
        return ResponseEntity.ok(apiResponse);
    }
}
