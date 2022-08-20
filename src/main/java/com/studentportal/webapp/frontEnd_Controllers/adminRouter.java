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
    

    @GetMapping("/students")
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


    
    @GetMapping("/edit/student/{stud_id}")
    public String editStudentForm(Model model,@PathVariable(value="stud_id") Long stud_id)
	{
        String StudUri = String.format("http://localhost:8080/student/find/%d", stud_id);
        String adminUri = "http://localhost:8080/admin/find/2";
        RestTemplate restTemplate = new RestTemplate();
        admin ad = restTemplate.getForObject(adminUri, admin.class);
        student stud = restTemplate.getForObject(StudUri, student.class);

		model.addAttribute("student",stud);
        model.addAttribute("admin",ad);
		return "AdminEditStudent.html";
	}

}
