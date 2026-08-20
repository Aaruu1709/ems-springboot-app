package com.aaruu.ems.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class DepartmentClient {

	private final RestTemplate restTemplate;

	public DepartmentClient(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public Object getDepartmentById(Integer departmentId) {

		String url = "http://localhost:8082/departments/" + departmentId;

		return restTemplate.getForObject(url, Object.class);
	}
}