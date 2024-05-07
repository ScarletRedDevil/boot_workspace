package com.sds.movieapp.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection="comments")
public class Comments {

	@Id
	private String id;
	
	//누가?
	private int member_idx;
	
	//어떤영화?
	private int movie_idx;
	
	//어떤 평으로?
	private String content;
	
//	어떤 점수로?
	private float score;

}
