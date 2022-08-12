package com.idcard.idcardservice.service.serviceImpl;

import com.idcard.idcardservice.constants.ERole;
import com.idcard.idcardservice.dto.RolePayload;
import com.idcard.idcardservice.exception.ResourceAlreadyExistException;
import com.idcard.idcardservice.exception.ResourceNotFoundException;
import com.idcard.idcardservice.model.Role;
import com.idcard.idcardservice.repository.RoleRepository;
import com.idcard.idcardservice.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Vifieh Ruth
 * on 5/27/22
 */

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public void createRole(RolePayload rolePayload) {
        Role role = new Role();
        Optional<Role> role1 = roleRepository.findByRole(role.getRole());
        if(role1.isPresent()) {
            throw new ResourceAlreadyExistException("Role already exist");
        }
        role.setRole(rolePayload.getRole());
        roleRepository.save(role);
    }

    @Override
    public void editRole(String roleId, RolePayload rolePayload) {
        Role role1 = getRole(roleId);
            role1.setRole(rolePayload.getRole());
            roleRepository.save(role1);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRole(String roleId) {
        Optional<Role> role = roleRepository.findById(UUID.fromString(roleId));
        role.orElseThrow(() -> new ResourceNotFoundException("Role not found with id - " + roleId));
        return role.get();
    }

    @Override
    public Role getRoleByName(ERole roleName) {
        Optional<Role> role = roleRepository.findByRole(roleName);
        role.orElseThrow(() -> new ResourceNotFoundException("Role not found with id - " + roleName));
        return role.get();
    }

    @Override
    public void deleteRole(String roleId) {
        roleRepository.deleteById(UUID.fromString(roleId));
    }
}
