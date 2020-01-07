package com.st11.dbshow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DbshowApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbshowApplication.class, args);
		System.out.println("============ DB Show Start ============");
	}

}
