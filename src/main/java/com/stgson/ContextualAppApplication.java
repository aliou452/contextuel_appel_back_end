package com.stgson;

import com.stgson.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("api/v1")
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class ContextualAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContextualAppApplication.class, args);
	}

	@GetMapping(path = "home")
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_VERIFICATEUR')")
	public String home(){
		return "Hello everybody";
	}

	@GetMapping(path = "dash")
//	@PreAuthorize("hasAuthority('depot:write')")
	public String dashboard(){
		return "Hello everybody";
	}
}
