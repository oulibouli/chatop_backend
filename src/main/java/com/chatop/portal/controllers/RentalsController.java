package com.chatop.portal.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.chatop.portal.dto.RentalDTO;
import com.chatop.portal.dto.RentalMapper;
import com.chatop.portal.services.RentalsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

// Swagger annotation
@Tag(name = "Rentals")
@RestController
@RequestMapping("/api/rentals")
public class RentalsController {

    // Inject the rentals service
    @Autowired
    private RentalsService rentalsService;
    
    // Injection the rentalmapper to convert the entities in DTO
    @Autowired
    private RentalMapper rentalMapper;

    @Operation(summary = "Get rentals", description = "Get all the rentals", responses = 
    {
        @ApiResponse(responseCode = "200", description = "Message sent successfully", content = {@Content(mediaType = "application/json")}),
        @ApiResponse(responseCode = "401", description = "Error occurred while sending message", content = @Content),
    })
    // This method manages a GET HTTP request to request all rentals
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllRentals() {
        // Call the rentals service to get all the rentals
        return rentalsService.getAllRentals();
    }

    @Operation(summary = "Get a rental", description = "Get the rental by id", responses = 
    {
        @ApiResponse(responseCode = "200", description = "Message sent successfully", content = {@Content(mediaType = "application/json")}),
        @ApiResponse(responseCode = "404", description = "Rental not found", content = @Content),
    })
    // This method manages a GET HTTP request to request a rental per id
    @GetMapping("/{id}")
    public ResponseEntity<RentalDTO> getRental(@PathVariable("id") int id) {
        // Call the rentals service to get the rental per id
        return rentalsService.getRental(id);
    }

    @Operation(summary = "Update a rental", description = "Update the rental by id", requestBody = @RequestBody(content = @Content(mediaType = "multipart/form-data",
    schema = @Schema(implementation = RentalDTO.class))), responses = 
    {
        @ApiResponse(responseCode = "200", description = "Rental updated successfully!", content = {@Content(mediaType = "application/json")}),
        @ApiResponse(responseCode = "500", description = "Error updating rental", content = @Content),
    })
    // This method manages a PUT HTTP request to update a rental
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateRental(
        @PathVariable int id, // Variable id get from the url
        @ModelAttribute RentalDTO rentalDTO // Data model used to update
    ) {
        // Call the rentals service to update
        return rentalsService.updateRental(id, rentalDTO);
    }

    @Operation(summary = "Create a rental", description = "Create a new rental", responses = 
    {
        @ApiResponse(responseCode = "200", description = "Rental created!", content = {@Content(mediaType = "application/json")}),
        @ApiResponse(responseCode = "404", description = "Error saving picture file", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error creating the rental", content = @Content),
    })
    // This method manages a POST HTTP request with a MULTIPART_FORM_DATA content
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> addRental(
        @RequestParam("name") String name,
        @RequestParam("surface") int surface,
        @RequestParam("price") double price,
        @RequestParam("description") String description,
        @RequestParam("pictureFile") MultipartFile pictureFile // Image file
    ) {
        // Use the rental mapper to convert the parameters into a DTO
        RentalDTO rentalDTO = rentalMapper.toDTOParams(name, surface, price, description, pictureFile);
        // Call the rentals service to add a new rental DTO
        return rentalsService.addRental(rentalDTO);
    }
}
