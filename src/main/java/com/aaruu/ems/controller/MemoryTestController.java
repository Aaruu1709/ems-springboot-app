package com.aaruu.ems.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class MemoryTestController {

//	@GetMapping("/string")
//	public String stringTest() {
//		String result = "";
//		for (int i = 0; i < 100000; i++) {
//			result = result + i;
//		}
//		return result;
//	}
//	

	@GetMapping("/builder")
	public String builderTest() {

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < 50000; i++) {
			sb.append(i);
		}

		return "Done";
	}

}
