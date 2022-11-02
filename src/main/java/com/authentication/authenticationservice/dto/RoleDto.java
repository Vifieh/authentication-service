package com.authentication.authenticationservice.dto;

import com.authentication.authenticationservice.constants.ERole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {

    private String id;
    private ERole role;

}
