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
            
            if (status.equalsIgnoreCase("Failed to add student") || status.equalsIgnoreCase("Failed to add student password was not of correct format")) {
                model.addAttribute("error", updated.get());
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

            Pattern pattern = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{10,}");
            Matcher matcher = pattern.matcher(newStud.getPassword());
            if (!matcher.matches()) {
                return String.format("redirect:/admin/display/students?updated=%s", "Failed to add student password was not of correct format"); 
            }
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
        if (studService.findByID(stud_id)==null) {
      
            return "redirect:/admin/display/students";
        }
        else
        {
            admin CurrentAdmin = (admin)myUser.getUser();
            student stud = studService.getStudent(stud_id);
    
            model.addAttribute("student",stud);
            model.addAttribute("admin",CurrentAdmin);
            return "AdminEditStudent.html";  
        }
	}

    @PostMapping("/student/update")
    public String updateStudent(@Validated @ModelAttribute("student") student OldStud,@AuthenticationPrincipal MyUserDetails myUser,Model model)
    {
        admin CurrentAdmin = (admin)myUser.getUser();

             student newStud = studService.getStudent(OldStud.getStudent_id());
             newStud.setEmail(OldStud.getEmail());
             newStud.setStudent_address(OldStud.getStudent_address());
             newStud.setStudent_name(OldStud.getStudent_name());
             newStud.setNewPassword(OldStud.getPassword());
             String response;

             if ((response=studService.updateStud(newStud)).equalsIgnoreCase("correct")) {
                studService.updateStud(newStud);
                return String.format("redirect:/admin/display/students?updated=%s", newStud.getStudent_id());
             }
             model.addAttribute("student",newStud);
             model.addAttribute("error",response);
             model.addAttribute("admin",CurrentAdmin);
             return "AdminEditStudent.html";


	}


    @GetMapping("/edit/student/courses/{student_id}")
    public String getStudentCourses(@AuthenticationPrincipal MyUserDetails myUser,Model model,@PathVariable(value="student_id") Long stud_id)
    {
        student CurrentStud = studService.findByID(stud_id);
        List<course> filtered = cService.filteredCourses(CurrentStud);

        admin CurrentAdmin = (admin)myUser.getUser();
        // student stud = studService.getStudent(stud_id);
        model.addAttribute("admin",CurrentAdmin);
        model.addAttribute("student", CurrentStud);
        model.addAttribute("courses", filtered);
        return "AdminStudentCourses.html";
    }

       
    @GetMapping("/delete/student/{stud_id}/course/{course_id}")
    public String deleteStudCourse(@AuthenticationPrincipal MyUserDetails myUser,Model model,@PathVariable(value="course_id") Long course_id,@PathVariable(value="stud_id") Long stud_id)
    {
        // student CurrentStud = (student)myUser.getUser();
        // studService.removeStudentCourse(CurrentStud.getStudent_id(),id);
        student stud = studService.getStudent(stud_id);
        stud.getStudent_id();
        // CurrentStud.setStudCourse(studService.getStudent(CurrentStud.getStudent_id()).getStudentCourses());

        if (studService.findByID(stud_id)==null) {
            return String.format("redirect:http:/admin/display/edit/students");
        }

        if (cService.getByID(course_id)==null) {
            return String.format("redirect:http:/admin/display/edit/students");
        }
        studService.removeStudentCourse(stud_id,course_id);


        return String.format("redirect:http:/admin/display/edit/student/courses/%d", stud_id);

		// model.addAttribute("student",CurrentStud);
        // model.addAttribute("courses",CurrentStud.getStudentCourses());
      
	}

    @PostMapping("/student/add/course/{stud_id}")
    public String addStudCourse(@AuthenticationPrincipal MyUserDetails myUser,@PathVariable(value="stud_id") Long stud_id,@Validated @ModelAttribute("newCourse") course newCourse,Model model)
    {
        if (studService.findByID(stud_id)==null) {
            return String.format("redirect:http:/admin/display/edit/students");
        }

        if (cService.getByID(newCourse.getCourse_id())==null) {
            return String.format("redirect:http:/admin/display/edit/students");
        }

         studService.addStudentCourse(stud_id, newCourse.getCourse_id());
         return String.format("redirect:http:/admin/display/edit/student/courses/%d", stud_id);
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

        if (cService.getByID(course_id)!=null) {
            cService.deleteCourseByID(course_id);
        }
        return "redirect:/admin/display/courses";

    }

    @GetMapping("/delete/student/{student_id}")
    public String deleteStudent(@AuthenticationPrincipal MyUserDetails myUser,Model model,@PathVariable(value="student_id") Long student_id) {
        
        if (studService.findByID(student_id)==null) {
            return "redirect:/admin/display/students";
        }

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
