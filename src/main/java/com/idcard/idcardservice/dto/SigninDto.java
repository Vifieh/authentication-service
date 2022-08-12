package com.idcard.idcardservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * @author Vifieh Ruth
 * on 5/27/22
 */

@Data
@AllArgsConstructor
public class SigninDto {

    private final String accessToken;
    private final String refreshToken;
    private final Instant expiredIn;
    private String id;
    private String username;
    private String email;
    private final List<String> role;
    private String type = "Bearer";

    public SigninDto(String accessToken, String refreshToken, Instant expiredIn, String id, String username,
                     String email, List<String> role) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiredIn = expiredIn;
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }

//    public SigninDto(String jwt, String token, UUID id, Instant expiryDate, String username, List<String> roles) {
//    }
}