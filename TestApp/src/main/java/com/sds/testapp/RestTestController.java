package com.sds.testapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestTestController {
	
	@GetMapping("/rest/test")
	public String getMsg() {
		
		return "hi";
	}
	
}