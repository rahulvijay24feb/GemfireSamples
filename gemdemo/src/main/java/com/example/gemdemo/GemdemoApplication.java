package com.example.gemdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

@SpringBootApplication
@SuppressWarnings("unused")
public class GemdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(GemdemoApplication.class, args);
	}
	
}
