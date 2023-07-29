package com.SchoolManagement.School.Management.System.utils;

import org.springframework.boot.SpringApplication;

import java.time.Year;
import java.util.Random;

public class IDGenerator {
    public static final int LENGTH_OF_PASSWORD = 10;

//    public static void main(String[] args) {
//        System.out.println(generateDefaultPassword(LENGTH_OF_PASSWORD));
//    }

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
        String schoolMail = "@Viteruni.org";
        return firstname + "." + lastname + schoolMail;
    }

    public static String generateDefaultPassword(int length) {
        String uppercaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowercaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";

        String allowedChars = uppercaseLetters + lowercaseLetters + numbers;
        StringBuilder password = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(allowedChars.length());
            password.append(allowedChars.charAt(index));
        }

        return password.toString();
    }

}


