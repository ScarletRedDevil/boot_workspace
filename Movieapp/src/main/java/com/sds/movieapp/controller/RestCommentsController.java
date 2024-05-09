package com.sds.movieapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sds.movieapp.domain.CommentsDoc;
import com.sds.movieapp.domain.MovieDoc;
import com.sds.movieapp.exception.CommentsException;
import com.sds.movieapp.model.comments.CommentsService;

import lombok.extern.slf4j.Slf4j;


//영화평 요청을 처리하는 하위 컨트롤러
@Slf4j
@RestController
public class RestCommentsController {

	@Autowired
	private CommentsService commentsService;
	
	//영화평 등록 요청을 처리 
	@PostMapping("/movie/comments")
	public ResponseEntity regist( CommentsDoc commentsDoc, MovieDoc movieDoc) {
		
		log.debug("호출여부 ");
		
		//영화평 작성자? 
		log.debug("member_idx = "+commentsDoc.getMember_idx());
		
		//장르
		for(int i=0;i<movieDoc.getGenres().length;i++) {
			log.debug("장르명 "+movieDoc.getGenres()[i]);
		}
		
		//영화명
		log.debug("영화명 "+movieDoc.getMovieNm());
		
		//감독
		for(int i=0;i<movieDoc.getDirectors().length;i++) {
			log.debug("감독명 "+movieDoc.getDirectors()[i]);
		}
		
		//배우
		for(int i=0;i<movieDoc.getActors().length;i++) {
			log.debug("배우명 "+movieDoc.getActors()[i]);
		}
		
		//제작 국가정보 
		for(int i=0;i<movieDoc.getNations().length;i++) {
			log.debug("국가명 "+movieDoc.getNations()[i]);
		}

		//영화평
		log.debug("영화평 "+commentsDoc.getContent());
		
		//3단계: 일 시키기 
		commentsService.registComments(commentsDoc, movieDoc);
		
		ResponseEntity entity = ResponseEntity.ok("몽고DB에 입력 성공");
		
		return entity;
	}
	
	@ExceptionHandler(CommentsException.class)
	public ResponseEntity handle(CommentsException e) {
		ResponseEntity entity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		return entity;
	}
}