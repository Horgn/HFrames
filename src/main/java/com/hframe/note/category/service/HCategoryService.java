package com.hframe.note.category.service;

import java.util.List;

import com.hframe.basic.base.BaseService;
import com.hframe.note.category.entity.HCategoryDTO;

/**
 * 小锤笔记--笔记菜单Service
 * @author Horgn黄小锤
 * @date 2019年2月9日 下午7:50:12
 * @version V1.0
 */
public interface HCategoryService extends BaseService<HCategoryDTO>{
	
	/**
	 * 获取指定用户的父级笔记分类，如果参数为空，则获取当前登录用户的分类
	 * @author Horgn黄小锤
	 * @date 2019年2月9日 下午8:45:33
	 * @param sUserId
	 * @return
	 */
	List<HCategoryDTO> getHCotegorysByUser(String sUserId);
	
	/**
	 * 获取指定分类的子分类
	 * @author Horgn黄小锤
	 * @date 2019年2月9日 下午8:51:44
	 * @param sParentId
	 * @return
	 */
	List<HCategoryDTO> getSonCotegorys(String sParentId);
	
	/**
	 * 获取指定用户的二级笔记分类，如果参数为空，则获取当前登录用户的分类
	 * @author Horgn黄小锤
	 * @date 2019年2月9日 下午8:45:33
	 * @param sUserId
	 * @return
	 */
	List<HCategoryDTO> getSonHCotegorysByUser(String sUserId);
	
	

}
