package com.SchoolManagement.School.Management.System.controller;

import com.SchoolManagement.School.Management.System.dtos.CustomResponse;
import com.SchoolManagement.School.Management.System.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PatchMapping("/enable")
    public ResponseEntity<CustomResponse> enableStudentAccount(@RequestParam(name = "studentId") String studentId){
        return adminService.enableStudentAccount(studentId);
    }
}
