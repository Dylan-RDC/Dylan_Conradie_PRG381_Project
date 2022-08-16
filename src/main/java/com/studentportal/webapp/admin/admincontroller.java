package com.studentportal.webapp.admin;

import java.util.ArrayList;
import java.util.*;
import com.studentportal.webapp.admin.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class admincontroller {
    @Autowired
    adminRepo myRepo;

    public List<admin> getAdmin()
    {
        try {
            var response = myRepo.findAll(); 
            return response;
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }



    @GetMapping("/testing")
    public List<admin> getAdmins(){

        return getAdmin();
    }
       
}
