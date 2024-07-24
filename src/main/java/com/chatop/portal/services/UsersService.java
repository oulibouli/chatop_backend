package com.chatop.portal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.chatop.portal.repository.UsersRepository;

@Service
public class UsersService implements UserDetailsService{

    @Autowired
    private UsersRepository ourUserRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return ourUserRepo.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}