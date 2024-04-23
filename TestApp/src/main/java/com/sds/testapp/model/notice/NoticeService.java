package com.sds.testapp.model.notice;

import java.util.List;

import com.sds.testapp.domain.Notice;

public interface NoticeService {
	public List selectAll();
	public Notice select(int notice_idx);
	public void insert (Notice notice);
	public void update (Notice notice);
	public void delete(Notice notice);
}
