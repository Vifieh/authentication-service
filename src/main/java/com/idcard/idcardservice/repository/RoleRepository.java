package com.idcard.idcardservice.repository;

import com.idcard.idcardservice.constants.ERole;
import com.idcard.idcardservice.model.Role;
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
