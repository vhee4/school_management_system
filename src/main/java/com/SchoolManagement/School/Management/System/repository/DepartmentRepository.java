package com.SchoolManagement.School.Management.System.repository;

import com.SchoolManagement.School.Management.System.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department,Long> {
    Optional<Department> findByName(String department);
}
