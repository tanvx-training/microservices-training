package com.tanvx.cities;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MeasurementsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeasurementsApplication.class, args);
	}
}
