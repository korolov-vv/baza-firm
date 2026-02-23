package com.baza.firmy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@SpringBootApplication
@EnableSpringDataWebSupport (pageSerializationMode = VIA_DTO)
public class FirmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirmsApplication.class, args);
	}
}
