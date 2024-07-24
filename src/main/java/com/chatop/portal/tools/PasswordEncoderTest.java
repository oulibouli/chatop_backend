package com.chatop.portal.tools;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "password"; // Le mot de passe en clair
        String encodedPassword = encoder.encode(rawPassword);

        System.out.println(encodedPassword);
    }
}