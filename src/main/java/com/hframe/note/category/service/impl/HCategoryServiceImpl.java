package com.hframe.note.category.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hframe.basic.base.BaseServiceImpl;
import com.hframe.basic.root.RootUtils;
import com.hframe.basic.util.StringUtils;
import com.hframe.note.category.dao.HCategoryDao;
import com.hframe.note.category.entity.HCategoryDTO;
import com.hframe.note.category.service.HCategoryService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class HCategoryServiceImpl extends BaseServiceImpl<HCategoryDTO> implements HCategoryService{
	
	@Autowired
	private HCategoryDao dao;
	
	@PostConstruct
	public void init(){
		super.dao = dao;
	}

	@Override
	public List<HCategoryDTO> getHCotegorysByUser(String sUserId) {
		
		Example example = new Example(HCategoryDTO.class);
		Criteria criteria = example.createCriteria();
		example.setOrderByClause(" iOrder desc ");
		
		criteria.andEqualTo("iCategoryLev", 0);
		if(StringUtils.isEmpty(sUserId)){
			criteria.andEqualTo("sCreator", RootUtils.getLoginUserId());
		}else{
			criteria.andEqualTo("sCreator", sUserId);
		}
		
		return dao.findList(example);
	}

	@Override
	public List<HCategoryDTO> getSonCotegorys(String sParentId) {
		
		if(StringUtils.isEmpty(sParentId)){
			throw new RuntimeException("参数不能为空");
		}
		
		Example example = new Example(HCategoryDTO.class);
		Criteria criteria = example.createCriteria();
		example.setOrderByClause(" iOrder desc ");
		
		criteria.andEqualTo("sParentId", sParentId);
		criteria.andEqualTo("iCategoryLev", 1);
		
		return dao.findList(example);
	}

	@Override
	public List<HCategoryDTO> getSonHCotegorysByUser(String sUserId) {
		
		Example example = new Example(HCategoryDTO.class);
		Criteria criteria = example.createCriteria();
		example.setOrderByClause(" iOrder desc ");
		
		criteria.andEqualTo("iCategoryLev", 1);
		if(StringUtils.isEmpty(sUserId)){
			criteria.andEqualTo("sCreator", RootUtils.getLoginUserId());
		}else{
			criteria.andEqualTo("sCreator", sUserId);
		}
		
		return dao.findList(example);
	}

	
	

}
