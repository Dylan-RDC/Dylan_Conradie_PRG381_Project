package com.studentportal.webapp.controllers;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class TestController {


    @GetMapping("/")
	public String homePage(Model model) 
	{
        List<String> names = new ArrayList<String>();
        names.add("Dylan");
        names.add("cameron");


		model.addAttribute("names",names);
		return "index1.html";
	}

    @GetMapping("/sutdent/register/{name}")
    public String getAttr(Model model,@PathVariable(value="name") String studname) {
            model.addAttribute("names",new ArrayList<String>(Arrays.asList(studname)));

            return "test.html";
    }



    // @RequestMapping("/test")
    // public ModelAndView index () {
    //     ModelAndView modelAndView = new ModelAndView();
    //     modelAndView.setViewName("test.html");
    //     return modelAndView;
    // }


}
