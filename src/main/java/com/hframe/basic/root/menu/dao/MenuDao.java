package com.hframe.basic.root.menu.dao;

import java.util.List;

import com.hframe.basic.base.BaseDao;
import com.hframe.basic.root.menu.entity.MenuDTO;

/**
 * 系统菜单Dao
 * @author Horgn黄小锤
 * @date 2019年1月30日 上午11:01:47
 * @version V1.0
 */
public interface MenuDao extends BaseDao<MenuDTO>{
	
	/**
	 * 根据指定权限及菜单类型获取子菜单
	 * @author Horgn黄小锤
	 * @date 2019年2月11日 下午8:09:32
	 * @param sParentId 可为空
	 * @param iMenuType
	 * @return
	 */
	public List<MenuDTO> getMenuByLoginUserPermission(String sParentId,Integer iMenuType);

}
