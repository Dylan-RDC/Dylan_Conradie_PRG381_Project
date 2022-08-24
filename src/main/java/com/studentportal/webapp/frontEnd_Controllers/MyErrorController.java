package com.studentportal.webapp.frontEnd_Controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyErrorController implements ErrorController{

    @RequestMapping("/error")
    public String handleError()
    {
        return "errorPage.html";
    }

    @GetMapping("/403")
    public String error(Model model)
    {
        return "error403.html";
    }
}
