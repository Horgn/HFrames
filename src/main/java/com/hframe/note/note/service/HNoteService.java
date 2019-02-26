package com.hframe.note.note.service;

import java.util.List;


import com.hframe.basic.base.BaseService;
import com.hframe.note.note.entity.HNoteDTO;

/**
 * 小锤笔记--笔记实体Service
 * @author Horgn黄小锤
 * @date 2019年2月9日 下午8:04:39
 * @version V1.0
 */
public interface HNoteService extends BaseService<HNoteDTO>{
	
	/**
	 * 获取指定分类的所有笔记名称
	 * @author Horgn黄小锤
	 * @date 2019年2月9日 下午8:59:47
	 * @param sCategoryId
	 * @return
	 */
	List<HNoteDTO> getNoteNameByCategory(String sCategoryId);
	
	/**
	 * 获取指定用户下的所有笔记名称，如果参数为空，则获取当前登录用户的笔记
	 * @author Horgn黄小锤
	 * @date 2019年2月10日 下午6:14:30
	 * @param sUserId
	 * @return
	 */
	List<HNoteDTO> getNoteNameByUser(String sUserId);
	
	/**
	 * 获取指定用户的好友的可见笔记
	 * @author Horgn黄小锤
	 * @date 2019年2月21日 下午6:41:48
	 * @param sUserId
	 * @return
	 */
	List<HNoteDTO> getFriendsNodesByUser(String sUserId);
	
	/**
	 * 获取所有对外开发的笔记
	 * @author Horgn黄小锤
	 * @date 2019年2月21日 下午6:42:49
	 * @return
	 */
	List<HNoteDTO> getNoteByEveryone();
	
	/**
	 * 获取好友所有开放的笔记
	 * @author Horgn黄小锤
	 * @date 2019年2月21日 下午7:32:25
	 * @param sUserId 当前用户id
	 * @param sFriendId 好友id
	 * @return
	 */
	List<HNoteDTO> getNotesByFriend(String sUserId, String sFriendId);
	
	/**
	 * 获取分享链接中的笔记
	 * @author Horgn黄小锤
	 * @date 2019年2月22日 下午5:15:11
	 * @param sNoteId
	 * @return
	 */
	HNoteDTO getShareNote(String sNoteId);
	
}
