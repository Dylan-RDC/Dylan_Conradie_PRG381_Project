package com.studentportal.webapp.frontEnd_Controllers;

import java.util.*;


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
            return String.format("redirect:/student/display/details?status=%s", "Successfully Updated Details");
        }
        else
        {
            return String.format("redirect:/student/display/details?status=%s", "Incorrect Credentials");
        }  

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
