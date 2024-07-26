package com.chatop.portal.controllers;

import java.util.List;

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

import com.chatop.portal.dto.ApiResponse;
import com.chatop.portal.dto.RentalDTO;
import com.chatop.portal.dto.RentalMapper;
import com.chatop.portal.dto.RentalsResponseDTO;
import com.chatop.portal.entity.Rentals;
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
    public RentalDTO getRental(@PathVariable("id") int id) {
        Rentals rentalOpt = rentalsService.getRental(id);
        RentalDTO rentalDTO = rentalMapper.toDTO(rentalOpt);
        return rentalDTO;
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateRental(
        @PathVariable int id,
        @ModelAttribute RentalDTO rentalDTO
        ) 
    {
        rentalsService.updateRental(id, rentalDTO);
        return ResponseEntity.ok(new ApiResponse("Rental created successfully!"));
    }

    @Operation(summary = "Create a new rental")
    @PostMapping(value="", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> addRental(
        @RequestParam("name") String name,
        @RequestParam("surface") int surface,
        @RequestParam("price") double price,
        @RequestParam("description") String description,
        @RequestParam("picture") MultipartFile pictureFile
        ) {

            RentalDTO rentalDTO = new RentalDTO();
            rentalDTO.setName(name);
            rentalDTO.setSurface(surface);
            rentalDTO.setPrice(price);
            rentalDTO.setDescription(description);
            rentalDTO.setPictureFile(pictureFile);

        ResponseEntity<ApiResponse> response = rentalsService.addRental(rentalDTO);
        return response;
    }
}
