package com.sds.testapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sds.testapp.model.notice.Notice;

@Controller
public class NoticeController {
	
	@GetMapping("/notice/list")
	public String GetList() {
		return "notice/list";
	}
	@GetMapping("/notice/writeform")
	public String getRegistForm() {
		return "notice/write";
	}
	
	//글쓰기 요청 처리
	@PostMapping("/notice/regist")
	public ModelAndView regist(Notice notice) {
		
		System.out.println("글쓰기 요청");
		ModelAndView mav = new ModelAndView("redirect:/notice/list");
	
		return mav;
	}

}
