package com.hframe.basic.root.dept.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hframe.basic.base.BaseServiceImpl;
import com.hframe.basic.root.dept.dao.DeptLeaderDao;
import com.hframe.basic.root.dept.entity.DeptLeaderDTO;
import com.hframe.basic.root.dept.service.DeptLeaderService;

@Service
public class DeptLeaderServiceImpl extends BaseServiceImpl<DeptLeaderDTO> implements DeptLeaderService{

	@Autowired
	private DeptLeaderDao dao;
	
	@PostConstruct
	public void init(){
		super.dao = dao;
	}
}
