package com.sds.movieapp.model.member;

import org.apache.ibatis.annotations.Mapper;

import com.sds.movieapp.domain.Role;

@Mapper
public interface RoleDAO {
	public Role selectByName(String role_name);
}