package com.sds.movieapp.model.movie;

import java.util.List;
import java.util.Map;

import com.sds.movieapp.domain.Movie;

public interface MovieService {
	public int selectCount();
	public List selectAll(Map map);
	public void regist(Movie movie);//1건 등록
	public List getMovieTypeList();//영화 유형 가져오기
}