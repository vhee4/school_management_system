package com.SchoolManagement.School.Management.System.utils;

public class Response {
    public static final String USER_EXISTS_CODE = "001";
    public static final String USER_EXISTS_MESSAGE = "User with provided email already exists";
    public static final String BAD_REQUEST_CODE = "002";
    public static final String BAD_REQUEST_MESSAGE_FIRSTNAME = "First name field must not be empty";
    public static final String BAD_REQUEST_MESSAGE_DEPARTMENT_EMPTY = "Department field must not be empty";
    public static final String BAD_REQUEST_MESSAGE_DEPARTMENT_DOES_NOT_EXIST = "Department does not exist";
    public static final String BAD_REQUEST_MESSAGE_LASTNAME = "Last name field must not be empty";
    public static final String BAD_REQUEST_MESSAGE_PHONENUMBER = "Phone number field must not be empty";
    public static final String BAD_REQUEST_MESSAGE_WRONG_PHONENUMBER_FORMAT = "Phone number format is invalid";
    public static final String BAD_REQUEST_MESSAGE_PHONENUMBER_ALREADY_EXISTS= "Phone number already exists";

    public static final String SUCCESS = "003";
    public static final String USER_REGISTERED_SUCCESS = "User successfully registered";
    public static final String SUCCESS_MESSAGE = "Successfully done";

}
