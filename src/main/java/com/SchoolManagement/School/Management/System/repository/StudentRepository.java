package com.SchoolManagement.School.Management.System.repository;

import com.SchoolManagement.School.Management.System.entity.AppUser;
import com.SchoolManagement.School.Management.System.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<StudentEntity,Long> {
    boolean existsByEmail(String email);
    Optional<StudentEntity> findByEmail(String email);
    Optional<StudentEntity> findByStudentId(String studentId);

    boolean existsByPhoneNumber(String phoneNumber);
//    Optional<StudentEntity> findByStaffId(String staffId);
}
