package com.sds.movieapp.domain;

import lombok.Data;

@Data
public class MemberDetail {

	private int member_detail_idx;
	private String password;
	private String phone;
	private String addr;
	
	private Member member; // member를 보유
}
