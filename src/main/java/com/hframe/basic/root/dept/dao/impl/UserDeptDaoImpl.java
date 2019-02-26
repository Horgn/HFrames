package com.hframe.basic.root.dept.dao.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hframe.basic.base.BaseDaoImpl;
import com.hframe.basic.root.dept.dao.UserDeptDao;
import com.hframe.basic.root.dept.entity.UserDeptDTO;
import com.hframe.basic.root.dept.mapper.UserDeptMapper;

@Repository
public class UserDeptDaoImpl extends BaseDaoImpl<UserDeptDTO> implements UserDeptDao{

	@Autowired
	private UserDeptMapper mapper;
	
	@PostConstruct
	public void init() {
		super.mapper = mapper;
	}
}
