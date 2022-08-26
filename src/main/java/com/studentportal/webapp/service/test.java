package com.studentportal.webapp.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.studentportal.webapp.models.Iuser;
import com.studentportal.webapp.models.admin;

public class test {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
        String rawPassword = "test";
        String encoded = encoder.encode("okay");
        if (encoder.matches("Thisisatest02", "$2a$10$VS5RfL1ItRJSmlNR0huAw.ip5dEAbmxgQFpab7MVn3JmIA3y2qJpe")) {
            System.out.println("MATCHES");
        }
        Iuser ad = new admin();



        System.out.println(encoded);
    }
}
