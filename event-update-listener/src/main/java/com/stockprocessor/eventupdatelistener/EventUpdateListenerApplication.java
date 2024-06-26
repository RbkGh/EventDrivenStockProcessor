package com.stockprocessor.eventupdatelistener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class EventUpdateListenerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventUpdateListenerApplication.class, args);
	}

}
