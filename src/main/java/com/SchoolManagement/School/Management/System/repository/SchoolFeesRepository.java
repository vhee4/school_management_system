package com.SchoolManagement.School.Management.System.repository;

import com.SchoolManagement.School.Management.System.entity.Department;
import com.SchoolManagement.School.Management.System.entity.Fees;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SchoolFeesRepository extends JpaRepository<Fees, Long> {
    Optional<Fees> findByDepartment(Department department);
    Optional<Fees> findByAmount(double amount);
}
