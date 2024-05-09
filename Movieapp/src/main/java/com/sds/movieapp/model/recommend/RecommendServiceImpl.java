package com.sds.movieapp.model.recommend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.movieapp.domain.CommentsDoc;
import com.sds.movieapp.domain.MovieDoc;
import com.sds.movieapp.model.comments.CommentsDocDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RecommendServiceImpl implements RecommendService{

	@Autowired
	private CommentsDocDAO commentsDocDAO;
	
	public List getList(int member_idx) {
		
		/*-----------------------------------------
		 1) 1차적으로 유저가 등록한 모든 영화평을 수집하자
		 2) 영화에 대한 메타정보도 수집하자 
		-----------------------------------------*/
		List<CommentsDoc> commentsList = commentsDocDAO.selectAllByMemember(member_idx);
		Map<Long, MovieDoc> metadataMap = new HashMap();//해당 영화평에 대한 영화들을 모아놓을 맵
		

		//위에서 수집한 순수한 List를  머아웃이 지원하는 전용  List로 전환
		//단 PreferenceArray는 머아웃 라이브러리가 지워하는 데이터 모델로 부터 얻어올 수 있다
		//그리고 이 모델은 우리가 정의하는 것이다..(사용자가 등록한 영화평 정보를 이용하여 모델을 만들어내야 함)
		CustomModel model = new CustomModel(member_idx, commentsList);
		DataModel dataModel = model.getDataModel();
		
		
		
		PreferenceArray preferenceArray = null;
		try {
			//유저가 평가한 영화평 목록
			preferenceArray = dataModel.getPreferencesFromUser((long)member_idx);
		} catch (TasteException e) {
			e.printStackTrace();
		}
		
		//최종확인 및 영화평 마다 1:1 대응하는 영화 메타데이터(감독,국가,장르,배우) 맵에 채워넣기
		for(int i=0;i<preferenceArray.length();i++) {
			 log.debug("평을 남긴 영화명은 "+preferenceArray.get(i).getItemID());  
		}
		
		return null;
	}
	
}