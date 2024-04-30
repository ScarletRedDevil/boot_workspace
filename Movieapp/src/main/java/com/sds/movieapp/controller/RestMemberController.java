package com.sds.movieapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.sds.movieapp.sns.NaverLogin;

@RestController
public class RestMemberController {
	
	@Autowired
	private NaverLogin naverLogin;
	
	//로그인 요청에 필요한 링크 주소 및 파라미터 생성 요청 처리 
	@GetMapping("/rest/member/authform/{sns}")
	public ResponseEntity getLink(@PathVariable("sns") String sns) {
		
		ResponseEntity entity=ResponseEntity.ok(naverLogin.getGrantUrl());  //내용을 보내야 하므로, body도 구성하자 
		
		return entity; 
		
	}
}

