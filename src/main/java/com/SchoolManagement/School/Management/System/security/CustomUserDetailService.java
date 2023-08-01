package com.SchoolManagement.School.Management.System.security;

import com.SchoolManagement.School.Management.System.entity.AppUser;
import com.SchoolManagement.School.Management.System.entity.StaffEntity;
import com.SchoolManagement.School.Management.System.entity.StudentEntity;
import com.SchoolManagement.School.Management.System.exception.ApplicationAuthenticationException;
import com.SchoolManagement.School.Management.System.repository.StaffRepository;
import com.SchoolManagement.School.Management.System.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final StaffRepository staffRepository;
    private final StudentRepository studentRepository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        AppUser user = userRepository.findByEmail(username).orElseThrow(()-> new ApplicationAuthenticationException("Invalid username and password combination"));
//        if(!user.isEnabled()){
//            throw new ApplicationAuthenticationException("Access denied, your account is currently locked");
//        }
//        return new CustomUserDetails(user);
        AppUser user = null;
        // Check if the user is a staff member
        Optional<StaffEntity> staffOptional = staffRepository.findByEmail(username);
        if (staffOptional.isPresent()) {
            StaffEntity staff = staffOptional.get();
            if (!staff.isEnabled()) {
                throw new ApplicationAuthenticationException("Access denied, your staff account is currently locked");
            }
            user = staff; // Assuming Staff implements UserDetails
        } else {
            // Check if the user is a student
            Optional<StudentEntity> studentOptional = studentRepository.findByEmail(username);
            if (studentOptional.isPresent()) {
                StudentEntity student = studentOptional.get();
                if (!student.isEnabled()) {
                    throw new ApplicationAuthenticationException("Access denied, your student account is currently locked");
                }
                user = student; // Assuming Student implements UserDetails
            }
        }
        assert user != null;
        return new CustomUserDetails(user);
    }
}
