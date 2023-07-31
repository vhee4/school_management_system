package com.SchoolManagement.School.Management.System.service;

import com.SchoolManagement.School.Management.System.dtos.CustomResponse;
import org.springframework.http.ResponseEntity;

public interface AdminService {
    ResponseEntity<CustomResponse> enableStudentAccount(String studentId);
}
