package com.SchoolManagement.School.Management.System.service.serviceImpl;

import com.SchoolManagement.School.Management.System.dtos.CustomResponse;
import com.SchoolManagement.School.Management.System.entity.Department;
import com.SchoolManagement.School.Management.System.entity.Fees;
import com.SchoolManagement.School.Management.System.entity.Student;
import com.SchoolManagement.School.Management.System.repository.DepartmentRepository;
import com.SchoolManagement.School.Management.System.repository.SchoolFeesRepository;
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
    private final StudentRepository studentRepository;
    private final DepartmentRepository departmentRepository;
    private final SchoolFeesRepository feesRepository;

    private static final String[] FEE_DESCRIPTION = {
            "Library fee",
            "Departmental due",
            "School charges",
            "Hostel fee"
    };
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

    @Override
    public ResponseEntity<CustomResponse> addSchoolFees(String departmentName, double fee) {
        Optional<Department> departmentOpt = departmentRepository.findByName(departmentName);
        if(departmentOpt.isEmpty()){
            return ResponseEntity.badRequest().body(new CustomResponse(HttpStatus.NO_CONTENT.name(), "No department with this name found!"));
        }
        if(departmentName==null){
            return ResponseEntity.badRequest().body(new CustomResponse(HttpStatus.NO_CONTENT.name(), "Provide department name"));
        }
        if(fee==0){
            return ResponseEntity.badRequest().body(new CustomResponse(HttpStatus.BAD_REQUEST.name(), "Fee cannot be 0.0"));
        }
        if(feesRepository.findByDepartment(departmentOpt.get()).isPresent()){
            return ResponseEntity.badRequest().body(new CustomResponse(HttpStatus.FORBIDDEN.name(), "This department already have fee assigned to it"));
        }

        Fees fees = Fees.builder()
                .amount(fee)
                .department(departmentOpt.get())
                .feeDescription(FEE_DESCRIPTION.toString()).build();
        feesRepository.save(fees);
        if(fees!=null){
            return ResponseEntity.ok(new CustomResponse(HttpStatus.CREATED.name(),  "Successfully created school fee for "+ departmentName, fees));
        }
        return ResponseEntity.badRequest().body(new CustomResponse(HttpStatus.BAD_REQUEST.name(), "Something went wrong!"));
    }
}
