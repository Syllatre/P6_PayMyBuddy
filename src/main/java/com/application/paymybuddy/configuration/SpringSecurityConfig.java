package com.application.paymybuddy.configuration;

import com.application.paymybuddy.service.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@AllArgsConstructor
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private DataSource dataSource;

    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

//        auth.inMemoryAuthentication()
//                .withUser("user").password(passwordEncoder.encode("1234")).roles("USER")
//                .and()
//                .withUser("admin").password(passwordEncoder.encode("1234")).roles("USER", "ADMIN");
        auth.userDetailsService(userDetailsService);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.formLogin();
        http.authorizeHttpRequests().antMatchers("/").permitAll();
        http.authorizeHttpRequests().antMatchers("/register").permitAll();
        http.authorizeHttpRequests().antMatchers("/admin/**").hasAuthority("ADMIN");
        http.authorizeHttpRequests().antMatchers("/user/**").hasAuthority("USER");
        http.authorizeHttpRequests().antMatchers("/webjars/**").permitAll();
        http.authorizeHttpRequests().anyRequest().authenticated();
        http.exceptionHandling().accessDeniedPage("/403");
    }
}
