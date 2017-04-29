package com.clone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan
@ComponentScan({"com.clone.controllers", "com.clone.repositories", "com.clone.config", "com.clone.services"})
@EnableJpaRepositories({"com.clone.controllers",  "com.clone.repositories"})
@SpringBootApplication
public class TwitterCloneApplication {

	/**
	 * Runs application
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(TwitterCloneApplication.class, args);
	}
}
