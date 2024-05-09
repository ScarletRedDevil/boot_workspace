package com.sds.movieapp.model.comments;

import java.util.List;

import com.sds.movieapp.domain.CommentsDoc;

public interface CommentsDocDAO {
	public void insert(CommentsDoc commentsDoc); //영화평 등록 
	public List selectAllByMemember(int member_idx); //회원이 등록한 모든 영화평 목록 가져오기 
	public CommentsDoc select(int member_idx);//회원의 영화평 한건 가져오기
	
}