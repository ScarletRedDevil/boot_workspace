package com.sds.movieapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sds.movieapp.domain.CustomUserDetails;
import com.sds.movieapp.domain.Member;
import com.sds.movieapp.domain.Movie;
import com.sds.movieapp.model.movie.MovieService;

@Controller
public class MovieController {
	
	@Autowired
	private MovieService movieService;

	//영화 상세보기 요청 
	@GetMapping("/movie/detail")
	public String getDetail(Model model, @RequestParam(value = "movie_idx", defaultValue = "0")int movie_idx) {
		
		//최종적으로 우리 mysql 정보오 ㅏopenAPI정보가 합쳐진 DTO가져옴
		Movie movie = movieService.select(movie_idx);
		System.out.println("영화 url은"+movie.getUrl());
		System.out.println("영화 url은"+movie.getMovieNm());
		
		model.addAttribute("movie", movie); //여기에 html에서 쓰게되는 모든 정보 들어감.
		
		//스프링 시큐리티가 로그인을 처리했기 때문에 스프링 시큐리티를 통해 유저정보를 꺼내서
		//detail.html에서 사용할 수 있도록 request에 저장해놓기
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		String nickname = auth.getName();
		
//		model.addAttribute("nickname", nickname);
		
		//auth.getprincipal()은 로그인하지 않았을  경우 스프링 시큐리티가 반환값으로 anonymousUser를 반환함.
		//따라서 조건문으로 만들어야함.
		
		//아래 if문은 로그인한 경우에 동작함.
//		if(auth.getPrincipal() instanceof CustomUserDetails) {
//			CustomUserDetails userDetails = (CustomUserDetails)auth.getPrincipal();
//			Member member = userDetails.getMember();
//			
//			model.addAttribute("member", member);
//		};
		
//		위 주석들은 AuthAspect에서 처리하게 되면서 더이상 여기서 처리하지 않게 바뀜.
		
		return "movie/detail";
	}
}
