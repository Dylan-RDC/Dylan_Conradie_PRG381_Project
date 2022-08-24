package com.studentportal.webapp.frontEnd_Controllers;



import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.studentportal.webapp.models.student;
import com.studentportal.webapp.service.studentService;

 
@Controller
@RequestMapping("/")
public class loginRouter {
    
    @Autowired
    studentService studService;

    @PostMapping("/register/student")
    public ModelAndView registerStudent(@Validated @ModelAttribute("newStudent") student newStudent,Model model,ModelMap map)
    {
        try {
            if (studService.addStudent(newStudent)!="FAILED") {
                map.addAttribute("message","Successfully Registered");
                return new ModelAndView("redirect:/student/login", map);
            } 
            else
            {
                map.addAttribute("message","User already exists with the given Email");
                return new ModelAndView("redirect:/register", map);
            }
        } catch (Exception e) {
            map.addAttribute("message","An error occurred");
            return new ModelAndView("redirect:/register", map);
        }
    }

    @GetMapping("")
    public String viewHomePage()
    {
        return "home.html";
    }


    @GetMapping("/student/login")
	public String studentLogIn(Model model,@RequestParam("message") Optional<String> message) 
	{
    

        if ( message.isPresent()) {
            model.addAttribute("message",message.get());
        }

		return "studentLogin.html";
	}

    
    @GetMapping("/admin/login")
	public String adminLogIn(Model model) 
	{
		return "adminLogin.html";
	}

    @GetMapping("/register")
    public String getRegistration(Model model,@RequestParam("message") Optional<String> message)
    {
        if ( message.isPresent()) {
            model.addAttribute("message",message.get());
        }
    
        model.addAttribute("newStudent", new student());
        return "StudentRegistration.html";
    }


   

}
