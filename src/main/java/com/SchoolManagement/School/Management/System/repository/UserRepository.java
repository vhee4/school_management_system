package com.SchoolManagement.School.Management.System.repository;

import com.SchoolManagement.School.Management.System.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser,Long> {
    boolean existsByEmail(String email);
    Optional<AppUser> findByEmail(String email);
    Optional<AppUser> findByStudentId(String studentId);
    Optional<AppUser> findByStaffId(String staffId);
}
