package com.hframe.basic.root.dept.dao.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hframe.basic.base.BaseDaoImpl;
import com.hframe.basic.root.dept.dao.DeptLeaderDao;
import com.hframe.basic.root.dept.entity.DeptLeaderDTO;
import com.hframe.basic.root.dept.mapper.DeptLeaderMapper;

@Repository
public class DeptLeaderDaoImpl extends BaseDaoImpl<DeptLeaderDTO> implements DeptLeaderDao{
	
	@Autowired
	private DeptLeaderMapper mapper;
	
	@PostConstruct
	public void init(){
		super.mapper = mapper;
	}

	/* (non-Javadoc)
	 * @see com.hframe.basic.base.BaseDaoImpl#checkExist(com.hframe.basic.base.BaseDTO)
	 */
	@Override
	public boolean checkExist(DeptLeaderDTO entity) {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
