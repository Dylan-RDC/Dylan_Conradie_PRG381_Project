package com.studentportal.webapp.frontEnd_Controllers;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import com.studentportal.webapp.models.course;
import com.studentportal.webapp.models.student;

import com.studentportal.webapp.security.MyUserDetails;
import com.studentportal.webapp.service.courseSerivce;
import com.studentportal.webapp.service.studentService;




@Controller
@RequestMapping("/student/display")
public class studentCourse {

    @Autowired
    studentService studService;

    @Autowired
    courseSerivce cService;
    
    @GetMapping("/delete/course/{id}")
    public String deleteStudCourse(@AuthenticationPrincipal MyUserDetails myUser,Model model,@PathVariable(value="id") Long id)
    {
        student CurrentStud = (student)myUser.getUser();
        studService.removeStudentCourse(CurrentStud.getStudent_id(),id);

        CurrentStud.setStudCourse(studService.getStudent(CurrentStud.getStudent_id()).getStudentCourses());

		// model.addAttribute("student",CurrentStud);
        // model.addAttribute("courses",CurrentStud.getStudentCourses());
		return "redirect:http://localhost:8080/student/display/courses";
	}
 
    

    @PostMapping("/addCourse")
    public String addCourse(@AuthenticationPrincipal MyUserDetails myUser,@Validated @ModelAttribute("newCourse") course newCourse,Model model)
    {

        student CurrentStud = (student)myUser.getUser();
        // System.out.println(newCourse.getCourse_id());
        // course nC = cService.getCourseByID(newCourse.getCourse_id());
        // CurrentStud.addStudentCourse(nC);
        // studService.updateStudent(CurrentStud);
         studService.addStudentCourse(CurrentStud.getStudent_id(), newCourse.getCourse_id());
         CurrentStud.setStudCourse(studService.getStudent(CurrentStud.getStudent_id()).getStudentCourses());
		return "redirect:http://localhost:8080/student/display/courses";
	}

    @PostMapping("/update")
    public String updateStudent(@AuthenticationPrincipal MyUserDetails myUser,@Validated @ModelAttribute("student") student OldStud,Model model)
    {

        student user = (student)myUser.getUser();
        // newStud.setEmail(OldStud.getEmail());
        // newStud.setStudent_address(OldStud.getStudent_address());
        // newStud.setStudent_name(OldStud.getStudent_name());
        System.out.println(user.getEmail());
        String hashpassword = user.getPassword();
        student saveStud = new student();

        saveStud.setStudent_id(user.getStudent_id());
        saveStud.setEmail(OldStud.getEmail());
        saveStud.setStudent_name(OldStud.getStudent_name());
        saveStud.setStudent_address(OldStud.getStudent_address());
        saveStud.setPassword(OldStud.getPassword());
        saveStud.setNewPassword(OldStud.getNewPassword());

        String response;

        if ((response = studService.updateStud(saveStud,hashpassword)).equalsIgnoreCase("correct")) {
            System.out.println(response);
            student updated = studService.getStudent(user.getStudent_id());
            user.setEmail(updated.getEmail());
            user.setStudent_name(updated.getStudent_name());
            user.setStudent_address(updated.getStudent_address());
            user.setPassword(updated.getPassword());
            return String.format("redirect:/student/display/details?status=%s", "Successfully Updated Details");
        }
        else {
            System.out.println(response);
            return String.format("redirect:/student/display/details?status=%s", response);
          
        }

      


        // BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
    
        // if (encoder.matches(OldStud.getPassword(), myUser.getPassword()) ) {
        //     //TODOOO

        //     student newStud = (student)myUser.getUser();
        //     newStud.setEmail(OldStud.getEmail());
        //     newStud.setStudent_address(OldStud.getStudent_address());
        //     newStud.setStudent_name(OldStud.getStudent_name());
        //     String newP;
        //     if (!(newP = OldStud.getNewPassword()).isEmpty()) {
                
        //         Pattern pattern = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{10,}");
        //         Matcher matcher = pattern.matcher(newP);
        //         if (matcher.matches()) {
        //             newStud.setPassword(encoder.encode(OldStud.getNewPassword()));
        //         }
        //         else
        //         {
        //             return String.format("redirect:/student/display/details?status=%s", "Password was not correctly formatted");  
        //         }
        //     }

        //     studService.updateStudent(newStud);
        //     return String.format("redirect:/student/display/details?status=%s", "Successfully Updated Details");
        // }
        // else
        // {
        //     return String.format("redirect:/student/display/details?status=%s", "Incorrect Credentials");
        // }  

	}

    @GetMapping("/courses")
	public String studentCourses(@AuthenticationPrincipal MyUserDetails myUser,Model model) 
	{
        Long CurrentStud_id = ((student) myUser.getUser()).getStudent_id();
        student CurrentStud = studService.getStudent(CurrentStud_id);



        // student CurrentStud = studService.getStudent(((student)myUser.getUser()).getStudent_id());
        List<course> course = cService.getAllCourses();
        List<course> filtered = new ArrayList<>();

        for (course c : course) {
            boolean found = false;
            for (course studc : CurrentStud.getStudentCourses()) {
                if (c.getCourse_id()==studc.getCourse_id()) {
                    found = true;
                    break;
                }

            }

            if (!found) {
                filtered.add(c);
            }            


        }

        

        model.addAttribute("newCourse", new course());
		model.addAttribute("student",CurrentStud);
        model.addAttribute("courses",filtered);
		return "StudentCourses.html";
	}

    @GetMapping("/sutdent/register/{name}")
    public String getStudentRegister(Model model,@PathVariable(value="name") String studname) {
            model.addAttribute("names",new ArrayList<String>(Arrays.asList(studname)));

            return "test.html";
    }
   
    @GetMapping("/details")
	public String homePage(@RequestParam("status") Optional<String> status, @AuthenticationPrincipal MyUserDetails myUser,Model model) 
	{
        student CurrentStud = (student)myUser.getUser();

        if (status.isPresent()) {
            String message = status.get();

            if (message.equalsIgnoreCase("Successfully Updated Details")) {
                model.addAttribute("success", status.get());
            }
            else
            {
                model.addAttribute("error", status.get());

            }
        }

        
		model.addAttribute("student",(CurrentStud));
        model.addAttribute("courses",(CurrentStud.getStudentCourses()));
		return "StudentDetails.html";
	}



}
