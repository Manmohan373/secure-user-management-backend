package com.example.secureusermanagement.service;

import com.example.secureusermanagement.dto.LoginRequest;
import com.example.secureusermanagement.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;


@Service
@RequiredArgsConstructor
public class LoginService {

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    private final RestTemplate restTemplate = new RestTemplate();

    public TokenResponse login(LoginRequest request) {
//        String url = serverUrl + "/realms/" + realm + "/protocol/openid-connect/token";
        String url = "http://localhost:8080/realms/MyAppRealm/protocol/openid-connect/token";

        // Form parameters
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("username", request.getUsername());
        body.add("password", request.getPassword());

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        try {
            return restTemplate.postForObject(url, requestEntity, TokenResponse.class);
        } catch (HttpClientErrorException e) {
            // Map Keycloak errors to meaningful messages
            return handleKeycloakError(e);
        }    }



    private TokenResponse handleKeycloakError(HttpClientErrorException e) {
        String body = e.getResponseBodyAsString();
        int status = e.getRawStatusCode();

        if (status == 400 && body.contains("invalid_grant")) {
            throw new RuntimeException("Invalid username or password");
        } else if (status == 401) {
            throw new RuntimeException("Unauthorized: check client credentials");
        } else {
            throw new RuntimeException("Login failed: " + body);
        }
    }
}
