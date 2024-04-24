package com.sds.testapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {
	
	//메인페이지 요청 처리 
	@GetMapping("/board/main")
	public String getMain() {
		
		return "board/index";
	}
	
}