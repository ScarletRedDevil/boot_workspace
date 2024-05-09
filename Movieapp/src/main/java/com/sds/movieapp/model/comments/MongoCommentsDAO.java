package com.sds.movieapp.model.comments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.sds.movieapp.domain.CommentsDoc;
import com.sds.movieapp.exception.CommentsException;

@Repository
public class MongoCommentsDAO implements CommentsDAO{

	@Autowired
	private MongoTemplate mongoTemplate;
	
	public void insert(CommentsDoc commentsdoc)throws CommentsException {
		
		try {
			mongoTemplate.insert(commentsdoc);
		} catch (Exception e) {
			throw new CommentsException("몽고 db 영화평 등록실패",e);
		}
	}

}
