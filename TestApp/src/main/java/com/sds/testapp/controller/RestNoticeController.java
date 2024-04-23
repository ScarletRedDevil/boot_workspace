package com.sds.testapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sds.testapp.model.notice.NoticeService;

/*
 * RestController는 REST방식 개발에 최적화된 컨트롤러.
 * REST는 비동기 방식과 상당히 연관성이 높음.
 * 즉 클라가 restful한 요청이 들어오면 서버측에서는 jsp, html이 아닌 순수 데이터 형태로 응답을 함
 * 데이터 : Json, xml, text..
 */
@RestController
public class RestNoticeController {
	
	@Autowired
	private NoticeService noticeService;
	
	@GetMapping("/notice")
	public List selectAll(){
		
		//3단계 일시키기
		Map map = new HashMap();
		map.put("startIndex", 0);
		map.put("rowCount",5);
		
		List noticeList = noticeService.selectAll(map);
		
		return noticeList;
	}
	
}
