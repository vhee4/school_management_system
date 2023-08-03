package com.SchoolManagement.School.Management.System.repository;

import com.SchoolManagement.School.Management.System.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StaffRepository extends JpaRepository<Staff,Long> {
    boolean existsByEmail(String email);
    Optional<Staff> findByEmail(String email);
    Optional<Staff> findByStaffId(String staffId);

    boolean existsByPhoneNumber(String phoneNumber);
}
