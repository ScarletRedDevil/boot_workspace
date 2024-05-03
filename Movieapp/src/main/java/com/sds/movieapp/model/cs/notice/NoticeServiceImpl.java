package com.sds.movieapp.model.cs.notice;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.movieapp.domain.Notice;
import com.sds.movieapp.exception.NoticeException;

@Service
public class NoticeServiceImpl implements NoticeService{

	@Autowired
	private NoticeDAO noticeDAO;
	
	public int selectCount() {
		return noticeDAO.selectCount();
	}

	@Override
	public List selectAll(Map map) {
		return noticeDAO.selectAll(map);
	}

	@Override
	public Notice select(Notice notice) {
		return noticeDAO.select(notice);
	}
	
	@Override
	public void insert(Notice notice) throws NoticeException{
		noticeDAO.insert(notice);		
	}

	@Override
	public void update(Notice notice)  throws NoticeException{
		noticeDAO.update(notice);
	}

	@Override
	public void delete(Notice notice) throws NoticeException{
		noticeDAO.delete(notice);
		
	}
	
}