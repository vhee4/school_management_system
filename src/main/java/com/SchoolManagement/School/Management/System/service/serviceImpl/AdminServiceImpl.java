package com.SchoolManagement.School.Management.System.service.serviceImpl;

import com.SchoolManagement.School.Management.System.dtos.CustomResponse;
import com.SchoolManagement.School.Management.System.entity.AppUser;
import com.SchoolManagement.School.Management.System.repository.UserRepository;
import com.SchoolManagement.School.Management.System.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    @Override
    public ResponseEntity<CustomResponse> enableStudentAccount(String studentId) {
        Optional<AppUser> existingStudent = userRepository.findByStudentId(studentId);
        if(existingStudent.isPresent()){
            AppUser student = AppUser.builder()
                    .isEnabled(true)
                    .build();
            userRepository.save(student);
            return ResponseEntity.ok(new CustomResponse(HttpStatus.OK.name(), "Successfully enabled student!"));
        }
        return ResponseEntity.badRequest().body(new CustomResponse(HttpStatus.BAD_REQUEST.name(), "Something went wrong!"));
    }
}
