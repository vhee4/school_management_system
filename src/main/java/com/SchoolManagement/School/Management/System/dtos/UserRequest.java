package com.SchoolManagement.School.Management.System.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequest {
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String sId;
    private String password;


}
