package com.sds.movieapp.model.member;

import org.apache.ibatis.annotations.Mapper;

import com.sds.movieapp.domain.Sns;

@Mapper
public interface SnsDAO {
	
	public Sns selectByName(String sns_name);
}