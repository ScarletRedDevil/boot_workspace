package com.sds.movieapp.model.recommend;

import java.util.List;

public interface RecommendService {

	//회원의 추천영화 목록 가져오기
	public List getList(int member_idx);
}
