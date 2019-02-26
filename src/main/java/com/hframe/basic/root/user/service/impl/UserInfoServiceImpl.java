package com.hframe.basic.root.user.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hframe.basic.base.BaseServiceImpl;
import com.hframe.basic.root.user.dao.UserInfoDao;
import com.hframe.basic.root.user.entity.UserInfoDTO;
import com.hframe.basic.root.user.service.UserInfoService;

@Service
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfoDTO> implements UserInfoService{
	
	@Autowired
	private UserInfoDao dao;
	
	@PostConstruct
	public void init(){
		super.dao = dao;
	}

	

}
