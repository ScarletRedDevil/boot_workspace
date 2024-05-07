package com.sds.movieapp.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection="movie")
public class Movie {
	
	@Id
	private String id;
	
	private int movie_idx;
	
	//영화추천에 필요한 필드
	private String[] genres; //장르
	private String movieNm; //영화명
	private String[] directors; //감독명
	private String[] actors; //배우명
	private String[] nations; //국가명
	private String content; //영화평
	

}
