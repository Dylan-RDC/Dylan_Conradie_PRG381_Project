package com.studentportal.webapp.capi_ontrollers;

import java.util.*;
import com.studentportal.webapp.models.admin;
import com.studentportal.webapp.repo.adminRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
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

    @GetMapping("/find/{id}")
    public Optional<admin> getStudent(Model model,@PathVariable(value="id") Long id) {
            return myRepo.findById(id);
    }

    @GetMapping("/testing")
    public List<admin> getAdmins(){

        return getAdmin();
    }
       
}
