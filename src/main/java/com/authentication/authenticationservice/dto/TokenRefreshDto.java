package com.authentication.authenticationservice.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TokenRefreshDto {

    private final String accessToken;
    private final String refreshToken;
    private String tokenType = "Bearer";

}