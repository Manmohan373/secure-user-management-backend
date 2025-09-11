package com.example.secureusermanagement.controller;


import com.example.secureusermanagement.entity.User;
import com.example.secureusermanagement.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/api/user/me")
    public Map<String, Object> getUserProfile(@AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");
        Optional<User> user = userRepository.findByUsername(username);
        Map<String, Object> userInfo = new HashMap<>();

        userInfo.put("Id", user.get().getId() );
        userInfo.put("Username", username);
        userInfo.put("Email", user.get().getEmail() );
        userInfo.put("Address", user.get().getAddress() );
        userInfo.put("Salary", user.get().getSalary() );

        return userInfo;
    }
}