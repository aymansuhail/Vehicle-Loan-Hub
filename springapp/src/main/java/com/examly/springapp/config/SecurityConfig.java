package com.examly.springapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    PasswordEncoder passwordEncoder;

    private static final String[] AUTH_WHITE_LIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/v2/api-docs/**",
            "/swagger-resources/**"

    };

    public void configure(AuthenticationManagerBuilder authority) throws Exception {
        authority.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and().build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        {
            // ,"/api/**"
            http.csrf(csrf -> csrf.disable())
                    .cors().and()
                    .authorizeHttpRequests(authorizeRequests -> authorizeRequests.requestMatchers(AUTH_WHITE_LIST).permitAll()
                            .requestMatchers("/api/login","/api/register").permitAll()
                            .requestMatchers("/swagger-ui/**").permitAll()
                            .requestMatchers("/v3/api-docs/**").permitAll()
                            .requestMatchers(HttpMethod.POST, "/api/loan").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.GET, "/api/loan").hasAnyRole("ADMIN","USER")
                            .requestMatchers(HttpMethod.GET, "/api/loan/{loanId}").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.PUT, "/api/loan/{loanId}").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/api/loan/{loanId}").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.GET, "/api/loanapplication").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.GET, "/api/loanapplication/{loanapplicationId}").hasRole("USER")
                            .requestMatchers(HttpMethod.PUT, "/api/loanapplication/{loanapplicationId}").hasAnyRole("ADMIN","USER")
                            .requestMatchers(HttpMethod.GET, "/api/loanapplication/user/{userId}").hasRole("USER")
                            .requestMatchers(HttpMethod.POST, "/api/loanapplication").hasRole("USER")
                            .requestMatchers(HttpMethod.DELETE, "/api/loanapplication/{loanapplicationId}").hasRole("USER")
                            .requestMatchers(HttpMethod.GET, "/api/feedback").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.POST, "/api/feedback").hasRole("USER")
                            .requestMatchers(HttpMethod.GET, "/api/feedback/user/{userId}").hasRole("USER")
                            .requestMatchers(HttpMethod.GET, "/api/feedback/{id}").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.PUT, "/api/feedback/{id}").hasRole("USER")
                            .requestMatchers(HttpMethod.DELETE, "/api/feedback/{id}").hasRole("USER")
                            .anyRequest().authenticated())
                    .exceptionHandling(exceptionHandling -> exceptionHandling
                            .authenticationEntryPoint(jwtAuthenticationEntryPoint))
                    .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
            return http.build();
        }
    }

    private SecurityScheme createAPIKeyScheme(){
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
        .bearerFormat("JWT")
        .scheme("bearer");
    }

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI().addSecurityItem(new SecurityRequirement()
        .addList("Bearer Authentication"))
        .components(new Components().addSecuritySchemes("Bearer Authentication",createAPIKeyScheme()));
    }
}