package com.hframe.note.category.dao.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hframe.basic.base.BaseDaoImpl;
import com.hframe.note.category.dao.HCategoryDao;
import com.hframe.note.category.entity.HCategoryDTO;
import com.hframe.note.category.mapper.HCategoryMapper;

@Repository
public class HCategoryDaoImpl extends BaseDaoImpl<HCategoryDTO> implements HCategoryDao{
	
	@Autowired
	private HCategoryMapper mapper;
	
	@PostConstruct
	public void init(){
		super.mapper = mapper;
	}
	
	

}
