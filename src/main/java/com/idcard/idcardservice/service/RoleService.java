package com.idcard.idcardservice.service;


import com.idcard.idcardservice.constants.ERole;
import com.idcard.idcardservice.dto.RolePayload;
import com.idcard.idcardservice.model.Role;

import java.util.List;

/**
 * @author Vifieh Ruth
 * on 5/27/22
 */

public interface RoleService {

    void createRole(RolePayload rolePayload);

    void editRole(String roleId, RolePayload rolePayload);

    List<Role> getAllRoles();

    Role getRole(String roleId);

    Role getRoleByName(ERole roleName);

    void deleteRole(String roleId);

}
