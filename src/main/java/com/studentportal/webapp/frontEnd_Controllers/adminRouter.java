package com.studentportal.webapp.frontEnd_Controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.studentportal.webapp.models.admin;
import com.studentportal.webapp.models.student;
import com.studentportal.webapp.security.MyUserDetails;
import com.studentportal.webapp.service.courseSerivce;
import com.studentportal.webapp.service.studentService;


@Controller
@RequestMapping("/admin/display")
public class adminRouter {
    
    @Autowired
    studentService studService;

    @Autowired
    courseSerivce cService;

    @GetMapping("/students")
	public String studentCourses(@AuthenticationPrincipal MyUserDetails myUser, Model model) 
	{

        List<student> students = studService.getAllStudents();
        admin CurrentAdmin = (admin)myUser.getUser();
		model.addAttribute("students",students);
        model.addAttribute("admin",CurrentAdmin);
		return "AdminHome.html";
	}


    
    @GetMapping("/edit/student/{stud_id}")
    public String editStudentForm(@AuthenticationPrincipal MyUserDetails myUser,Model model,@PathVariable(value="stud_id") Long stud_id)
	{
        admin CurrentAdmin = (admin)myUser.getUser();
        student stud = studService.getStudent(stud_id);

		model.addAttribute("student",stud);
        model.addAttribute("admin",CurrentAdmin);
		return "AdminEditStudent.html";
	}

    @PostMapping("/student/update")
    public String updateStudent(@Validated @ModelAttribute("student") student OldStud,@AuthenticationPrincipal MyUserDetails myUser,Model model)
    {


        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
            //TODOOO
            admin CurrentAdmin = (admin)myUser.getUser();

            student newStud = studService.getStudent(OldStud.getStudent_id());
            newStud.setEmail(OldStud.getEmail());
            newStud.setStudent_address(OldStud.getStudent_address());
            newStud.setStudent_name(OldStud.getStudent_name());

            if (!OldStud.getPassword().isBlank()) {
                newStud.setPassword(encoder.encode(OldStud.getPassword()));
            }
            
            studService.updateStudent(newStud);
            List<student> studs = studService.getAllStudents();
            String updated = String.format("%d", newStud.getStudent_id());

            model.addAttribute("updated", updated);
            model.addAttribute("students",studs);
            model.addAttribute("admin",CurrentAdmin);

            return "AdminHome.html";

		// return "redirect:http://localhost:8080/admin/display/students";
	}

}
