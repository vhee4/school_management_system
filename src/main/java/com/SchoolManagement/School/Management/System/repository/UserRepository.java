package com.SchoolManagement.School.Management.System.repository;

import com.SchoolManagement.School.Management.System.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
