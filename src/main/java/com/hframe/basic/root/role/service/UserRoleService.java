package com.hframe.basic.root.role.service;

import java.util.List;

import com.hframe.basic.base.BaseService;
import com.hframe.basic.root.role.entity.UserRoleDTO;

/**
 * 系统-用户角色Service
 * @author Horgn黄小锤
 * @date 2019年1月24日 下午6:11:29
 * @version V1.0
 */
public interface UserRoleService extends BaseService<UserRoleDTO>{
	
	/**
	 * 根据用户id获取该用户所有角色
	 * @author Horgn黄小锤
	 * @date 2019年1月24日 下午6:25:52
	 * @param sUserId
	 * @return
	 */
	List<UserRoleDTO> findUserRoles(String sUserId);
	
	/**
	 * 根据用户id获取其所有角色的id
	 * @author Horgn黄小锤
	 * @date 2019年2月4日 下午2:28:48
	 * @param sUserId
	 * @return
	 */
	List<Object> findUserRolesId(String sUserId);
	
	/**
	 * 获取指定权限所拥有的人数
	 * @author Horgn黄小锤
	 * @date 2019年2月4日 下午1:28:59
	 * @param sRoleId
	 * @return
	 */
	int getRoleUserCount(String sRoleId);
	
	

}
