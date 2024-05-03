package com.sds.movieapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//몽고 db 연결예정
@Controller
public class NoticeController {

	//게시물 목록
	@GetMapping("/cs/notice/list")
	public String getList() {
		
		
		return "/cs/notice/list";
	}
	
	@GetMapping("/cs/notice/writeform")
	public String getWriteList() {
		
		
		return "/cs/notice/regist";
	}
}
