package com.SchoolManagement.School.Management.System.service.serviceImpl;

import com.SchoolManagement.School.Management.System.dtos.*;
import com.SchoolManagement.School.Management.System.entity.AppUser;
import com.SchoolManagement.School.Management.System.entity.Roles;
import com.SchoolManagement.School.Management.System.entity.StaffEntity;
import com.SchoolManagement.School.Management.System.entity.StudentEntity;
import com.SchoolManagement.School.Management.System.exception.ApplicationAuthenticationException;
import com.SchoolManagement.School.Management.System.repository.RoleRepository;
import com.SchoolManagement.School.Management.System.repository.StaffRepository;
import com.SchoolManagement.School.Management.System.repository.StudentRepository;
import com.SchoolManagement.School.Management.System.security.CustomUserDetailService;
import com.SchoolManagement.School.Management.System.security.CustomUserDetails;
import com.SchoolManagement.School.Management.System.security.JWTTokenUtil;
import com.SchoolManagement.School.Management.System.service.UserService;
import com.SchoolManagement.School.Management.System.utils.IDGenerator;
import com.SchoolManagement.School.Management.System.utils.Response;
import lombok.RequiredArgsConstructor;
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
    public final StaffRepository staffRepository;
    public final StudentRepository studentRepository;
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
        boolean isExists = studentRepository.existsByPhoneNumber(userRequest.getPhoneNumber());
        if(isExists){
            return CustomResponse.builder()
                    .responseCode(Response.BAD_REQUEST_CODE)
                    .responseMessage(Response.BAD_REQUEST_MESSAGE_PHONENUMBER_ALREADY_EXISTS)
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

        StudentEntity student = new StudentEntity();
        student.setFirstName(userRequest.getFirstName());
        student.setLastName(userRequest.getLastName());
        student.setEmail(IDGenerator.generateEmail(userRequest.getFirstName(),userRequest.getLastName()));
        student.setPassword(passwordEncoder.encode(IDGenerator.generateDefaultPassword(userRequest.getFirstName())));
        student.setPhoneNumber(userRequest.getPhoneNumber());
        student.setStudentId(IDGenerator.generateStudentID());
        student.setRoles(Collections.singleton(studentRole));

        StudentEntity savedUser = studentRepository.save(student);

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
        boolean isExists = staffRepository.existsByPhoneNumber(userRequest.getPhoneNumber());

        if(isExists){
            return CustomResponse.builder()
                    .responseCode(Response.BAD_REQUEST_CODE)
                    .responseMessage(Response.BAD_REQUEST_MESSAGE_PHONENUMBER_ALREADY_EXISTS)
                    .data(null)
                    .build();

        }

        StaffEntity staff = new StaffEntity();
        staff.setFirstName(userRequest.getFirstName());
        staff.setLastName(userRequest.getLastName());
        staff.setEmail(IDGenerator.generateEmail(userRequest.getFirstName(),userRequest.getLastName()));
        staff.setPassword(passwordEncoder.encode(IDGenerator.generateDefaultPassword(userRequest.getFirstName())));
        staff.setPhoneNumber(userRequest.getPhoneNumber());
        staff.setStaffId(IDGenerator.generateStaffID());
//        staff.setRoles(Collections.singleton(staff));

        StaffEntity savedUser = staffRepository.save(staff);

        return CustomResponse.builder()
                .responseCode(Response.SUCCESS)
                .responseMessage(Response.USER_REGISTERED_SUCCESS)
                .data(Data.builder()
                        .id(savedUser.getId())
                        .sId(savedUser.getStaffId())
                        .email(savedUser.getEmail())
                        .password(savedUser.getPassword())
                        .build())
                .build();

    }

    public ResponseEntity<List<CustomResponse>> getAllStudents() {
        List<StudentEntity> users = studentRepository.findAll();
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
        List<StaffEntity> users = staffRepository.findAll();
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
            try {
                authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
                final CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(loginRequest.getUsername());
                StaffEntity staff = staffRepository.findByEmail(userDetails.getUsername()).orElse(null);
                StudentEntity student = studentRepository.findByEmail(userDetails.getUsername()).orElse(null);

                if (student != null && passwordEncoder.matches(loginRequest.getPassword(), student.getPassword())) {
                    LoginResponse response = ResponseEntity.ok(LoginResponse.builder()
                            .access_token(jwtTokenUtil.generateToken(userDetails))
                            .email(student.getEmail())
                            .firstName(student.getFirstName())
                            .lastName(student.getLastName())
                            .Id(student.getStudentId())
                            .phoneNumber(student.getPhoneNumber())
                            .isEnabled(student.isEnabled())
                            .build()).getBody();
                    return ResponseEntity.ok(new CustomResponse(HttpStatus.OK.name(), "Login successfully", response));

                } else if (staff != null && passwordEncoder.matches(loginRequest.getPassword(), staff.getPassword())) {
                    LoginResponse response =  ResponseEntity.ok(LoginResponse.builder()
                            .access_token(jwtTokenUtil.generateToken(userDetails))
                            .email(staff.getEmail())
                            .firstName(staff.getFirstName())
                            .lastName(staff.getLastName())
                            .Id(staff.getStaffId())
                            .phoneNumber(staff.getPhoneNumber())
                            .isEnabled(staff.isEnabled())
                            .build()).getBody();
                        return ResponseEntity.ok(new CustomResponse(HttpStatus.OK.name(), "Login successfully", response));

                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body(new CustomResponse(HttpStatus.UNAUTHORIZED.name(), "Invalid credentials", null));
                }
            } catch (Exception ex) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new CustomResponse(HttpStatus.UNAUTHORIZED.name(), "Invalid username or password combination", null));
            }
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
