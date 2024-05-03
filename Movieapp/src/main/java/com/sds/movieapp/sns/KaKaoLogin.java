package com.sds.movieapp.sns;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

//외부 설정파일에 등록한 각종 파라미터들을 이 클래스로 불러와서 한꺼번에 가지고 다니기도 하고,
//요청 주소를 생성해주기도 함..(편의성 목적..zino style)
@Component
@Data
public class KaKaoLogin {
	@Value("${sns.kakao.endpoint}")
	private String endpoint;
	
	@Value("${sns.kakao.client_id}")
	private String client_id;
	
	@Value("${sns.kakao.redirect_uri}")
	private String redirect_uri;
	
	@Value("${sns.kakao.response_type}")
	private String response_type;
	
	@Value("${sns.kakao.token_request_url}")
	private String token_request_url;
	
	@Value("${sns.kakao.grant_type}")
	private String grant_type;
	
	@Value("${sns.kakao.userinfo_url}")
	private String userinfo_url;
	
	//로그인 요청 시 가져갈 파라미터 문자열 
	public String getGrantUrl() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(endpoint+"?client_id="+client_id);
		sb.append("&redirect_uri="+redirect_uri);
		sb.append("&response_type="+response_type);
		
		return sb.toString();
	}
}













