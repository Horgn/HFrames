package com.hframe.basic.root.role.dao.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hframe.basic.base.BaseDaoImpl;
import com.hframe.basic.root.role.dao.UserRoleDao;
import com.hframe.basic.root.role.entity.UserRoleDTO;
import com.hframe.basic.root.role.mapper.UserRoleMapper;
import com.hframe.basic.util.StringUtils;


@Repository
public class UserRoleDaoImpl extends BaseDaoImpl<UserRoleDTO> implements UserRoleDao{

	@Autowired
	private UserRoleMapper mapper;
	
	@PostConstruct
	public void init(){
		super.mapper = mapper;
	}

	@Override
	public int deleteRolesByUser(String sUserId) {
		if(StringUtils.isEmpty(sUserId)){
			throw new RuntimeException("参数不能为空");
		}
		String sql = "delete from "+ getTableName() + " where sUserId='" + sUserId + "' ";
		return super.deleteBySql(sql);
	}

	@Override
	public List<Object> findUserRolesId(String sUserId) {
		
		if(StringUtils.isEmpty(sUserId)){
			throw new RuntimeException("参数不能为空");
		}
		
		String sql = "select sRoleId from "+ getTableName() + " where sUserId='"+sUserId+"' and iState=1 ";
		return super.selectOneColumnBySql(sql);
	}

	
}
