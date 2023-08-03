package com.SchoolManagement.School.Management.System.service.serviceImpl;

import com.SchoolManagement.School.Management.System.dtos.CustomResponse;
import com.SchoolManagement.School.Management.System.entity.Student;
import com.SchoolManagement.School.Management.System.repository.StaffRepository;
import com.SchoolManagement.School.Management.System.repository.StudentRepository;
import com.SchoolManagement.School.Management.System.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final StaffRepository staffRepository;
    private final StudentRepository studentRepository;
    @Override
    public ResponseEntity<CustomResponse> enableStudentAccount(String studentId) {
        Optional<Student> existingStudentOpt = studentRepository.findByStudentId(studentId);
        if (existingStudentOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(new CustomResponse(HttpStatus.BAD_REQUEST.name(), "No student found for this id"));
        }

        Student existingStudent = existingStudentOpt.get();
        existingStudent.setEnabled(true); // Set the isEnabled field to true

        studentRepository.save(existingStudent);
        return ResponseEntity.ok(new CustomResponse(HttpStatus.OK.name(), "Successfully enabled student!"));
    }
}
