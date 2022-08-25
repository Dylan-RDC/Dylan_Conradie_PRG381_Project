package com.studentportal.webapp.frontEnd_Controllers;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.studentportal.webapp.models.admin;
import com.studentportal.webapp.models.course;
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
	public String studentCourses(@RequestParam("updated") Optional<String> updated,@AuthenticationPrincipal MyUserDetails myUser, Model model) 
	{

        List<student> students = studService.getAllStudents();
        admin CurrentAdmin = (admin)myUser.getUser();

        if (updated.isPresent()) {
            String status = updated.get();
            
            if (status.equalsIgnoreCase("Failed to add student")) {
                model.addAttribute("error", updated.get());
                System.out.println(status);
            }
            else
            {
                
                model.addAttribute("updated", updated.get());

            }
        }

        model.addAttribute("newStudent", new student());
		model.addAttribute("students",students);
        model.addAttribute("admin",CurrentAdmin);
		return "AdminHome.html";
	}

    @PostMapping("/register/student")
    public String addStudent(@Validated @ModelAttribute("student") student newStud,@AuthenticationPrincipal MyUserDetails myUser, Model model) 
    {

        try {

            if (studService.addStudent(newStud).equalsIgnoreCase("failed")) {
                return  String.format("redirect:/admin/display/students?updated=%s", "Failed to add student") ;
            }
            return "redirect:/admin/display/students";
        } catch (Exception e) {
            return  String.format("redirect:/admin/display/students?updated=%s", "Failed to add student") ;
        }
        
        
    
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
        
            admin CurrentAdmin = (admin)myUser.getUser();

            student newStud = studService.getStudent(OldStud.getStudent_id());
            newStud.setEmail(OldStud.getEmail());
            newStud.setStudent_address(OldStud.getStudent_address());
            newStud.setStudent_name(OldStud.getStudent_name());
            String newP = OldStud.getPassword();
            if (!newP.isBlank()) {

                Pattern pattern = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{10,}");
                Matcher matcher = pattern.matcher(newP);
                if (matcher.matches()) {
                    newStud.setPassword(encoder.encode(OldStud.getPassword()));
                    studService.updateStudent(newStud);
                }
                else
                {
                    model.addAttribute("student",newStud);
                    model.addAttribute("error","Password is not correctly formatted");
                    model.addAttribute("admin",CurrentAdmin);
                    return "AdminEditStudent.html"; 
                }
            }
            

            return String.format("redirect:/admin/display/students?updated=%s", newStud.getStudent_id()); 

	}

    @GetMapping("/courses")
    public String adminCourses(@RequestParam("error") Optional<String> error,@AuthenticationPrincipal MyUserDetails myUser,Model model)
    {
        admin CurrentAdmin = (admin)myUser.getUser();
        List<course> courses = cService.getAllCourses();

        if (error.isPresent()) {
            model.addAttribute("error", error.get());
        }

        model.addAttribute("admin",CurrentAdmin);
        model.addAttribute("courses", courses);
        model.addAttribute("newCourse", new course());
        return "AdminAddCourse.html";
    }

    @GetMapping("/delete/course/{course_id}")
    public String deleteCourse(@AuthenticationPrincipal MyUserDetails myUser,Model model,@PathVariable(value="course_id") Long course_id) {

        cService.deleteCourseByID(course_id);

        return "redirect:/admin/display/courses";

    }

    @GetMapping("/delete/student/{student_id}")
    public String deleteStudent(@AuthenticationPrincipal MyUserDetails myUser,Model model,@PathVariable(value="student_id") Long student_id) {
        
        studService.deleteStudent(student_id);

        return "redirect:/admin/display/students";

    }

    @PostMapping("/addCourse")
    public String addCourse(@AuthenticationPrincipal MyUserDetails myUser,@Validated @ModelAttribute("newCourse") course newCourse,Model model) {
     
        try {
            if (cService.addCourse(newCourse).equalsIgnoreCase("failed")) {
                return String.format("redirect:/admin/display/courses?error=Course with name: %s already exists", newCourse.getCourse_name());
            } 
            
            return String.format("redirect:/admin/display/courses");
        } catch (Exception e) {
            //TODO: handle exception
            
            return String.format("redirect:/admin/display/courses?error=%s", "Failed to add Course");
        }
      
        

    }

}
