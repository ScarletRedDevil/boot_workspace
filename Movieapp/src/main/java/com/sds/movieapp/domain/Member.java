package com.sds.movieapp.domain;

import lombok.Data;

@Data
public class Member {
	private int member_idx;
	private String uid;
	private String nickname;
	private String email;
	
	private Sns sns; //has a 관계로 erd 상의 부모를 보유
	private Role role; //has a 관계로 erd 상의 부모를 보유
	
	private MemberDetail memberDetail;
}