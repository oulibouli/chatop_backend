package com.chatop.portal.controllers;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chatop.portal.model.Rentals;
import com.chatop.portal.services.RentalsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name="Rentals")
@Controller // Declare this class as a controller
@RequestMapping(path= "/api/rentals")  //URL start with /rentals
public class RentalsController {
    @Autowired
    private RentalsService rentalsService;

    @GetMapping("/") // Map this parameter to the method
    public @ResponseBody Iterable<Rentals> getRentals() {
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        System.out.println("User: " + currentPrincipalName);
        System.out.println(authorities);
        for (GrantedAuthority authority : authorities) {
            System.out.println("Role: " + authority.getAuthority());
        }

        return rentalsService.getAllRentals();
    }

    @Operation(summary="Get the rental by id")
    @GetMapping("/{id}")
    public ResponseEntity<Rentals> getRental(@PathVariable("id") Long id) {
        Optional<Rentals> Optionalrental = rentalsService.getRental(id);
        return Optionalrental
            .map(rental -> ResponseEntity.ok(rental))
            .orElseGet(() -> ResponseEntity.notFound().build());

    }

    // @PostMapping("/")
    // public Rentals addRentals(
    //     @RequestParam("name") String name,
    //     @RequestParam("surface") int surface,
    //     @RequestParam("price") int price,
    //     @RequestParam(value="picture", required=false) MultipartFile picture,
    //     @RequestParam("description") String description,
    //     @RequestParam("owner_id") int owner_id) {      
        
    //         Rentals rental = new Rentals();
    //         rental.setName(name);
    //         rental.setSurface(surface);
    //         rental.setPrice(price);
    //         rental.setDescription(description);
    //         rental.setOwnerId(owner_id);
    //         if(picture != null) {
    //             rental.setPicture(picture.getOriginalFilename());
    //         }
    //         rental.setCreated_at(LocalDateTime.now());
    //         return rentalsService.addRental(rental);
    // }
    @PostMapping(path = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Rentals addRental(@ModelAttribute Rentals rental) {
        return rentalsService.addRental(rental);
    }
    

    // @DeleteMapping("/{id}")
    // public @ResponseBody void deleteRental(@PathVariable Long id) {
    //     rentalsService.deleteRentalById(id);
    // }

    // @PatchMapping("/{id}")
    // public ResponseEntity<Rentals> partialUpdateRental(@PathVariable Long id, @RequestBody Rentals rental){

    // }

    // @PutMapping("/{id}") // Replace the entire Rentals
    // public ResponseEntity<Rentals> updateRental(@PathVariable Long id, @RequestBody Rentals rental) {        
    //     return rentalsService.updateRental(id, rental);
    // }
}
