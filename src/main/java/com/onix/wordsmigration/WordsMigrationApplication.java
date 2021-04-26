package com.onix.wordsmigration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class WordsMigrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(WordsMigrationApplication.class, args);
	}

}
