package com.sds.movieapp.model.member;

import org.apache.ibatis.annotations.Mapper;

import com.sds.movieapp.domain.MemberDetail;

@Mapper
public interface MemberDetailDAO {

	public int insert(MemberDetail memberDetail);
	
}
