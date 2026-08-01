package com.jeancmr.library_management.security.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtAuthResponseDto {
    private String accessToken;
    private String tokenType = "Bearer";

    public JwtAuthResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }
}