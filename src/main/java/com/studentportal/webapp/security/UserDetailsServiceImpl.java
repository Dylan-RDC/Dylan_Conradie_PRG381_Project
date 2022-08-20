package com.studentportal.webapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.studentportal.webapp.models.Iuser;
import com.studentportal.webapp.service.adminService;
import com.studentportal.webapp.service.studentService;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private adminService adService;

    @Autowired
    private studentService studService;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
            Iuser admin = adService.getAdminByEmail(email);
            Iuser stud = studService.getStudentByEmail(email);

            System.out.println(stud!=null);
            if (stud!=null) {
                return new MyUserDetails(stud);
            }

            if (admin!=null) {
                return new MyUserDetails(admin);
            }
            throw new UsernameNotFoundException("Could not find user");

    }
    
}
