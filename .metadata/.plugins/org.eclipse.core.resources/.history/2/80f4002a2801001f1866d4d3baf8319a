package com.sds.testapp.model.notice;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.testapp.domain.Notice;
import com.sds.testapp.exception.NoticeException;

@Service
public class NoticeServiceImpl implements NoticeService{
	
	@Autowired
	private NoticeDAO noticeDAO;
	
	@Override
	public List selectAll(Map map) {
		return noticeDAO.selectAll(map);
	}

	@Override
	public Notice select(int notice_idx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(Notice notice) throws NoticeException{
		int result = noticeDAO.insert(notice);

		if(result < 1) {
			throw new NoticeException("글등록 실패"); //일부러 에러 발생 try로 잡지 말자
		}
	}

	@Override
	public void update(Notice notice) {
		// TODO Auto-generated method stub
	}

	@Override
	public void delete(Notice notice) {
		// TODO Auto-generated method stub
	}
	
}