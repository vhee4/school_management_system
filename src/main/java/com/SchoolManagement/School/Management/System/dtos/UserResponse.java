package com.SchoolManagement.School.Management.System.dtos;

import lombok.Builder;

@lombok.Data
@Builder
public class UserResponse {
    private String responseCode;
    private String responseMessage;
    private Data data;
}
