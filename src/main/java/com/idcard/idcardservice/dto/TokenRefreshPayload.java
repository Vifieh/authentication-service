package com.idcard.idcardservice.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TokenRefreshPayload {

    @NotBlank
    private String refreshToken;

}