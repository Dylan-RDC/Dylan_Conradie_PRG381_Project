package com.studentportal.webapp.frontEnd_Controllers;

import java.util.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.studentportal.webapp.models.course;
import com.studentportal.webapp.models.student;


@Controller
@RequestMapping("/student/display")
public class studentCourse {
    

    @GetMapping("/courses")
	public String studentCourses(Model model) 
	{
        List<String> names = new ArrayList<String>();
        names.add("Dylan");
        names.add("cameron");


        String StudUri = "http://localhost:8080/student/find/2";
        String CourseUri = "http://localhost:8080/course/testing";
        RestTemplate restTemplate = new RestTemplate();
        student result = restTemplate.getForObject(StudUri, student.class);
        Collection response = restTemplate.getForObject(CourseUri, Collection.class);
        List<course> courses = (List<course>) response;
        // System.out.println(courses);
		model.addAttribute("student",result);
        model.addAttribute("courses",courses);
		return "StudentCourses.html";
	}

    @GetMapping("/sutdent/register/{name}")
    public String getStudentRegister(Model model,@PathVariable(value="name") String studname) {
            model.addAttribute("names",new ArrayList<String>(Arrays.asList(studname)));

            return "test.html";
    }

    

    @GetMapping("/details")
	public String homePage(Model model) 
	{
        List<String> names = new ArrayList<String>();
        names.add("Dylan");
        names.add("cameron");


        String StudUri = "http://localhost:8080/student/find/2";
        String CourseUri = "http://localhost:8080/course/testing";
        RestTemplate restTemplate = new RestTemplate();
        student result = restTemplate.getForObject(StudUri, student.class);
        Collection response = restTemplate.getForObject(CourseUri, Collection.class);
        List<course> courses = (List<course>) response;
		model.addAttribute("student",result);
        model.addAttribute("courses",courses);
		return "StudentDetails.html";
	}

    @GetMapping("/student/register/{name}")
    public String getAttr(Model model,@PathVariable(value="name") String studname) {
            model.addAttribute("names",new ArrayList<String>(Arrays.asList(studname)));

            return "test.html";
    }

    @GetMapping("/register")
	public String register(Model model) 
	{
        // List<String> names = new ArrayList<String>();
        // names.add("Dylan");
        // names.add("cameron");


        // String StudUri = "http://localhost:8080/student/find/2";
        // String CourseUri = "http://localhost:8080/course/testing";
        // RestTemplate restTemplate = new RestTemplate();
        // student result = restTemplate.getForObject(StudUri, student.class);
        // Collection response = restTemplate.getForObject(CourseUri, Collection.class);
        // List<course> courses = (List<course>) response;
		// model.addAttribute("student",result);
        // model.addAttribute("courses",courses);
        model.addAttribute("newStudent",new student());
		return "StudentRegistration.html";
	}



    
    // @RequestMapping("/test")
    // public ModelAndView index () {
    //     ModelAndView modelAndView = new ModelAndView();
    //     modelAndView.setViewName("test.html");
    //     return modelAndView;
    // }

}
