package com.sds.movieapp.sns;

import lombok.Data;

//네이버 서버로 부터 받은 토큰을 포함한 데이터를 보관할 객체
@Data
public class KaKaoOAuthToken {
	private String access_token;
	private String token_type;
	private String refresh_token;
	private String id_token;
	private String expires_in;
	private String scope;
	private String refresh_token_expires_in;
	
}