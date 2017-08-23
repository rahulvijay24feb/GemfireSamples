package com.example.gemdemoSDG;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;
import org.springframework.data.gemfire.repository.support.GemfireRepositoryFactoryBean;

@SpringBootApplication
@EnableGemfireRepositories
public class GemdemoSdgApplication {

//	GemfireRepositoryFactoryBean gm ;
	public static void main(String[] args) {
		SpringApplication.run(GemdemoSdgApplication.class, args);
	}
}
