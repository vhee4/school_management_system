package com.SchoolManagement.School.Management.System.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.NaturalId;
import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;

@Entity
@Data
@Builder
//@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NaturalId(mutable = false)
    private Long id;
    @Email
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String password;
    private String staffId;
    private String studentId;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<Roles> roles;
    private boolean isEnabled = false;
    private Timestamp createdOn;
    private Timestamp lastModifiedOn;

    @PrePersist
    public void prePersist() {
        createdOn = Timestamp.from(Calendar.getInstance().toInstant());
        lastModifiedOn = Timestamp.from(Calendar.getInstance().toInstant());
    }

    @PreUpdate
    public void preUpdate() {
        lastModifiedOn = Timestamp.from(Calendar.getInstance().toInstant());
    }
}
