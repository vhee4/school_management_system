package com.SchoolManagement.School.Management.System.dtos;

import lombok.Builder;

@lombok.Data
@Builder
public class Data {
    private Long id;
    private String sId;
    private String email;
    private String password;
}
