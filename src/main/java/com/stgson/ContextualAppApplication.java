package com.stgson;

import com.stgson.auth.AppUser;
import com.stgson.auth.AppUserRepository;
import com.stgson.auth.AppUserRole;
import com.stgson.auth.AppUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = AppUserRepository.class)
public class ContextualAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContextualAppApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(
			AppUserService appUserService) {
		return args -> appUserService.signUpUser(
				new AppUser(
						"Aliou",
						"SY",
						"781700136",
						"2525",
						AppUserRole.ADMIN,
						false,
						true
				)
		);
	}
}
