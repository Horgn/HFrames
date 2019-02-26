package com.hframe.basic.root.dept.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hframe.basic.base.BaseServiceImpl;
import com.hframe.basic.root.dept.dao.UserDeptDao;
import com.hframe.basic.root.dept.entity.DeptartmentDTO;
import com.hframe.basic.root.dept.entity.UserDeptDTO;
import com.hframe.basic.root.dept.service.UserDeptService;
import com.hframe.basic.root.user.entity.UserDTO;
import com.hframe.basic.util.StringUtils;

@Service
public class UserDeptServiceImpl extends BaseServiceImpl<UserDeptDTO> implements UserDeptService{
	
	@Autowired
	private UserDeptDao dao;
	
	@PostConstruct
	public void init(){
		super.dao = dao;
	}

	@Override
	public List<UserDTO> getUsersByDept(String sDeptId) {
		if(StringUtils.isEmpty(sDeptId)){
			throw new RuntimeException("参数不可以为空");
		}
		
		return null;
	}

	@Override
	public List<DeptartmentDTO> getDeptsByUser(String sUserId) {
		if(StringUtils.isEmpty(sUserId)){
			throw new RuntimeException("参数不可以为空");
		}
		return null;
	}

	@Override
	public int countUsersByDept(String sDeptId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countDeptsByUser(String sUserId) {
		// TODO Auto-generated method stub
		return 0;
	}


}
