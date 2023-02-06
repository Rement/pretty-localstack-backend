package org.rement.localstackservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableFeignClients
@SpringBootApplication
public class LocalstackServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocalstackServiceApplication.class, args);
	}

}
