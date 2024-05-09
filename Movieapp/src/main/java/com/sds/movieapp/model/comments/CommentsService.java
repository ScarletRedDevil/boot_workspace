package com.sds.movieapp.model.comments;

import com.sds.movieapp.domain.CommentsDoc;
import com.sds.movieapp.domain.MovieDoc;

public interface CommentsService {
	
	public void registComments(CommentsDoc commentsDoc, MovieDoc movieDoc);
}