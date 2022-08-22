package com.studentportal.webapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration  
@Order(1)
public class AdminSecurityConfig {
    
    @Bean
    public UserDetailsService adminDetailsService()
    {
        return new adminDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder1() {
        return new BCryptPasswordEncoder(10);
    } 

    @Bean
    public DaoAuthenticationProvider adminAuthenticationProvider() 
    {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(adminDetailsService());
        provider.setPasswordEncoder(passwordEncoder1());
        return provider;
    }


    @Bean
    public SecurityFilterChain filterChainAdmin(HttpSecurity http) throws Exception
    {
        http.authorizeRequests().antMatchers("/").permitAll();
        http.authorizeRequests().antMatchers("/student/login").permitAll();
        http.authorizeRequests().antMatchers("/admin/login").permitAll();
        http.authenticationProvider(adminAuthenticationProvider() );
        http.antMatcher("/admin/**").authorizeRequests().anyRequest().hasAuthority("ADMIN")
            .and()
            .formLogin()
                .loginPage("/admin/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .loginProcessingUrl("/admin/login")
                .defaultSuccessUrl("/admin/display/students")
                .permitAll()
            .and()
                .logout().logoutUrl("/admin/logout")
                .logoutSuccessUrl("/")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/403");
           

        return http.build();
    }
}
