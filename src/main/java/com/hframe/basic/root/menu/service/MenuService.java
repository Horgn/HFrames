package com.hframe.basic.root.menu.service;

import java.util.List;
import java.util.Map;

import com.hframe.basic.base.BaseService;
import com.hframe.basic.root.menu.entity.MenuDTO;

/**
 * 系统菜单Service
 * @author Horgn黄小锤
 * @date 2019年1月30日 上午11:03:44
 * @version V1.0
 */
public interface MenuService extends BaseService<MenuDTO>{
	
	/**
	 * 获取所有主菜单
	 * @author Horgn黄小锤
	 * @date 2019年2月2日 下午4:40:42
	 * @return
	 */
	List<MenuDTO> getMainMenus();
	
	/**
	 * 获取指定菜单下的所有子分组菜单
	 * @author Horgn黄小锤
	 * @date 2019年1月30日 上午11:09:09
	 * @param sParentId
	 * @return
	 */
	List<MenuDTO> getGroupMenus(String sParentId);
	
	/**
	 * 获取指定菜单下的所有子页面菜单
	 * @author Horgn黄小锤
	 * @date 2019年1月30日 上午11:09:09
	 * @param sParentId
	 * @return
	 */
	List<MenuDTO> getPageMenus(String sParentId);
	
	/**
	 * 获取指定菜单下的所有子元素菜单
	 * @author Horgn黄小锤
	 * @date 2019年1月30日 上午11:09:09
	 * @param sParentId
	 * @return
	 */
	List<MenuDTO> getElementMenus(String sParentId);
	
	/**
	 * 获取系统菜单ztree列表
	 * @author Horgn黄小锤
	 * @date 2019年2月7日 上午10:46:49
	 * @return
	 */
	Map<String, List<MenuDTO>> getZtreeMenu();
	
	/**
	 * 获取指定菜单的直接下级菜单，如果有，则返回true
	 * @author Horgn黄小锤
	 * @date 2019年2月7日 下午12:26:43
	 * @param menuDTO
	 * @return
	 */
	List<MenuDTO> getSonMenus(MenuDTO menuDTO);
	
	/**
	 * 判断指定菜单是否包含有子菜单
	 * @author Horgn黄小锤
	 * @date 2019年2月7日 下午1:36:45
	 * @param sParentId
	 * @return
	 */
	boolean checkSonCount(String sParentId);
	
	/**
	 * 根据菜单类型获取菜单列表
	 * @author Horgn黄小锤
	 * @date 2019年2月8日 上午11:49:48
	 * @param iMenuType
	 * @return
	 */
	public List<MenuDTO> getMenusByType(Integer iMenuType);
	
	/**
	 * 根据指定权限及菜单类型获取子菜单
	 * @author Horgn黄小锤
	 * @date 2019年2月11日 下午8:09:32
	 * @param sParentId 可为空
	 * @param iMenuType
	 * @return
	 */
	public List<MenuDTO> getMenuByLoginUserPermission(String sParentId,Integer iMenuType);
	
	/**
	 * 根据菜单id获取菜单列表
	 * @author Horgn黄小锤
	 * @date 2019年2月11日 下午8:31:34
	 * @param menuIds
	 * @return
	 */
	public List<MenuDTO> getMenusByIds(List<String> menuIds);

}
