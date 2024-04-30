package com.sds.movieapp.model.movie;

import java.util.List;
import java.util.Map;

import com.sds.movieapp.domain.Movie;

public interface MovieDAO {
	
	public int selectCount(); //총 레코드 수 
	public List selectAll(Map map);
	public void insert(Movie movie);
}