package com.baza.firmy;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport (pageSerializationMode = VIA_DTO)
public class FirmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirmsApplication.class, args);
	}

}
