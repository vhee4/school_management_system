package com.SchoolManagement.School.Management.System.service.serviceImpl;

import com.SchoolManagement.School.Management.System.dtos.*;
import com.SchoolManagement.School.Management.System.entity.AppUser;
import com.SchoolManagement.School.Management.System.entity.Roles;
import com.SchoolManagement.School.Management.System.exception.ApplicationAuthenticationException;
import com.SchoolManagement.School.Management.System.repository.RoleRepository;
import com.SchoolManagement.School.Management.System.repository.UserRepository;
import com.SchoolManagement.School.Management.System.security.CustomUserDetailService;
import com.SchoolManagement.School.Management.System.security.CustomUserDetails;
import com.SchoolManagement.School.Management.System.security.JWTTokenUtil;
import com.SchoolManagement.School.Management.System.service.UserService;
import com.SchoolManagement.School.Management.System.utils.IDGenerator;
import com.SchoolManagement.School.Management.System.utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    public final UserRepository userRepository;
    public final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailService userDetailsService;
    private final JWTTokenUtil jwtTokenUtil;
    String phoneNumberRegex = "(^$|(234\\d{10})|(\\d{11}))";//phone number accepts only 13 digits starting with 234


    @Override
    public CustomResponse signUpForStudents(UserRequest userRequest) {
        if(userRequest.getFirstName().isEmpty()){
            return CustomResponse.builder()
                    .responseCode(Response.BAD_REQUEST_CODE)
                    .responseMessage(Response.BAD_REQUEST_MESSAGE_FIRSTNAME)
                    .data(null)
                    .build();
        }
        if(userRequest.getLastName().isBlank()){
            return CustomResponse.builder()
                    .responseCode(Response.BAD_REQUEST_CODE)
                    .responseMessage(Response.BAD_REQUEST_MESSAGE_LASTNAME)
                    .data(null)
                    .build();
        }
        if(userRequest.getPhoneNumber().isBlank()){
            return CustomResponse.builder()
                    .responseCode(Response.BAD_REQUEST_CODE)
                    .responseMessage(Response.BAD_REQUEST_MESSAGE_PHONENUMBER)
                    .data(null)
                    .build();}
        if (!isInputValid(userRequest.getPhoneNumber(), phoneNumberRegex)) {
            return CustomResponse.builder()
                    .responseCode(Response.BAD_REQUEST_CODE)
                    .responseMessage(Response.BAD_REQUEST_MESSAGE_WRONG_PHONENUMBER_FORMAT)
                    .data(null)
                    .build();
        }

        Optional<Roles> roles = roleRepository.findByName("ROLE_STUDENT");
        Roles studentRole = null;
        if(!roles.isPresent()){
            Roles defaultRole = Roles.builder()
                    .name("ROLE_STUDENT")
                    .build();
            studentRole = roleRepository.save(defaultRole);
        }else{
            studentRole = roles.get();
        }


        AppUser user = AppUser.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .email(IDGenerator.generateEmail(userRequest.getFirstName(),userRequest.getLastName()))
                .phoneNumber(userRequest.getPhoneNumber())
                .password(passwordEncoder.encode(IDGenerator.generateDefaultPassword(userRequest.getFirstName())))
                .studentId(IDGenerator.generateStudentID())
                .roles(Collections.singleton(studentRole))
                .build();

        AppUser savedUser = userRepository.save(user);

        return CustomResponse.builder()
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
    public CustomResponse signUpForStaffs(UserRequest userRequest) {
        if(userRequest.getFirstName().isEmpty()){
            return CustomResponse.builder()
                    .responseCode(Response.BAD_REQUEST_CODE)
                    .responseMessage(Response.BAD_REQUEST_MESSAGE_FIRSTNAME)
                    .data(null)
                    .build();
        }
        if(userRequest.getLastName().isBlank()){
            return CustomResponse.builder()
                    .responseCode(Response.BAD_REQUEST_CODE)
                    .responseMessage(Response.BAD_REQUEST_MESSAGE_LASTNAME)
                    .data(null)
                    .build();
        }
        if(userRequest.getPhoneNumber().isEmpty()){
            return CustomResponse.builder()
                    .responseCode(Response.BAD_REQUEST_CODE)
                    .responseMessage(Response.BAD_REQUEST_MESSAGE_PHONENUMBER)
                    .data(null)
                    .build();
        }
        if (!isInputValid(userRequest.getPhoneNumber(), phoneNumberRegex)) {
                return CustomResponse.builder()
                        .responseCode(Response.BAD_REQUEST_CODE)
                        .responseMessage(Response.BAD_REQUEST_MESSAGE_WRONG_PHONENUMBER_FORMAT)
                        .data(null)
                        .build();
            }

        AppUser user = AppUser.builder()
                .email(IDGenerator.generateEmail(userRequest.getFirstName(), userRequest.getLastName()))
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .phoneNumber(userRequest.getPhoneNumber())
                .password(passwordEncoder.encode(IDGenerator.generateDefaultPassword(userRequest.getFirstName())))
                .studentId(IDGenerator.generateStaffID())
                .build();
//        Roles role = roleRepository.findByRoleName("USER");
//        user.setRoles(Collections.singleton(role));

        AppUser savedUser = userRepository.save(user);

        CustomResponse response = CustomResponse.builder()
                .responseCode(Response.SUCCESS)
                .responseMessage(Response.USER_REGISTERED_SUCCESS)
                .data(Data.builder()
                        .id(savedUser.getId())
                        .sId(savedUser.getStaffId())
                        .email(savedUser.getEmail())
                        .password(savedUser.getPassword())
                        .build())
                .build();
        return response;

    }

    public ResponseEntity<List<CustomResponse>> getAllStudents() {
        List<AppUser> users = userRepository.findAll();
        List<CustomResponse> responses = users.stream().map(allStudents -> CustomResponse.builder()
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

    public ResponseEntity<List<CustomResponse>> getAllStaffs() {
        List<AppUser> users = userRepository.findAll();
        List<CustomResponse> responses = users.stream().map(allStaffs -> CustomResponse.builder()
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

    private boolean isInputValid(String input, String regex) {
        return Pattern.compile(regex)
                .matcher(input)
                .matches();
    }

    @Override
    public ResponseEntity<CustomResponse> authenticateUser(LoginRequest loginRequest) throws Exception {
            authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
            final CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(loginRequest.getUsername());
            AppUser user = userRepository.findByEmail(userDetails.getUsername()).get();
            final String access_token = jwtTokenUtil.generateToken(userDetails);
        LoginResponse response = LoginResponse.builder()
                .access_token(access_token)
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .staffId(user.getStaffId())
                .studentId(user.getStudentId())
                .phoneNumber(user.getPhoneNumber())
                .isEnabled(user.isEnabled())
                .build();

            return ResponseEntity.ok(new CustomResponse(HttpStatus.OK.name(), "Login successfully", response));
    }

    private void authenticateUser(String username, String password) throws Exception {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        }catch (DisabledException e){
            throw new Exception("USER_DISABLED", e);
        }catch (BadCredentialsException e){
            throw new ApplicationAuthenticationException("Invalid username or password combination", e);
        }

    }
}
