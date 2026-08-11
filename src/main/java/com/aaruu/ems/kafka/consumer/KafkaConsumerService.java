package com.aaruu.ems.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.aaruu.ems.kafka.event.EmployeeCreatedEvent;
//
//@Service
//public class KafkaConsumerService {
//
//	@KafkaListener(topics = "employee-created", groupId = "employee-group", containerFactory = "kafkaListenerContainerFactory")
//	public void consumeEmployeeCreatedEvent(EmployeeCreatedEvent event) {
//
//		System.out.println("Received Employee Created Event from Kafka: " + event);
//	}
//}

@Service
public class KafkaConsumerService {

	@KafkaListener(topics = "employee-created", groupId = "employee-group", containerFactory = "kafkaListenerContainerFactory")
	public void consumeEmployeeCreatedEvent(EmployeeCreatedEvent event) {

		System.out.println("🔥 Received Employee Created Event from Kafka: " + event);
	}
}