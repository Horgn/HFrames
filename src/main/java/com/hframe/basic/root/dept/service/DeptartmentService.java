package com.hframe.basic.root.dept.service;

import java.util.List;

import com.hframe.basic.base.BaseService;
import com.hframe.basic.root.dept.entity.DeptartmentDTO;

/**
 * 系统-部门Service
 * @author Horgn黄小锤
 * @date 2019年2月4日 下午11:07:44
 * @version V1.0
 */
public interface DeptartmentService extends BaseService<DeptartmentDTO>{
	
	/**
	 * 获取直接下级部门
	 * @author Horgn黄小锤
	 * @date 2019年2月4日 下午11:15:23
	 * @param sId
	 * @return
	 */
	List<DeptartmentDTO> getDeptartments(String sId);
	
	/**
	 * 获取指定部门下的所有子部门id，包括当前部门
	 * @author Horgn黄小锤
	 * @date 2019年2月4日 下午11:43:51
	 * @param sId 
	 * @return
	 */
	List<Object> getAllSonDeptIds(String sId);
	
	/**
	 * 根据部门等级获取部门列表
	 * @author Horgn黄小锤
	 * @date 2019年2月9日 下午1:00:50
	 * @param iDetpLev
	 * @return
	 */
	List<DeptartmentDTO> getDepartmentsByLev(Integer iDetpLev);
	
	/**
	 * 判断指定部门下是否有子部门，有则返回true
	 * @author Horgn黄小锤
	 * @date 2019年2月9日 下午2:54:03
	 * @param sParentId
	 * @return
	 */
	Boolean hasSonDepts(String sParentId);

}
