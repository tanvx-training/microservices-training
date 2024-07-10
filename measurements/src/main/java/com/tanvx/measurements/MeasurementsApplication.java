package com.tanvx.measurements;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MeasurementsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeasurementsApplication.class, args);
	}
}
