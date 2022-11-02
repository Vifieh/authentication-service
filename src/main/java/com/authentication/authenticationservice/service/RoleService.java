package com.authentication.authenticationservice.service;


import com.authentication.authenticationservice.constants.ERole;
import com.authentication.authenticationservice.dto.RolePayload;
import com.authentication.authenticationservice.model.Role;

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
