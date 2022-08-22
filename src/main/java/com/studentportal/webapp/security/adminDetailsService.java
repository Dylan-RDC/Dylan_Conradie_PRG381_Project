package com.studentportal.webapp.security;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import com.studentportal.webapp.models.admin;

import com.studentportal.webapp.service.adminService;
 

 
public class adminDetailsService implements UserDetailsService {
 
    @Autowired
    adminService aService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        admin current = aService.getAdminByEmail(username);
        
        if (current == null) {
            throw new UsernameNotFoundException("No use for for the given email");
        }

        return new MyUserDetails(current);
    }


}