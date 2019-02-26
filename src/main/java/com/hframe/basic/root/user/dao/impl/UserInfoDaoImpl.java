package com.hframe.basic.root.user.dao.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hframe.basic.base.BaseDaoImpl;
import com.hframe.basic.root.user.dao.UserInfoDao;
import com.hframe.basic.root.user.entity.UserDTO;
import com.hframe.basic.root.user.entity.UserInfoDTO;
import com.hframe.basic.root.user.mapper.UserInfoMapper;

@Repository
public class UserInfoDaoImpl extends BaseDaoImpl<UserInfoDTO> implements UserInfoDao{
	
	@Autowired
	private UserInfoMapper mapper;
	
	@PostConstruct
	public void init(){
		super.mapper = mapper;
	}

	@Override
	public String saveByUser(UserDTO user) {
		
		if(null == user){
			throw new RuntimeException("参数不能为空");
		}
		
		UserInfoDTO info = new UserInfoDTO(user.getsId());
		info.setdCreateDate(user.getdCreateDate());
		info.setsModifieder(user.getsModifieder());
		
		return super.save(info);
	}

	
	

	
	

}
