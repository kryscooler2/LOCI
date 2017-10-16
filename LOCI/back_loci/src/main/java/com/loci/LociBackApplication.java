package com.loci;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("unused")
@SpringBootApplication
public class LociBackApplication {
	public static void main(String[] args) {
		SpringApplication.run(LociBackApplication.class, args);
	}
}
