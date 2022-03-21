package com.example.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@ComponentScan(basePackages = { "com.example" })
@EnableJpaRepositories(basePackages = "com.example.persistence")
@EntityScan(basePackages = "com.example.persistence")
public class ServletLoader {

	public static void main(String[] args) {
		SpringApplication.run(ServletLoader.class, args);
	}
}
