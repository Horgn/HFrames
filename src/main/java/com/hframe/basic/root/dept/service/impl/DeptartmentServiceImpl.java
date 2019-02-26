package com.hframe.basic.root.dept.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hframe.basic.base.BaseServiceImpl;
import com.hframe.basic.root.dept.dao.DeptartmentDao;
import com.hframe.basic.root.dept.entity.DeptartmentDTO;
import com.hframe.basic.root.dept.service.DeptartmentService;
import com.hframe.basic.util.StringUtils;

import tk.mybatis.mapper.entity.Example;


@Service
public class DeptartmentServiceImpl extends BaseServiceImpl<DeptartmentDTO> implements DeptartmentService{
	
	@Autowired
	private DeptartmentDao dao;
	
	@PostConstruct
	public void init(){
		super.dao = dao;
	}

	@Override
	public List<DeptartmentDTO> getDeptartments(String sId) {
		
		if(StringUtils.isEmpty(sId)){
			throw new RuntimeException("参数不能为空");
		}
		
		return dao.findList(new DeptartmentDTO(sId));
	}

	@Override
	public List<Object> getAllSonDeptIds(String sId) {
		
		if(StringUtils.isEmpty(sId)){
			throw new RuntimeException("参数不能为空");
		}
		return dao.getAllSonDeptIds(sId);
	}

	@Override
	public List<DeptartmentDTO> getDepartmentsByLev(Integer iDetpLev) {
		
		if(null == iDetpLev){
			return new ArrayList<>();
		}
		Example example = new Example(DeptartmentDTO.class);
		example.createCriteria().andEqualTo("iDeptLev", iDetpLev);
		example.setOrderByClause(" iOrder desc ");
		
		return dao.findList(example);
	}

	@Override
	public Boolean hasSonDepts(String sParentId) {
		
		if(StringUtils.isEmpty(sParentId)){
			throw new RuntimeException("参数不能为空");
		}
		
		DeptartmentDTO deptartmentDTO = new DeptartmentDTO();
		deptartmentDTO.setsParentId(sParentId);
		
		int count = dao.findCount(deptartmentDTO);
		if(count > 0){
			return true;
		}
		
		return false;
	}


}
