package com.eos.wool;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EosWoolGettingApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder().sources(EosWoolGettingApplication.class).web(false).run(args);
	}
}
