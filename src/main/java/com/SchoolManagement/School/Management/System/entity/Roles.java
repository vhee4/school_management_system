package com.SchoolManagement.School.Management.System.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Entity
@Data
@Builder
@Table(name = "role")
@NoArgsConstructor
@AllArgsConstructor
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NaturalId
    private String name;
    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Collection<StaffEntity> staffs = new HashSet<>();
    @ManyToMany(mappedBy = "roles")
    private Collection<StudentEntity> students = new HashSet<>();

    public Roles(String name) {
        this.name = name;
    }

    // remove all users from role
    public void removeAllUsersFromRole(){
        if(this.getStaffs() != null || this.getStudents() !=null){
            List<StaffEntity> staffsInRole = this.getStaffs().stream().toList();
            List<StudentEntity> studentsInRole = this.getStudents().stream().toList();
            staffsInRole.forEach(this::removeStaffFromRole);
            studentsInRole.forEach(this::removeStudentFromRole);
        }
    }

    // remove single user from role
    public void removeStaffFromRole(StaffEntity staff) {
        staff.getRoles().remove(this);
        this.getStaffs().remove(staff);
    }public void removeStudentFromRole(StudentEntity student) {
        student.getRoles().remove(this);
        this.getStaffs().remove(student);
    }

    // assign role to user
    public void assignStaffToRole(StaffEntity staff){
        staff.getRoles().add(this);
        this.getStaffs().add(staff);
    }public void assignStudentToRole(StudentEntity student){
        student.getRoles().add(this);
        this.getStudents().add(student);
    }
}
