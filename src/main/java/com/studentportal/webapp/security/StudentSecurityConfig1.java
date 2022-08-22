// package com.studentportal.webapp.security;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.core.annotation.Order;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;

// @Configuration  
// @Order(2)
// public class StudentSecurityConfig {
    


//     @Bean
//     public SecurityFilterChain filterChainStudent(HttpSecurity http) throws Exception
//     {
//         http.authorizeRequests().antMatchers("/").permitAll();

//         http.antMatcher("/student/**").authorizeRequests().anyRequest().hasAuthority("STUDENT")
//             .and()
//             .formLogin()
//                 .loginPage("/student/login")
//                 .usernameParameter("email")
//                 .passwordParameter("password")
//                 .loginProcessingUrl("/student/login")
//                 .defaultSuccessUrl("/student/display/details")
//                 .permitAll()
//             .and()
//                 .logout().logoutUrl("/student/logout")
//                 .logoutSuccessUrl("/");
           

//         return http.build();
//     }
// }
