package com.sds.movieapp.domain;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

//open api 에서 가져온 영화 1건을 담게될 DTO
@Data
public class Movie {
	private int movie_idx;
	
	//1편의 영화불러왔을때 사용할 변수
	private String movieCd;
	private String movieNm; //요청 파라미터와 중복
	private String movieNmEn;
	private String prdtYear;
	private String openDt;
	private String typeNm;
	private String prdtStatNm;
	private String nationAlt;
	private String genreAlt;
	private String repNationNm;
	private String repGenreNm;
	private List<Director> directors;
	private List<Company> companys;
	private List<Genre> genres;
	private List<Actor> actors;
	private List<Nation> nations;
	
	//영화 웹요청 파라미터
	private String curPage = "";//현재페이지
	private String itemPerPage = "100";//결과row수
	private String directorNm = "";//감독명
	private String openStartDt = "";//개봉연도 시작조건 ( YYYY )
	private String openEndDt = "";//개봉연도 끝조건 ( YYYY )	
	private String prdtStartYear = "";//제작연도 시작조건 ( YYYY )
	private String prdtEndYear = "";//제작연도 끝조건    ( YYYY )
	private String repNationCd = "";//대표국적코드 (공통코드서비스에서 '2204'로 조회된 국가코드)
	private String[] movieTypeCdArr=null;//영화형태코드 배열 (공통코드서비스에서 '2201'로 조회된 영화형태코드)
	
	//이미지 웹상의 경로
	private String url;
	
	MultipartFile file; //html 컴포넌트와 반드시 일치해야 자동으로 바이너리 파일이 매핑 됨..
	
}

