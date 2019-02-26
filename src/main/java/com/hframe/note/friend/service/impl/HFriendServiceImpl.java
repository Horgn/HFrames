package com.hframe.note.friend.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hframe.basic.base.BaseServiceImpl;
import com.hframe.basic.root.user.entity.UserDTO;
import com.hframe.note.friend.dao.HFriendDao;
import com.hframe.note.friend.entity.HFriendDTO;
import com.hframe.note.friend.service.HFriendService;


@Service
public class HFriendServiceImpl extends BaseServiceImpl<HFriendDTO> implements HFriendService{
	
	@Autowired
	private HFriendDao dao;
	
	@PostConstruct
	public void init(){
		super.dao = dao;
	}

	@Override
	public List<UserDTO> getFriendsByUser(String sUserId, Integer... states) {
		return dao.getFriendsByUser(sUserId, states);
	}

}
