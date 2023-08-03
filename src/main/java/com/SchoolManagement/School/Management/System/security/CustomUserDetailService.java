package com.SchoolManagement.School.Management.System.security;

import com.SchoolManagement.School.Management.System.entity.Staff;
import com.SchoolManagement.School.Management.System.entity.Student;
import com.SchoolManagement.School.Management.System.repository.StaffRepository;
import com.SchoolManagement.School.Management.System.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final StaffRepository staffRepository;
    private final StudentRepository studentRepository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Staff staff = staffRepository.findByEmail(username).orElse(null);
        Student student = studentRepository.findByEmail(username).orElse(null);

        if (staff != null) {
            if (!staff.isEnabled()) {
                throw new DisabledException("Access denied, your staff account is currently locked");
            }
            return new CustomUserDetails(staff);
        } else if (student != null) {
            if (!student.isEnabled()) {
                throw new DisabledException("Access denied, your student account is currently locked");
            }
            return new CustomUserDetails(student);
        }

        throw new UsernameNotFoundException("Invalid username and password combination");
    }
}
