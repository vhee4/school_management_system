package com.SchoolManagement.School.Management.System.service.serviceImpl;

import com.SchoolManagement.School.Management.System.dtos.Data;
import com.SchoolManagement.School.Management.System.dtos.UserRequest;
import com.SchoolManagement.School.Management.System.dtos.UserResponse;
import com.SchoolManagement.School.Management.System.entity.AppUser;
import com.SchoolManagement.School.Management.System.entity.Roles;
import com.SchoolManagement.School.Management.System.repository.RoleRepository;
import com.SchoolManagement.School.Management.System.repository.UserRepository;
import com.SchoolManagement.School.Management.System.service.UserService;
import com.SchoolManagement.School.Management.System.utils.IDGenerator;
import com.SchoolManagement.School.Management.System.utils.Response;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    public final UserRepository userRepository;
    public final RoleRepository roleRepository;

    @Override
    public UserResponse signUpForStudents(UserRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            return UserResponse.builder()
                    .responseCode(Response.USER_EXISTS_CODE)
                    .responseMessage(Response.USER_EXISTS_MESSAGE)
                    .build();
        }

        AppUser user = AppUser.builder()
                .email(IDGenerator.generateEmail(userRequest.getFirstName(), userRequest.getLastName()))
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .phoneNumber(userRequest.getPhoneNumber())
                .password(IDGenerator.generateDefaultPassword(IDGenerator.LENGTH_OF_PASSWORD))
                .studentId(IDGenerator.generateStudentID())
//                .roles(Collections.singleton(studentRoles))
                .build();

        Optional<Roles> roles = roleRepository.findByName("ROLE_STUDENT");
        Roles studentRoles = null;
        if (roles.isPresent()) {
            studentRoles = roles.get();
        } else {
            Roles defaultStudentRole = Roles.builder()
                    .name("ROLE_STUDENT")
                    .build();
            studentRoles = roleRepository.save(defaultStudentRole);
        }
user.setRoles(Collections.singleton(studentRoles));

        AppUser savedUser = userRepository.save(user);

        return UserResponse.builder()
                .responseCode(Response.SUCCESS)
                .responseMessage(Response.USER_REGISTERED_SUCCESS)
                .data(Data.builder()
                        .id(savedUser.getId())
                        .sId(savedUser.getStudentId())
                        .email(savedUser.getEmail())
                        .password(savedUser.getPassword())
                        .build())
                .build();
    }

    @Override
    public ResponseEntity<UserResponse> signUpForStaffs(UserRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            return new ResponseEntity<>(UserResponse.builder()
                    .responseCode(Response.USER_EXISTS_CODE)
                    .responseMessage(Response.USER_EXISTS_MESSAGE)
                    .build(), HttpStatus.CONFLICT);
        }
        AppUser user = AppUser.builder()
                .email(IDGenerator.generateEmail(userRequest.getFirstName(), userRequest.getLastName()))
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .phoneNumber(userRequest.getPhoneNumber())
                .password(IDGenerator.generateDefaultPassword(IDGenerator.LENGTH_OF_PASSWORD))
                .studentId(IDGenerator.generateStaffID())
                .build();
//        Roles role = roleRepository.findByRoleName("USER");
//        user.setRoles(Collections.singleton(role));

        AppUser savedUser = userRepository.save(user);

        UserResponse response = UserResponse.builder()
                .responseCode(Response.SUCCESS)
                .responseMessage(Response.USER_REGISTERED_SUCCESS)
                .data(Data.builder()
                        .id(savedUser.getId())
                        .sId(savedUser.getStaffId())
                        .email(savedUser.getEmail())
                        .password(savedUser.getPassword())
                        .build())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    public ResponseEntity<List<UserResponse>> getAllStudents() {
        List<AppUser> users = userRepository.findAll();
        List<UserResponse> responses = users.stream().map(allStudents -> UserResponse.builder()
                .responseCode(Response.SUCCESS)
                .responseMessage(Response.SUCCESS_MESSAGE)
                .data(Data.builder()
                        .id(allStudents.getId())
                        .sId(allStudents.getStudentId())
                        .email(allStudents.getEmail())
                        .build())
                .build()).toList();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    public ResponseEntity<List<UserResponse>> getAllStaffs() {
        List<AppUser> users = userRepository.findAll();
        List<UserResponse> responses = users.stream().map(allStaffs -> UserResponse.builder()
                .responseCode(Response.SUCCESS)
                .responseMessage(Response.SUCCESS_MESSAGE)
                .data(Data.builder()
                        .id(allStaffs.getId())
                        .sId(allStaffs.getStaffId())
                        .email(allStaffs.getEmail())
                        .build())
                .build()).toList();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}
