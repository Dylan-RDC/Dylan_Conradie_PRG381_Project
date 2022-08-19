package com.studentportal.webapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.studentportal.webapp.models.Iuser;
import com.studentportal.webapp.models.student;
import com.studentportal.webapp.repo.adminRepo;
import com.studentportal.webapp.repo.studentRepo;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private adminRepo adminRep;

    @Autowired
    private studentRepo studRep;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
            Iuser admin = adminRep.getAdminByEmail(email);
            Iuser stud = studRep.getStudentByEmail(email);

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
