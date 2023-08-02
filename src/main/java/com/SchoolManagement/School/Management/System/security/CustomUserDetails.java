package com.SchoolManagement.School.Management.System.security;

import com.SchoolManagement.School.Management.System.entity.AppUser;
import com.SchoolManagement.School.Management.System.entity.Roles;
import com.SchoolManagement.School.Management.System.entity.StaffEntity;
import com.SchoolManagement.School.Management.System.entity.StudentEntity;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CustomUserDetails implements UserDetails {
    private String username;
    private String password;
    private List<GrantedAuthority> authorities;
    private boolean isEnabled;
    private Collection<Roles> roles;

    public CustomUserDetails(StaffEntity user) {
        this.username = user.getEmail();
        this.password = user.getPassword();
        this.isEnabled = user.isEnabled();
        this.authorities = user.getRoles().stream()
                .map(Roles::getName)
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());

    }

    public CustomUserDetails(StudentEntity user) {
        this.username = user.getEmail();
        this.password = user.getPassword();
        this.isEnabled = user.isEnabled();
        this.authorities = user.getRoles().stream()
                .map(Roles::getName)
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());

    }

    public void addRoles(Collection<Roles> roles) {
        this.roles.addAll(roles);
        this.authorities = this.roles.stream()
                .map(Roles::getName)
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
