package com.hframe.basic.root.role.dao;

import java.util.List;

import com.hframe.basic.base.BaseDao;
import com.hframe.basic.root.role.entity.UserRoleDTO;

/**
 * 系统-用户角色Dao
 * @author Horgn黄小锤
 * @date 2019年1月24日 下午6:04:53
 * @version V1.0
 */
public interface UserRoleDao extends BaseDao<UserRoleDTO>{
	
	/**
	 * 删除指定用户的所有角色
	 * @author Horgn黄小锤
	 * @date 2019年2月4日 下午3:17:15
	 * @param sUserId
	 * @return
	 */
	public int deleteRolesByUser(String sUserId);
	
	/**
	 * 根据用户id获取其所有角色的id
	 * @author Horgn黄小锤
	 * @date 2019年2月4日 下午2:28:48
	 * @param sUserId
	 * @return
	 */
	List<Object> findUserRolesId(String sUserId);
}
