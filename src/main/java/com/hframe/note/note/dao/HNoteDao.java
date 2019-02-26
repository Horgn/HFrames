package com.hframe.note.note.dao;

import java.util.List;

import com.hframe.basic.base.BaseDao;
import com.hframe.note.note.entity.HNoteDTO;

/**
 * 小锤笔记-笔记实体Dao
 * @author Horgn黄小锤
 * @date 2019年2月9日 下午8:00:44
 * @version V1.0
 */
public interface HNoteDao extends BaseDao<HNoteDTO>{
	
	/**
	 * 获取指定用户下的所有笔记名称，如果参数为空，则获取当前登录用户的笔记
	 * @author Horgn黄小锤
	 * @date 2019年2月10日 下午6:14:30
	 * @param sUserId
	 * @return
	 */
	List<HNoteDTO> getNoteNameByUser(String sUserId);
	
	/**
	 * 获取指定分类的所有笔记名称
	 * @author Horgn黄小锤
	 * @date 2019年2月9日 下午8:59:47
	 * @param sCategoryId
	 * @return
	 */
	List<HNoteDTO> getNoteNameByCategory(String sCategoryId);
	
	/**
	 * 异步增加笔记访问量（+1）
	 * @author Horgn黄小锤
	 * @date 2019年2月22日 上午11:17:30
	 * @param sNoteId
	 */
	void asyncAddReadCount(String sNoteId);

}
