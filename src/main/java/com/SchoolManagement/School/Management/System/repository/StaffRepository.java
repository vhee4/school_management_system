package com.SchoolManagement.School.Management.System.repository;

import com.SchoolManagement.School.Management.System.entity.AppUser;
import com.SchoolManagement.School.Management.System.entity.StaffEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StaffRepository extends JpaRepository<StaffEntity,Long> {
    boolean existsByEmail(String email);
    Optional<StaffEntity> findByEmail(String email);
    Optional<StaffEntity> findByStaffId(String staffId);

    boolean existsByPhoneNumber(String phoneNumber);
}
