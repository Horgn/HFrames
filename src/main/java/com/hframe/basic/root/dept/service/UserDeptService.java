package com.hframe.basic.root.dept.service;

import java.util.List;

import com.hframe.basic.base.BaseService;
import com.hframe.basic.root.dept.entity.DeptartmentDTO;
import com.hframe.basic.root.dept.entity.UserDeptDTO;
import com.hframe.basic.root.user.entity.UserDTO;

/**
 * 系统-用户-部门关联表Service
 * @author Horgn黄小锤
 * @date 2019年2月4日 下午11:08:28
 * @version V1.0
 */
public interface UserDeptService extends BaseService<UserDeptDTO>{
	
	/**
	 * 获取指定部门下的所有用户（包括子部门）
	 * @author Horgn黄小锤
	 * @date 2019年2月4日 下午11:37:16
	 * @param sDeptId
	 * @return
	 */
	List<UserDTO> getUsersByDept(String sDeptId);
	
	/**
	 * 获取指定用户所属部门
	 * @author Horgn黄小锤
	 * @date 2019年2月4日 下午11:37:37
	 * @param sUserId
	 * @return
	 */
	List<DeptartmentDTO> getDeptsByUser(String sUserId);
	
	/**
	 * 计算指定部门下所有用户数量（包括子部门）
	 * @author Horgn黄小锤
	 * @date 2019年2月4日 下午11:52:56
	 * @param sDeptId
	 * @return
	 */
	int countUsersByDept(String sDeptId);
	
	/**
	 * 根据指定用户计算所属部门数量
	 * @author Horgn黄小锤
	 * @date 2019年2月4日 下午11:53:37
	 * @param sUserId
	 * @return
	 */
	int countDeptsByUser(String sUserId);
}
