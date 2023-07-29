package com.SchoolManagement.School.Management.System.repository;

import com.SchoolManagement.School.Management.System.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Roles,Long> {
    Optional<Roles> findByName(String role);
}
