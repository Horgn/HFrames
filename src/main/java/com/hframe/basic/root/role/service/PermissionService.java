package com.hframe.basic.root.role.service;

import java.util.List;

import com.hframe.basic.base.BaseService;
import com.hframe.basic.root.role.entity.PermissionDTO;

/**
 * 系统-角色权限Service
 * @author Horgn黄小锤
 * @date 2019年1月24日 下午6:12:18
 * @version V1.0
 */
public interface PermissionService extends BaseService<PermissionDTO>{
	
	/**
	 * 根据角色id获取该角色所有权限
	 * @author Horgn黄小锤
	 * @date 2019年1月24日 下午6:19:28
	 * @param sRoleId
	 * @return
	 */
	public List<PermissionDTO> getPermissions(String sRoleId);
	
	/**
	 * 获取当前登录用户菜单权限
	 * @author Horgn黄小锤
	 * @date 2019年2月11日 下午8:02:54
	 * @return
	 */
	public List<String> getPermissionsByLoginUser();
	
}
