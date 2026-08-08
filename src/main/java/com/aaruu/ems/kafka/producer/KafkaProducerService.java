package com.aaruu.ems.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.aaruu.ems.kafka.event.EmployeeCreatedEvent;

@Service
public class KafkaProducerService {

	private static final String TOPIC = "employee-created";

	private final KafkaTemplate<String, EmployeeCreatedEvent> kafkaTemplate;

	public KafkaProducerService(KafkaTemplate<String, EmployeeCreatedEvent> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public void sendEmployeeCreatedEvent(EmployeeCreatedEvent event) {

		kafkaTemplate.send(TOPIC, event.getEmployeeId().toString(), event);

		System.out.println("Employee event sent to Kafka: " + event);
	}
}