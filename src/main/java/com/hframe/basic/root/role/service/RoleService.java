package com.hframe.basic.root.role.service;

import java.util.List;

import com.hframe.basic.base.BaseService;
import com.hframe.basic.root.role.entity.RoleDTO;

/**
 * 系统-角色Service
 * @author Horgn黄小锤
 * @date 2019年1月24日 下午6:11:57
 * @version V1.0
 */
public interface RoleService extends BaseService<RoleDTO>{
	
	/**
	 * 更改角色权限
	 * @author Horgn黄小锤
	 * @date 2019年2月7日 下午4:22:48
	 * @param sRoleId
	 * @param permissionIds
	 */
	public void changeRolePermissions(String sRoleId, List<String> permissionIds);
	

}
