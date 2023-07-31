package com.SchoolManagement.School.Management.System.service.serviceImpl;

import com.SchoolManagement.School.Management.System.dtos.CustomResponse;
import com.SchoolManagement.School.Management.System.entity.AppUser;
import com.SchoolManagement.School.Management.System.repository.UserRepository;
import com.SchoolManagement.School.Management.System.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    @Override
    public ResponseEntity<CustomResponse> enableStudentAccount(String studentId) {
        Optional<AppUser> existingStudentOpt = userRepository.findByStudentId(studentId);
        if (existingStudentOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(new CustomResponse(HttpStatus.BAD_REQUEST.name(), "No student found for this id"));
        }

        AppUser existingStudent = existingStudentOpt.get();
        existingStudent.setEnabled(true); // Set the isEnabled field to true

        userRepository.save(existingStudent);
        return ResponseEntity.ok(new CustomResponse(HttpStatus.OK.name(), "Successfully enabled student!"));
    }
}
