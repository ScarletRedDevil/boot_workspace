package com.sds.movieapp.model.recommend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.sds.movieapp.domain.SentimentDic;

@Repository
public class SentimentDicDAOImpl implements SentimentDicDAO{

	@Autowired
	private MongoTemplate mongoTemplate;

	//형태소 하나를, 감성 사전으로부터 검색하여 그 결과를 가져오자(부정 or 긍정 )
	public SentimentDic select(String word) {
		Query query = new Query(Criteria.where("ngram").is(word));
		
		return mongoTemplate.findOne(query, SentimentDic.class);
	}

}



