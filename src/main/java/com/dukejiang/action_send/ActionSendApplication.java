package com.dukejiang.action_send;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.dukejiang.action_send.repository")
@EntityScan
public class ActionSendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActionSendApplication.class, args);
	}

}
