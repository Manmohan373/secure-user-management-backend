package com.example.secureusermanagement.dto;

import lombok.Data;

@Data
public class TokenResponse {
    private String access_token;
    private String refresh_token;
    private String token_type;
    private long expires_in;
    private long refresh_expires_in;
}
