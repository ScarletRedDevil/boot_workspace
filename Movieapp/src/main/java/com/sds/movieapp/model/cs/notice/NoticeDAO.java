package com.sds.movieapp.model.cs.notice;

import java.util.List;
import java.util.Map;

import com.sds.movieapp.domain.Notice;

public interface NoticeDAO {

	public int selectCount();//게시물 수 
	public List selectAll(Map map); //모든 게시물 가져오기
	public Notice select(Notice notice);
	public void insert(Notice notice);
	public void update(Notice notice);
	public void delete(Notice notice);
}
