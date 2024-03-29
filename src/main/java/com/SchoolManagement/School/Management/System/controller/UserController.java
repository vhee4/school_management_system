package com.SchoolManagement.School.Management.System.controller;

import com.SchoolManagement.School.Management.System.dtos.UserRequest;
import com.SchoolManagement.School.Management.System.dtos.CustomResponse;
import com.SchoolManagement.School.Management.System.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<CustomResponse> signUpForStudents(@RequestBody UserRequest userRequest) {
        return new ResponseEntity<>(userService.signUpForStudents(userRequest), HttpStatus.CREATED);
    }


}
