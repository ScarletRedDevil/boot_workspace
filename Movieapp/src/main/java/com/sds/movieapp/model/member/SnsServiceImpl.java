package com.sds.movieapp.model.member;

import org.springframework.stereotype.Service;

import com.sds.movieapp.domain.Sns;

@Service
public class SnsServiceImpl implements SnsService{
	
	private SnsDAO snsDAO;
	
	@Override
	public Sns selectByName(String sns_name) {
		return snsDAO.selectByName(sns_name);//naver 대입, naver에 대한 모든 정보 반환
	}
}