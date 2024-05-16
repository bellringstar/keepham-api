package com.example.keephamapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class KeephamApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(KeephamApiApplication.class, args);
	}

}
