package com.studentportal.webapp.security;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import com.studentportal.webapp.models.student;
import com.studentportal.webapp.service.studentService;
 

 
public class studentDetailsService implements UserDetailsService {
 
    @Autowired
    studentService sService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        student current = sService.getStudentByEmail(username);
        
        if (current == null) {
            throw new UsernameNotFoundException("No use for for the given email");
        }

        return new MyUserDetails(current);
    }

}