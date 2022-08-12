package com.idcard.idcardservice.dto;

import com.idcard.idcardservice.constants.ERole;
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
