package com.sds.movieapp.domain;

import lombok.Data;

@Data
public class MemberDetail {
	private int member_detail_idx;
	private String password;
	private String phone;
	private String addr;
	private Member member;//has a 관계로 erd 상의 부모를 보유
}