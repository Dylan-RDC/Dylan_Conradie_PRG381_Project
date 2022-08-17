// package com.studentportal.webapp.security;

// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.Collection;
// import java.util.HashSet;
// import java.util.*;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.userdetails.UserDetails;

// import com.studentportal.webapp.models.role;

// public class MyUserDetails implements UserDetails{

//     @Autowired
//     Set<role> roles = new HashSet<>(Arrays.asList(new role("ADMIN"),new role("STUDENT")));

//     @Override
//     public Collection<? extends GrantedAuthority> getAuthorities() {
        
//         return roles;
//     }

//     @Override
//     public String getPassword() {
//         // TODO Auto-generated method stub
//         return null;
//     }

//     @Override
//     public String getUsername() {
//         // TODO Auto-generated method stub
//         return null;
//     }

//     @Override
//     public boolean isAccountNonExpired() {
//         // TODO Auto-generated method stub
//         return false;
//     }

//     @Override
//     public boolean isAccountNonLocked() {
//         // TODO Auto-generated method stub
//         return false;
//     }

//     @Override
//     public boolean isCredentialsNonExpired() {
//         // TODO Auto-generated method stub
//         return false;
//     }

//     @Override
//     public boolean isEnabled() {
//         // TODO Auto-generated method stub
//         return false;
//     }
    
// }
