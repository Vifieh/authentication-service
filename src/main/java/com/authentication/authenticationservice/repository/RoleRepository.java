package com.authentication.authenticationservice.repository;

import com.authentication.authenticationservice.constants.ERole;
import com.authentication.authenticationservice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Vifieh Ruth
 * on 5/27/22
 */

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    Optional<Role> findByRole(ERole role);
}
