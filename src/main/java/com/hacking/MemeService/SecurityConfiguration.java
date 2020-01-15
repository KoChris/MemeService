package com.hacking.MemeService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${admin.username}")
    private String username;

    @Value("${admin.password}")
    private String password;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        log.info("username and password is: {}, {}", username, password );

        auth.inMemoryAuthentication()
                .withUser(username)
                .password(encoder().encode(password))
                .roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/api/admin/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/api/memes/").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/api/memes/").hasRole("ADMIN")
                .anyRequest().permitAll().and()
                .httpBasic().and()
                .cors().and()
                .csrf().disable();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}
