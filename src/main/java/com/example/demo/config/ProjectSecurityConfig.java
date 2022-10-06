package com.example.demo.config;

import com.example.demo.security.DemoUsernamePwdAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ProjectSecurityConfig /*extends WebSecurityConfigurerAdapter*/ {
/*
    @Autowired
    private DemoUsernamePwdAuthenticationProvider authenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Permit All Requests inside the Web Application
        http.authorizeRequests().
                    anyRequest().permitAll().
                    and().formLogin()
                    .and().httpBasic();
        // Deny All Requests inside the Web Application
        *//*http.authorizeRequests().
                anyRequest().denyAll().
                and().formLogin()
                .and().httpBasic();*//*

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("user).password("12345").roles("USER")
            .and().passwordEncoder(NoOpPasswordEncoder.getInstance());
    }
}*/

    /*
    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("12345")
                .roles("USER")
                .build();
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("54321")
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }
*/

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().ignoringAntMatchers("/saveMsg", "/public/**"/*, "/h2-console/**"*/)
                .and()
                    .authorizeRequests()
                        .mvcMatchers("/dashboard", "/displayProfile", "/updateProfile", "/saveMsg").authenticated()
                        .mvcMatchers("/displayMessages", "/closeMsg").hasRole("ADMIN")
                        .mvcMatchers("/**").permitAll()
                .and()
                    .httpBasic()
                .and()
                    .formLogin()
                        .loginPage("/login")
                        .defaultSuccessUrl("/dashboard")
                        .failureUrl("/login?error=true")
                .and()
                    .logout()
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true);
        /*http.headers().frameOptions().disable(); dùng cho h2 database */
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
