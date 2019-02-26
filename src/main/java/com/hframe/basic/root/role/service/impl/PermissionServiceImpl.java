package com.hframe.basic.root.role.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hframe.basic.base.BaseServiceImpl;
import com.hframe.basic.root.RootUtils;
import com.hframe.basic.root.role.dao.PermissionDao;
import com.hframe.basic.root.role.entity.PermissionDTO;
import com.hframe.basic.root.role.entity.RoleDTO;
import com.hframe.basic.root.role.entity.UserRoleDTO;
import com.hframe.basic.root.role.service.PermissionService;
import com.hframe.basic.root.user.entity.UserDTO;
import com.hframe.basic.util.StringUtils;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;


@Service
public class PermissionServiceImpl extends BaseServiceImpl<PermissionDTO> implements PermissionService{
	
	@Autowired
	private PermissionDao dao;
	
	@PostConstruct
	public void init(){
		super.dao = dao;
	}

	@Override
	public List<PermissionDTO> getPermissions(String sRoleId) {
		
		if(StringUtils.isEmpty(sRoleId)){
			return new ArrayList<>();
		}
		
		Example example = new Example(PermissionDTO.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("sRoleId", sRoleId);
		example.selectProperties("sId", "sMenuId", "sPremission");
		
		return dao.findList(example);
	}

	@Override
	public List<String> getPermissionsByLoginUser() {
		
		UserDTO loginUser = RootUtils.getLoginUser();
		if(null == loginUser){
			throw new RuntimeException("获取用户信息失败");
		}
		
		List<String> permissions = new ArrayList<>();
		List<UserRoleDTO> userRoles = loginUser.getUserRoles();
		
		userRoles.forEach((userRole) -> {
			RoleDTO role = userRole.getRole();
			permissions.add(role.getsRoleSign());
			
			List<PermissionDTO> permiList = RootUtils.PermissionService.getPermissions(role.getsId());
			permiList.forEach((permi) -> permissions.add(permi.getsPremission()));
		});
		
		return permissions;
	}

}
