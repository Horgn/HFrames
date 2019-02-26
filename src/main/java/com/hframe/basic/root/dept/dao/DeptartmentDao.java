package com.hframe.basic.root.dept.dao;

import java.util.List;

import com.hframe.basic.base.BaseDao;
import com.hframe.basic.root.dept.entity.DeptartmentDTO;

/**
 * 系统-部门Dao
 * @author Horgn黄小锤
 * @date 2019年2月4日 下午10:56:59
 * @version V1.0
 */
public interface DeptartmentDao extends BaseDao<DeptartmentDTO>{
	
	/**
	 * 获取指定部门下的所有子部门id，包括当前部门
	 * @author Horgn黄小锤
	 * @date 2019年2月4日 下午11:43:51
	 * @param sId 
	 * @return
	 */
	List<Object> getAllSonDeptIds(String sId);

}
