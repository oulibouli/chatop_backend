package com.chatop.portal.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Rentals")
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
    private RentalMapper rentalMapper;

    @Operation(summary="Get all the rentals")
    @GetMapping
    public Map<String, Object> getAllRentals() {
        Map<String, Object> rentals = rentalsService.getAllRentals();

        return rentals;
    }

    @Operation(summary="Get the rental by id")
    @GetMapping("/{id}")
    public ResponseEntity<RentalDTO> getRental(@PathVariable("id") int id) {
        RentalDTO rentalDTO = rentalsService.getRental(id);
        
        return ResponseEntity.ok(rentalDTO);
    }

    @Operation(summary="Update a rental by id")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateRental(
        @PathVariable int id,
        @ModelAttribute RentalDTO rentalDTO
        ) 
    {
        ResponseEntity<String> response = rentalsService.updateRental(id, rentalDTO);
        return response;
    }

    @Operation(summary = "Create a new rental")
    @PostMapping(value="", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addRental(
        @RequestParam("name") String name,
        @RequestParam("surface") int surface,
        @RequestParam("price") double price,
        @RequestParam("description") String description,
        @RequestParam("picture") MultipartFile pictureFile
    )
    {

        RentalDTO rentalDTO = rentalMapper.toDTOParams(name, price, price, description, pictureFile);

        return rentalsService.addRental(rentalDTO);
    }
}
