package com.studentportal.webapp.frontEnd_Controllers;

import java.util.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.studentportal.webapp.models.admin;
import com.studentportal.webapp.models.student;


@Controller
@RequestMapping("/admin/display")
public class adminRouter {
    

    @GetMapping("/admins")
	public String studentCourses(Model model) 
	{


        String StudUri = "http://localhost:8080/student/testing";
        String adminUri = "http://localhost:8080/admin/find/2";
        RestTemplate restTemplate = new RestTemplate();
        Collection result = restTemplate.getForObject(StudUri, Collection.class);
        List<student> students = (List<student>) result;
        admin ad = restTemplate.getForObject(adminUri, admin.class);
		model.addAttribute("students",result);
        model.addAttribute("admin",ad);
		return "AdminHome.html";
	}
}
