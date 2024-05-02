package com.sds.movieapp.model.member;

import org.apache.ibatis.annotations.Mapper;

import com.sds.movieapp.domain.Member;

@Mapper
public interface MemberDAO {
	public int insert(Member member); //회원등록
	public Member selectByUid(String uid);//uid 에 해당하는 회원정보 가져오기
}
