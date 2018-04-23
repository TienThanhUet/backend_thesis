package com.satdio.dataIntegration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.stadio"})
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.stadio"})
@EnableMongoRepositories(basePackages = {"com.stadio.model.repository"})
@EnableAsync
public class DataIntegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataIntegrationApplication.class, args);
	}
}
