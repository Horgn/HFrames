package com.hframe.basic.root.role.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hframe.basic.base.BaseServiceImpl;
import com.hframe.basic.root.role.dao.UserRoleDao;
import com.hframe.basic.root.role.entity.UserRoleDTO;
import com.hframe.basic.root.role.service.UserRoleService;
import com.hframe.basic.util.StringUtils;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;



@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRoleDTO> implements UserRoleService{

	@Autowired
	private UserRoleDao dao;
	
	@PostConstruct
	public void init(){
		super.dao = dao;
	}
	

	@Override
	public List<UserRoleDTO> findUserRoles(String sUserId) {
		
		if(StringUtils.isEmpty(sUserId)){
			return new ArrayList<>();
		}
		
		Example example = new Example(UserRoleDTO.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("sUserId", sUserId);
		example.selectProperties("sId" ,"sRoleId");
		
		return dao.findList(example);
	}
	

	@Override
	public int getRoleUserCount(String sRoleId) {
		
		if(StringUtils.isEmpty(sRoleId)){
			throw new RuntimeException("参数不能为空");
		}
		UserRoleDTO userRole = new UserRoleDTO();
		userRole.setsRoleId(sRoleId);
		return dao.findCount(userRole);
	}
	

	@Override
	public List<Object> findUserRolesId(String sUserId) {
		return dao.findUserRolesId(sUserId);
	}


	
	

}
