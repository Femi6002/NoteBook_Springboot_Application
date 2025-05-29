package com.example.notebook.auth.repository;

import com.example.notebook.auth.model.AppRole;
import com.example.notebook.auth.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(AppRole appRoles);
}
