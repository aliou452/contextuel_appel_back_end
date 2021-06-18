package com.stgson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories()
public class ContextualAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContextualAppApplication.class, args);
	}

}
