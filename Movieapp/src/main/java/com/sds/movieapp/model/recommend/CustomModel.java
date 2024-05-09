package com.sds.movieapp.model.recommend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericItemPreferenceArray;
import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;

import com.sds.movieapp.domain.CommentsDoc;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

//우리가 작성한 영화평 데이터를 이용한 데이터 모델 생성하기 위한 객체
//결국 개발자가 정의한 자료형을 아파치 머아웃에서 원하는 형태로 전환해 놓은 결과물음 담은 객체
@Slf4j
@Data
public class CustomModel {
	private DataModel dataModel; //이 모델이 우리가 보유한 데이터를 이해하도록 코드 작성.. 
	
	public CustomModel(int member_idx, List<CommentsDoc> commentsList) {
		//Map<사용자id , 영화평 리스트  >
		//GenericPreference 에는 {member_idx, movie_idx, score} : 영화 평 한개의 정보를 가진 객체 
		//Map<Long, List<GenericPreference>> preferMap=new HashMap();
		
		List<GenericPreference> preferList = new ArrayList();
		
		for(CommentsDoc commentsDoc : commentsList) {
			GenericPreference prefer = null;
			prefer = new GenericPreference(commentsDoc.getMember_idx(),commentsDoc.getMovie_idx() , commentsDoc.getScore());
			preferList.add(prefer);
		}
		
		//생성된 리스트를 최종적으로 맵에 넣어주되, 회원의 아이디가 key 값이 되어야 한다.. 
		//preferMap.put((long)member_idx,  preferList);
		
		FastByIDMap<PreferenceArray> userData = new FastByIDMap();
		userData.put(member_idx,  new GenericUserPreferenceArray(preferList));
		
		dataModel = new GenericDataModel(userData);
	}
}


