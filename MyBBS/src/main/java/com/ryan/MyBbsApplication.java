package com.ryan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class MyBbsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyBbsApplication.class, args);
	}

}
