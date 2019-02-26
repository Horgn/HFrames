package com.hframe.note.friend.dao;

import java.util.List;

import com.hframe.basic.base.BaseDao;
import com.hframe.basic.root.user.entity.UserDTO;
import com.hframe.note.friend.entity.HFriendDTO;

/**
 * 小锤笔记--好友Dao
 * @author Horgn黄小锤
 * @date 2019年2月21日 下午5:57:30
 * @version V1.0
 */
public interface HFriendDao extends BaseDao<HFriendDTO>{
	
	/**
	 * 根据用户id及关系状态获取所有好友
	 * @author Horgn黄小锤
	 * @date 2019年2月21日 下午6:04:00
	 * @param sUserId
	 * @param states 好友访问状态，第一个参数为对外状态，第二个状态为对内状态
	 * @return
	 */
	public List<UserDTO> getFriendsByUser(String sUserId, Integer... states);
	
	/**
	 * 根据用户id及关系状态获取所有好友id
	 * @author Horgn黄小锤
	 * @date 2019年2月21日 下午6:04:00
	 * @param sUserId
	 * @param states 好友访问状态，第一个参数为对外状态，第二个状态为对内状态
	 * @return
	 */
	public List<Object> getFriendIdsByUser(String sUserId, Integer... states);
	
	/**
	 * 判断当前用户的好友是否对自己开放，如果好友对当前用户开放，则返回true
	 * @author Horgn黄小锤
	 * @date 2019年2月21日 下午6:57:15
	 * @param sUserId 当前用户id
	 * @param friendId 好友用户id
	 * @return
	 */
	public Boolean checkFriendOutterState(String sUserId, String friendId);
	
	/**
	 * 获取当前用户的好友中对自己开放的好友id
	 * @author Horgn黄小锤
	 * @date 2019年2月21日 下午7:23:37
	 * @param sUserId
	 * @param friendIds
	 * @return
	 */
	public List<Object> getFriendOutterState(String sUserId, List<Object> friendIds);

}
