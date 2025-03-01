package com.nicolas.microservice_hotel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MicroserviceHotelApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceHotelApplication.class, args);
	}

}
