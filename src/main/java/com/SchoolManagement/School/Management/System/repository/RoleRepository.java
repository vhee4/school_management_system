package com.SchoolManagement.School.Management.System.repository;

import com.SchoolManagement.School.Management.System.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Roles,Long> {
    Roles findByRoleName(String role);
}
