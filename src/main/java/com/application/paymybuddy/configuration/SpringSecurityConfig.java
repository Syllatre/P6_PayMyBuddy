package com.application.paymybuddy.configuration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@AllArgsConstructor
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

//        PasswordEncoder passwordEncoder = passwordEncoder();
//        auth.inMemoryAuthentication()
//                .withUser("user").password(passwordEncoder.encode("1234")).roles("USER")
//                .and()
//                .withUser("admin").password(passwordEncoder.encode("1234")).roles("USER", "ADMIN");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.formLogin();
        http.authorizeHttpRequests().antMatchers("/").permitAll();
        http.authorizeHttpRequests().antMatchers("/transfert/**","/connection/**").hasRole("USER");
        http.authorizeHttpRequests().anyRequest().authenticated();
        http.exceptionHandling().accessDeniedPage("/403");
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
