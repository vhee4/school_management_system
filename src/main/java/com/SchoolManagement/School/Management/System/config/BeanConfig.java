package com.SchoolManagement.School.Management.System.config;

import com.SchoolManagement.School.Management.System.entity.Department;
import com.SchoolManagement.School.Management.System.entity.Roles;
import com.SchoolManagement.School.Management.System.entity.Staff;
import com.SchoolManagement.School.Management.System.repository.DepartmentRepository;
import com.SchoolManagement.School.Management.System.repository.RoleRepository;
import com.SchoolManagement.School.Management.System.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Collections;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class BeanConfig {
    private final StaffRepository staffRepository;
    private final RoleRepository roleRepository;
    private final DepartmentRepository departmentRepository;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public CommandLineRunner createDefaultUser(PlatformTransactionManager transactionManager) {
        return args -> {
            TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);

            transactionTemplate.execute(status -> {


                if (!staffRepository.findByEmail("admin@viteruni.org").isPresent()) {
                    Optional<Roles> role = roleRepository.findByName("ROLE_ADMIN");
                    Roles adminRole = null;
                    if(!role.isPresent()){
                        Roles newAdminUser= Roles.builder()
                                .name("ROLE_ADMIN")
                                .build();
                        adminRole= roleRepository.save(newAdminUser);
                    }else{
                        adminRole = role.get();
                    }

                    Staff newUser = new Staff();
                            newUser.setEmail("admin@viteruni.org");
                    newUser.setFirstName("Vivian");
                    newUser.setLastName("Chioma");
                    newUser.setEnabled(true);
                    newUser.setRoles(Collections.singleton(adminRole));
                    newUser.setPassword(passwordEncoder().encode("admin"));

//                    entityManager.persist(role); // Save the UserRole entity
                    staffRepository.save(newUser); // Save the UserEntity entity
                }
                return null;
            });
            transactionTemplate.execute(status -> {

                if (!departmentRepository.findByName("Science").isPresent()) {
                    Department department = Department.builder()
                            .name("Science")
                            .build();
                    departmentRepository.save(department); // Save the UserEntity entity
                }
                return null;
            });
        };
    }
}
