package com.sds.movieapp.model.member;

import com.sds.movieapp.domain.Sns;

public interface SnsService {
	public Sns selectByName(String sns_name);
}