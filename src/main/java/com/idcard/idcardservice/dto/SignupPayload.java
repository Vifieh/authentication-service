package com.idcard.idcardservice.dto;

import com.idcard.idcardservice.constants.ERole;
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