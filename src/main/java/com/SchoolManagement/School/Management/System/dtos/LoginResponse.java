package com.SchoolManagement.School.Management.System.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponse {
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String Id;
    private String department;
    private double schoolFee;
    private String access_token;
    private boolean isEnabled;
}
