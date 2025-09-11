package com.example.secureusermanagement.controller;

import com.example.secureusermanagement.dto.LoginRequest;
import com.example.secureusermanagement.dto.TokenResponse;
import com.example.secureusermanagement.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        TokenResponse tokenResponse = loginService.login(request);
        return ResponseEntity.ok(tokenResponse);
    }
}
