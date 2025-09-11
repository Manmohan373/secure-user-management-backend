package com.example.secureusermanagement.controller;

import com.example.secureusermanagement.dto.RegisterRequest;
import com.example.secureusermanagement.service.KeycloakService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@AllArgsConstructor
public class RegisterController {

    private final KeycloakService keycloakService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        try {
            keycloakService.createUser(request);
            return ResponseEntity.ok("User registered successfully in Keycloak!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }
}
