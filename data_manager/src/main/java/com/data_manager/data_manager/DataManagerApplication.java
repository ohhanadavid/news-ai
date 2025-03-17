package com.data_manager.data_manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAsync(proxyTargetClass = true)
@EnableTransactionManagement(proxyTargetClass = true)

public class DataManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataManagerApplication.class, args);
	}

}
