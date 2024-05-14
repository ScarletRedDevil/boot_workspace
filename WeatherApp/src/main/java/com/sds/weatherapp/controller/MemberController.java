package com.sds.weatherapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sds.weatherapp.exception.MemberException;


@Controller
public class MemberController {

	//로그인 폼 요청 처리 
		@GetMapping("/member/loginform")
		public String getLoginForm() {
			
			return "member/login";
		}
		
		@ExceptionHandler(MemberException.class)
		public ModelAndView handle(MemberException e) {
			
			ModelAndView mav = new ModelAndView("error/result");
			mav.addObject("e", e);
			
			return mav;
		}
}
