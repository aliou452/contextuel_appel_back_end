package com.stgson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("api/v1/home")
public class ContextualAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContextualAppApplication.class, args);
	}

	@GetMapping
	public String home(){
		return "Hello everybody";
	}
}
