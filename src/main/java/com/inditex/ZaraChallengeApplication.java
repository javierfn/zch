package com.inditex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class ZaraChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZaraChallengeApplication.class, args);
	}

}
