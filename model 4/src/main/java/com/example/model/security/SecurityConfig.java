package com.example.model.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeRequests(authorizeRequests ->
                        {

                            try {
                                authorizeRequests
                                        .antMatchers("/", "/signup", "/login").permitAll()
                                        .antMatchers("/h2-console/**").permitAll()
                                        .anyRequest().authenticated()
                                        .and()
                                        .headers()
                                        .frameOptions()
                                        .disable();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }






                        }
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")
                                .defaultSuccessUrl("/welcome")
                                .permitAll()
                )
                .logout(logout ->
                        logout
                                .permitAll()

                );
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

