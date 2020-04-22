package com.st11.dbshow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration;

@SpringBootApplication(exclude = {MongoReactiveAutoConfiguration.class, MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class DbshowApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbshowApplication.class, args);
		System.out.println("============ DB Show Start ============");

	}

}
