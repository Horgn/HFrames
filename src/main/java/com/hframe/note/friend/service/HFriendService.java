package com.hframe.note.friend.service;

import java.util.List;

import com.hframe.basic.base.BaseService;
import com.hframe.basic.root.user.entity.UserDTO;
import com.hframe.note.friend.entity.HFriendDTO;

/**
 * 小锤笔记--好友Service
 * @author Horgn黄小锤
 * @date 2019年2月21日 下午5:59:13
 * @version V1.0
 */
public interface HFriendService extends BaseService<HFriendDTO>{
	
	/**
	 * 根据用户id及关系状态获取所有好友
	 * @author Horgn黄小锤
	 * @date 2019年2月21日 下午6:04:00
	 * @param sUserId
	 * @param states 好友访问状态，第一个参数为对外状态，第二个状态为对内状态
	 * @return
	 */
	public List<UserDTO> getFriendsByUser(String sUserId, Integer... states);

}
