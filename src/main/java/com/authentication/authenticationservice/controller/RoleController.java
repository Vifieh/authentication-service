package com.authentication.authenticationservice.controller;

import com.authentication.authenticationservice.dto.RolePayload;
import com.authentication.authenticationservice.service.serviceImpl.RoleServiceImpl;
import com.authentication.authenticationservice.dto.RoleDto;
import com.authentication.authenticationservice.model.Role;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("api/v1/public/")
@CrossOrigin
@RestController
@AllArgsConstructor
public class RoleController {

    private final RoleServiceImpl roleService;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping("roles")
    public ResponseEntity<?> createRole(@RequestBody RolePayload rolePayload) {
        roleService.createRole(rolePayload);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("roles/{roleId}")
    public ResponseEntity<?> editRole(@PathVariable("roleId") String roleId,
                                                       @RequestBody RolePayload rolePayload) {
        roleService.editRole(roleId, rolePayload);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("roles")
    public ResponseEntity<List<RoleDto>> getRoles() {
        List<Role> roles = roleService.getAllRoles();
        List<RoleDto> roleDTOS = roles.stream()
                .map(role -> this.modelMapper.map(role, RoleDto.class)).collect(Collectors.toList());
        return new ResponseEntity<>(roleDTOS, HttpStatus.OK);
    }

    @GetMapping("roles/{roleId}")
    public ResponseEntity<RoleDto> getRole(@PathVariable("roleId") String roleId) {
        Role role = roleService.getRole(roleId);
        RoleDto roleDto = this.modelMapper.map(role, RoleDto.class);
        return new ResponseEntity<>(roleDto, HttpStatus.OK);
    }

    @DeleteMapping("roles/{roleId}")
    public ResponseEntity<?> deleteRole(@PathVariable("roleId") String roleId) {
        roleService.deleteRole(roleId);
        return ResponseEntity.noContent().build();
    }

}
