package com.sds.testapp.model.notice;

import java.util.List;
import java.util.Map;

import com.sds.testapp.domain.Notice;

public interface NoticeService {

	public List selectAll(Map map); //모두 가져오기 
	public Notice select(int notice_idx); //한건 가져오기 
	public void insert(Notice notice);//한건 넣기
	public void update(Notice notice); //한건 수정 
	public void delete(Notice notice); //한건 삭제   	
}