package com.hframe.basic.root.role.dao;

import java.util.List;

import com.hframe.basic.base.BaseDao;
import com.hframe.basic.root.role.entity.PermissionDTO;

/**
 * 系统-角色权限Dao
 * @author Horgn黄小锤
 * @date 2019年1月24日 下午6:05:45
 * @version V1.0
 */
public interface PermissionDao extends BaseDao<PermissionDTO>{
	
	/**
	 * 删除指定角色下所有权限
	 * @author Horgn黄小锤
	 * @date 2019年2月7日 下午4:23:36
	 * @param sRoleId
	 */
	public void deleteRolePermi(String sRoleId);
	
	/**
	 * 获取指定角色所有权限id
	 * @author Horgn黄小锤
	 * @date 2019年2月8日 下午1:41:41
	 * @param sRoleId
	 * @return
	 */
	List<Object> getRolePermissionIds(String sRoleId);

}
