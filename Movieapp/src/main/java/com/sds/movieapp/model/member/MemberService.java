package com.sds.movieapp.model.member;

import com.sds.movieapp.domain.Member;

public interface MemberService {
	
	public void regist(Member member);
	public Member selectByUid(String uid); //uid에 해당하는 회원가져오기
}
