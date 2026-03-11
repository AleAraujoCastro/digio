package com.teste.digio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DigioApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigioApplication.class, args);
	}

}
