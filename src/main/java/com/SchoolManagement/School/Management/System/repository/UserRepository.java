package com.SchoolManagement.School.Management.System.repository;

import com.SchoolManagement.School.Management.System.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser,Long> {
    boolean existsByEmail(String email);
    AppUser findByEmail(String email);
}
