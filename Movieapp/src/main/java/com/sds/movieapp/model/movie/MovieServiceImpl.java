package com.sds.movieapp.model.movie;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.movieapp.domain.Movie;
import com.sds.movieapp.exception.MovieException;

@Service
public class MovieServiceImpl implements MovieService{
	
	@Autowired
	private MovieDAO movieDAO;
	
	//영화진흥원 조회 서비스 객체 
	@Autowired
	private MovieApiService movieApiService;
	
	@Override
	public int selectCount() {
		return movieDAO.selectCount();
	}
	
	
	@Override
	public List selectAll(Map map) {
		List<Movie> siteMovieList = movieDAO.selectAll(map); //우리 사이트에 등록된 목록...이 List 에 들어있는 Movie에는 
		//  영화코드와 url만 존재함..따라서 영화코드를 이용하여 한건 가져오기 조회를 통해 비어있는 영화정보를 Movie dto 에 채워넣자
		
		for(Movie movie : siteMovieList) {
			//오픈 api 호출 객체에게   Movie DTO 맡겨서, 채워진 상태로 돌려받자!
			movieApiService.getMovie(movie);			
		}
		return siteMovieList;
	}
	
	//1건 등록
	public void regist(Movie movie) throws MovieException{
		movieDAO.insert(movie);
	}
	
	//영화 유형 가져오기
	public List getMovieTypeList() {
		return movieApiService.getTypeList();
	}
	
}
