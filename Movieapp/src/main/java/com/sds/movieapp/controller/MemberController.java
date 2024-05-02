package com.sds.movieapp.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sds.movieapp.model.movie.NaverOAuthToken;
import com.sds.movieapp.sns.NaverLogin;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j //로거용 어노테이션
@Controller
public class MemberController {
	
	@Autowired
	private NaverLogin naverLogin;
	
	//로그인 폼 요청 처리
	@GetMapping("/member/loginform")
	public String getLoginForm() {
		
		return "member/login";
	}
	
	//네이버 서버에서 들어온 콜백 요청 처리
	//결과 처리 후 로그인 요청한 사용자가 보게 될 화면이므로 리턴은 html이 되어야 함.
	//따라서 modelandview 혹은 String 와야 함.
	@GetMapping("/member/sns/naver/callback")
	public ModelAndView naverCallback(HttpServletRequest request) {
		
		String code = request.getParameter("code");
		
		//토큰 요청을 위함 Post 헤더와 body 구성
		String token_url = naverLogin.getToken_request_url();
		
		//바디구성 
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("code", code);
		params.add("client_id", naverLogin.getClient_id());
		params.add("client_secret", naverLogin.getClient_secret());
		params.add("redirect_uri", naverLogin.getRedirect_uri()); //콜백 주소 
		params.add("grant_type", naverLogin.getGrant_type());
		params.add("state", naverLogin.getState());
		
		//post의 헤더 구성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/x-www-form-urlencoded");
		
		//머리와 몸을 합치기
		HttpEntity entity=new HttpEntity(params, headers);
		
		//비동기 방식으로  post 요청 (ajax 아님) 
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = restTemplate.exchange(token_url, HttpMethod.POST, entity, String.class);//요청 시작
		
		//응답정보에 들어있는 데이터 중 토큰 꺼내기
		String body = responseEntity.getBody();
		log.info("네이버가 보낸 인증 완료 정보는 "+body);
		
		//String 에 불과한 자료를 토큰을 접근하려면 JSON을 파싱해야 한다.(json simple, google)
		//jackson lib 에서 지원하는 ObjectMapper도 있음.
		ObjectMapper objectMapper = new ObjectMapper();
		
		NaverOAuthToken oAuthToken = null;
		
		try {
			oAuthToken = objectMapper.readValue(body, NaverOAuthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		//토큰정보를 이용해 네이버 회원정보 가져오기.
		String userinfo_url = naverLogin.getUserinfo_url();
		
		//Get 방식을 적용한 헤더 구성
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer "+oAuthToken.getAccess_token());
		HttpEntity entity2 = new HttpEntity(headers2);
		
		//비동기객체이용요청(프론트엔드의 ajax 아님)
		RestTemplate restTemplate2 = new RestTemplate();
		ResponseEntity<String> userEntity = restTemplate2.exchange(userinfo_url, HttpMethod.GET,  entity2, String.class);
		
		String userBody = userEntity.getBody();
		log.info(userBody);
		
		//사용자 정보 추출하기
		ObjectMapper objectMapper2 = new ObjectMapper();
		
		//준비된 DTO없을 경우  HashMap 꺼내기
		HashMap<String, Object> userMap = null;
		
		try {
			userMap = objectMapper2.readValue(userBody, HashMap.class);//두번째 인수는 인스턴스가 아닌 동적 클래스이므로, HashMap.class가 와야함
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Map response = (Map)userMap.get("response");
		
		String id = (String)response.get("id");
		String email = (String)response.get("email");
		String name = (String)response.get("name");
		
		//중복회원 없다면 가입시키기
		
		//세션 할당해 메인으로 보냄
		
		return null;
	}

}
