package com.sds.movieapp.sns;

import lombok.Data;

//네이버 서버로 부터 받은 토큰을 포함한 데이터를 보관할 객체
@Data
public class NaverOAuthToken {
	private String access_token;
	private String refresh_token;
	private String token_type;
	private String expires_in;
}