package com.studentportal.webapp.security;
 
import java.util.*;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import com.studentportal.webapp.models.Iuser;
import com.studentportal.webapp.models.admin;
import com.studentportal.webapp.models.student;
 

 
public class MyUserDetails implements UserDetails {
 
    private Iuser user;
     
    public MyUserDetails(Iuser User) {
        this.user = User; 
    }
 
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        if (student.class.isInstance(user)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_STUDENT"));
        }

        if (admin.class.isInstance(user)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }        
     return authorities;
    }
    
    public Iuser getUser()
    {
        return this.user;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }
 
    @Override
    public String getUsername() {
        return user.getUsername();
    }
 
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
 
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
 
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
 
    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }


}