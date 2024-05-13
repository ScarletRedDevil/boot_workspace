package com.sds.movieapp.model.member;

import com.sds.movieapp.domain.Role;

public interface RoleService {

	public Role selectByName(String Role_name);
}
