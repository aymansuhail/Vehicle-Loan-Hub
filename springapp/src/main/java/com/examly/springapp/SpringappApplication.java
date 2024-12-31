package com.examly.springapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info=@Info(title="Vehicle_loan",version = "1.0",description = "Loan_management_apis"))
public class SpringappApplication {


	public static void main(String[] args) {
		SpringApplication.run(SpringappApplication.class, args);
	}
	@Bean
	public PasswordEncoder createPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}


}
