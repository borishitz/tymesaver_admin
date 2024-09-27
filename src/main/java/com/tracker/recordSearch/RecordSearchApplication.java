package com.tracker.recordSearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class RecordSearchApplication implements WebMvcConfigurer {

//	@Autowired
//	private EmailSenderService senderService;

	public void addViewController(ViewControllerRegistry registry){
		registry.addViewController("/track").setViewName("dashboard");
	}

	public static void main(String[] args) {
		SpringApplication.run(RecordSearchApplication.class, args);
	}
}
