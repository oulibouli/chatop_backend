package com.chatop.portal.entity;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data // Automatically define the getters and setters
@Entity // Define the class as an entity
public class Users implements UserDetails {
    
    @Id // Primary key in the table
    @GeneratedValue(strategy= GenerationType.IDENTITY) // Generates the id automatically by the BD
    private Integer id;
    private String email;
    private String name;
    private String password;
    private String role;
    private Timestamp created_at;
    private Timestamp updated_at;

    // Get the authorization role for the user
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        
        return List.of(new SimpleGrantedAuthority(role)); // Return a list of roles
    }
    @Override
    public String getUsername() {
        return email;
    }
}
