package com.studentportal.webapp.frontEnd_Controllers;

import java.util.*;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.studentportal.webapp.models.Iuser;
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
        studService.removeStudentCourse(CurrentStud,id);
       

		model.addAttribute("student",CurrentStud);
        model.addAttribute("courses",CurrentStud.getStudentCourses());
		return "redirect:http://localhost:8080/student/display/courses";
	}


      
    @PostMapping("/addCourse")
    public String addCourse(@AuthenticationPrincipal MyUserDetails myUser,@Validated @ModelAttribute("newCourse") course newCourse,Model model)
    {
        student CurrentStud = (student)myUser.getUser();
        List<course> temp = CurrentStud.getStudentCourses();
        boolean found = false;
        for (course c : temp) {
            if (c.getCourse_id()==newCourse.getCourse_id()) {
                found = true;
                break;
            }
        }
        if (!found) {
            CurrentStud.addStudentCourse(newCourse);
            studService.updateStudent(CurrentStud);
        }

		return "redirect:http://localhost:8080/student/display/courses";
	}

    @PostMapping("/update")
    public String updateStudent(@AuthenticationPrincipal MyUserDetails myUser,@Validated @ModelAttribute("student") student OldStud,Model model)
    {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
    
        if (encoder.matches(OldStud.getPassword(), myUser.getPassword()) ) {
            //TODOOO

            student newStud = (student)myUser.getUser();
            newStud.setEmail(OldStud.getEmail());
            newStud.setStudent_address(OldStud.getStudent_address());
            newStud.setStudent_name(OldStud.getStudent_name());

            if (!OldStud.getNewPassword().isEmpty()) {
                
                newStud.setPassword(encoder.encode(OldStud.getNewPassword()));
            }

            studService.updateStudent(newStud);
        }


        

		return "redirect:http://localhost:8080/student/display/details";
	}

    @GetMapping("/courses")
	public String studentCourses(@AuthenticationPrincipal MyUserDetails myUser,Model model) 
	{
        student CurrentStud = studService.getStudent(((student)myUser.getUser()).getStudent_id());
        List<course> course = cService.getAllCourses();
        // String StudUri = "http://localhost:8080/student/find/2";
        // String CourseUri = "http://localhost:8080/course/testing";
        // RestTemplate restTemplate = new RestTemplate();
        // student result = restTemplate.getForObject(StudUri, student.class);
        // Collection response = restTemplate.getForObject(CourseUri, Collection.class);
        // List<course> courses = (List<course>) response;
        // System.out.println(courses);
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
   
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/details")
	public String homePage(@AuthenticationPrincipal MyUserDetails myUser,Model model) 
	{
        // String StudUri = "http://localhost:8080/student/find/2";
        // String CourseUri = "http://localhost:8080/course/testing";
        // RestTemplate restTemplate = new RestTemplate();
        // HttpHeaders headers = new HttpHeaders();
        // headers.add("Authorization", headerValue);
        //  restTemplate.exchange(CourseUri, HttpMethod.POST, new HttpEntity<T>(createHeaders(myUser.getUsername(), myUser.getPassword())), clazz)


        // student result = restTemplate.getForObject(StudUri, student.class);
        // Collection response = restTemplate.getForObject(CourseUri, Collection.class);
        // List<course> courses = (List<course>) response;
        // Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        student CurrentStud = (student)myUser.getUser();

        
        
    
		model.addAttribute("student",(CurrentStud));
        model.addAttribute("courses",(CurrentStud.getStudentCourses()));
		return "StudentDetails.html";
	}

    // @PostMapping("/register")
    // public String addStudent(@Validated student user, BindingResult result, Model model) {
        
    //     if (result.hasErrors()) {
    //         model.addAttribute("newStudent",new student());
	// 	    return "StudentRegistration.html";
    //     }

    //     String StudUri = "http://localhost:8080/student/insert";
    //     RestTemplate restTemplate = new RestTemplate();
    //     System.out.println("REPO" + user);
    //     HttpEntity<student> req = new HttpEntity<student>(user);
        
    //     String response = restTemplate.postForObject(StudUri, req, String.class );

    //     if (response!="Error") {
    //         model.addAttribute("newStudent",user);
    //         return "StudentRegistration.html";
    //     }



    //     model.addAttribute("newStudent",user);
	// 	return "StudentRegistration.html";
    // }

    // @GetMapping("/register")
	// public String registerForm(Model model) 
	// {
    //     // List<String> names = new ArrayList<String>();
    //     // names.add("Dylan");
    //     // names.add("cameron");


    //     // String StudUri = "http://localhost:8080/student/find/2";
    //     // String CourseUri = "http://localhost:8080/course/testing";
    //     // RestTemplate restTemplate = new RestTemplate();
    //     // student result = restTemplate.getForObject(StudUri, student.class);
    //     // Collection response = restTemplate.getForObject(CourseUri, Collection.class);
    //     // List<course> courses = (List<course>) response;
	// 	// model.addAttribute("student",result);
    //     // model.addAttribute("courses",courses);
    //     model.addAttribute("newStudent",new student());
	// 	return "StudentRegistration.html";
	// }



}
