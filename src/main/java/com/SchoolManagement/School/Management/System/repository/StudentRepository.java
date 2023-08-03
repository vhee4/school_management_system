package com.SchoolManagement.School.Management.System.repository;

import com.SchoolManagement.School.Management.System.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student,Long> {
    boolean existsByEmail(String email);
    Optional<Student> findByEmail(String email);
    Optional<Student> findByStudentId(String studentId);

    boolean existsByPhoneNumber(String phoneNumber);
//    Optional<StudentEntity> findByStaffId(String staffId);
}
