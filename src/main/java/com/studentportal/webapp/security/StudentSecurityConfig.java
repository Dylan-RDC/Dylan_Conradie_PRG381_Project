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
@Order(2)
public class StudentSecurityConfig {
    
    @Bean
    public UserDetailsService studentDetailsService()
    {
        return new studentDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder2() {
        return new BCryptPasswordEncoder(10);
    } 

    @Bean
    public DaoAuthenticationProvider studentAuthenticationProvider() 
    {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(studentDetailsService());
        provider.setPasswordEncoder(passwordEncoder2());
        return provider;
    }


    @Bean
    public SecurityFilterChain filterChainStudent(HttpSecurity http) throws Exception
    {
        http.authorizeRequests().antMatchers("/").permitAll();
        http.authorizeRequests().antMatchers("/student/login").permitAll();
        http.authorizeRequests().antMatchers("/admin/login").permitAll();
        // http.authorizeRequests().antMatchers("/register").permitAll();
        http.authenticationProvider(studentAuthenticationProvider());

        http.antMatcher("/student/**").authorizeRequests().anyRequest().hasRole("STUDENT")
        .and()
            .formLogin()
                .loginPage("/student/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .loginProcessingUrl("/student/login")
                .defaultSuccessUrl("/student/display/details")
                .permitAll()
            .and()
                .logout().logoutUrl("/student/logout")
                .logoutSuccessUrl("/")
                .and()
                .logout().logoutUrl("/student/logout")
                .logoutSuccessUrl("/")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/403");
           

        return http.build();
    }
}

