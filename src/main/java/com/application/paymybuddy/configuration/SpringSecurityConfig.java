//package com.application.paymybuddy.configuration;
//
//import lombok.AllArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import javax.sql.DataSource;
//
//@AllArgsConstructor
//@Configuration
//@EnableWebSecurity
//public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    private DataSource dataSource;
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder authen) throws Exception{
//        authen.jdbcAuthentication()
//                .dataSource(dataSource);
//    }
////        authen.inMemoryAuthentication()
////                .withUser("springuser").password(passwordEncoder().encode("spring123")).roles("USER")
////                .and()
////                .withUser("root").password(passwordEncoder().encode("rootroot")).roles("ADMIN","USER");
////    }
//    @Override
//    public void configure(HttpSecurity http) throws Exception{
//        http.authorizeHttpRequests()
//                .antMatchers("/register").hasRole("ADMIN")
//                .antMatchers("/register").hasRole("USER")
//                .anyRequest().authenticated()
//                .and()
//                .formLogin();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//}
