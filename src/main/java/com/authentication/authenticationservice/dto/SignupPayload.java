package com.authentication.authenticationservice.dto;

import com.authentication.authenticationservice.constants.ERole;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupPayload {

    private String username;
    private String email;
    private String password;
    private ERole role;
}