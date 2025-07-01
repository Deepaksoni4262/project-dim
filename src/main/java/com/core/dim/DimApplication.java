package com.core.dim;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class DimApplication {

	public static void main(String[] args) {
		SpringApplication.run(DimApplication.class, args);
	}

}
