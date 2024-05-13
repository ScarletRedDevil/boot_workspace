package com.sds.movieapp.model.member;

import org.springframework.stereotype.Service;

import com.sds.movieapp.domain.Role;

@Service
public class RoleServiceImpl implements RoleService{

	private RoleDAO roleDAO;
	
	public Role selectByName(String Role_name) {
		
		return roleDAO.selectByName(Role_name);
	}

}
