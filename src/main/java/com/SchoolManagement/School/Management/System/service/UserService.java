package com.SchoolManagement.School.Management.System.service;

import com.SchoolManagement.School.Management.System.dtos.LoginRequest;
import com.SchoolManagement.School.Management.System.dtos.UserRequest;
import com.SchoolManagement.School.Management.System.dtos.CustomResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    CustomResponse signUpForStudents(UserRequest userRequest);
    CustomResponse signUpForStaffs(UserRequest userRequest);

    ResponseEntity<List<CustomResponse>> getAllStudents();
    ResponseEntity<List<CustomResponse>> getAllStaffs();

    ResponseEntity<CustomResponse> authenticateUser(LoginRequest loginRequest) throws Exception;


}
