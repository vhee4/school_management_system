package com.SchoolManagement.School.Management.System.service;

import com.SchoolManagement.School.Management.System.dtos.UserRequest;
import com.SchoolManagement.School.Management.System.dtos.UserResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    public UserResponse signUpForStudents(UserRequest userRequest);
        ResponseEntity<UserResponse> signUpForStaffs(UserRequest userRequest);

    ResponseEntity<List<UserResponse>> getAllStudents();
    ResponseEntity<List<UserResponse>> getAllStaffs();


}
