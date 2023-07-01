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
@Table(name = "user_role")
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
    private Collection<AppUser> users = new HashSet<>();

    public Roles(String name) {
        this.name = name;
    }

    // remove all users from role
    public void removeAllUsersFromRole(){
        if(this.getUsers() != null){
            List<AppUser> usersInRole = this.getUsers().stream().toList();
            usersInRole.forEach(this::removeUserFromRole);
        }
    }

    // remove single user from role
    public void removeUserFromRole(AppUser user) {
        user.getRoles().remove(this);
        this.getUsers().remove(user);
    }

    // assign role to user
    public void assignUserToRole(AppUser user){
        user.getRoles().add(this);
        this.getUsers().add(user);
    }
}
