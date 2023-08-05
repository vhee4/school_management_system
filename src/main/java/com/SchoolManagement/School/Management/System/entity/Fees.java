package com.SchoolManagement.School.Management.System.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Calendar;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Fees {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double amount;
    @Column(columnDefinition = "TEXT")
    private String feeDescription;

    @OneToOne
    @JoinColumn(name = "department_id")
    private Department department;
    private Timestamp createdAt;
    private Timestamp lastModifiedAt;

    @PrePersist
    public void persist(){
        createdAt = Timestamp.from(Calendar.getInstance().toInstant());
        lastModifiedAt = Timestamp.from(Calendar.getInstance().toInstant());
    }

    @PreUpdate
    public void update(){
        lastModifiedAt = Timestamp.from(Calendar.getInstance().toInstant());
    }
}
