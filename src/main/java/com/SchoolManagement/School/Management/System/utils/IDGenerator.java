package com.SchoolManagement.School.Management.System.utils;

import org.springframework.boot.SpringApplication;

import java.security.SecureRandom;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class IDGenerator {
    public static final int LENGTH_OF_PASSWORD = 10;

    public static String generateStaffID() {
        int randomNumber = new Random().nextInt(9999) + 1;
        int currentYear = Year.now().getValue();
        return "STF/" + currentYear + "/" + String.format("%04d", randomNumber);
    }

    public static String generateStudentID() {
        int randomNumber = new Random().nextInt(99999) + 1;
        int currentYear = Year.now().getValue();
        return "STU/" + currentYear + "/" + String.format("%04d", randomNumber);
    }

    public static String generateEmail(String firstname, String lastname) {
        StringBuilder sb = new StringBuilder();
        int random = new SecureRandom().nextInt(999)+1;
        String schoolMail = "@viteruni.org";
        return sb.append(firstname).append(".").append(lastname).append(String.format("%02d",random)).append(schoolMail).toString().toLowerCase();
    }

    public static String generateDefaultPassword(String firstName) {
//        String uppercaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
//        String lowercaseLetters = "abcdefghijklmnopqrstuvwxyz";
//        String numbers = "0123456789";
//
//        String allowedChars = uppercaseLetters + lowercaseLetters + numbers;
//        StringBuilder password = new StringBuilder();
//
//        Random random = new Random();
//        for (int i = 0; i < length; i++) {
//            int index = random.nextInt(allowedChars.length());
//            password.append(allowedChars.charAt(index));
//        }
//
//        return password.toString();
        Date date = new Date();
        int yearOfRegistration = getYearFromDate(date);
        String defaultPassword = firstName+yearOfRegistration;
        return defaultPassword.toLowerCase();
    }

    public static int getYearFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

}


