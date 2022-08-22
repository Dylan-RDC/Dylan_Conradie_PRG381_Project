package com.studentportal.webapp.frontEnd_Controllers;

import java.util.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.studentportal.webapp.models.admin;
import com.studentportal.webapp.models.student;

 
@Controller
@RequestMapping("/")
public class loginRouter {
    

    @GetMapping("")
    public String viewHomePage()
    {
        return "home.html";
    }


    @GetMapping("/student/login")
	public String studentLogIn(Model model) 
	{
		return "studentLogin.html";
	}

    
    @GetMapping("/403")
    public String error(Model model)
    {
        return "error403.html";
    }
    
    @GetMapping("/admin/login")
	public String adminLogIn(Model model) 
	{


        // String StudUri = "http://localhost:8080/student/testing";
        // String adminUri = "http://localhost:8080/admin/find/2";
        // RestTemplate restTemplate = new RestTemplate();
        // Collection result = restTemplate.getForObject(StudUri, Collection.class);
        // List<student> students = (List<student>) result;
        // admin ad = restTemplate.getForObject(adminUri, admin.class);
		// model.addAttribute("students",result);
        // model.addAttribute("admin",ad);
		return "adminLogin.html";
	}


   

}
