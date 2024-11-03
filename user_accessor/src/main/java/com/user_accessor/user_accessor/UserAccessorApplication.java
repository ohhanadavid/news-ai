package com.user_accessor.user_accessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.user_accessor.user_accessor.DAL")
public class UserAccessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserAccessorApplication.class, args);
	}

}
