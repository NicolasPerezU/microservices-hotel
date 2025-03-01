package com.nicolas.microservice_opinions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MicroserviceOpinionsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceOpinionsApplication.class, args);
	}

}
