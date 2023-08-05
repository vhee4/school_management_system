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

    @PostMapping("/add-fee")
    public ResponseEntity<CustomResponse> addSchoolFees(@RequestParam("department_name") String departmentName, @RequestParam("amount") double fee){
        return adminService.addSchoolFees(departmentName, fee);
    }
}
