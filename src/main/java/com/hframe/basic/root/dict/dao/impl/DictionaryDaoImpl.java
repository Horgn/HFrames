package com.hframe.basic.root.dict.dao.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hframe.basic.base.BaseDaoImpl;
import com.hframe.basic.root.dict.dao.DictionaryDao;
import com.hframe.basic.root.dict.entity.DictionaryDTO;
import com.hframe.basic.root.dict.mapper.DictionaryMapper;
import com.hframe.basic.util.StringUtils;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Repository
public class DictionaryDaoImpl extends BaseDaoImpl<DictionaryDTO> implements DictionaryDao{
	
	@Autowired
	private DictionaryMapper mapper;
	
	@PostConstruct
	public void init(){
		super.mapper = mapper;
	}

	@Override
	public boolean checkExist(DictionaryDTO dataDict) {
		
		if(null == dataDict || StringUtils.isEmpty(dataDict.getsDictName(), dataDict.getsDictValue())){
			throw new RuntimeException("字典名称或字典值不能为空");
		}
		
		if(null == dataDict.getiDictLevel()){
			throw new RuntimeException("数据等级不能为空");
		}
		
		Example example = new Example(DictionaryDTO.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("sDictName", dataDict.getsDictName());
		criteria.orEqualTo("sDictValue", dataDict.getsDictValue());
		
		if (StringUtils.isNotEmpty(dataDict.getsId())) {//如果是更新操作，则将跳过与自身的对比
			Example.Criteria criteria2 = example.createCriteria();
			criteria2.andNotEqualTo("sId", dataDict.getsId());
			example.and(criteria2);
		}
		
		Example.Criteria criteria2 = example.createCriteria();
		criteria2.andEqualTo("iDictLevel", dataDict.getiDictLevel());
		if(StringUtils.isNotEmpty(dataDict.getsParentId())){
			criteria2.andEqualTo("sParentId", dataDict.getsParentId());
		}
		
		example.and(criteria2);
		int count = 0;
		try {
			count = super.findCount(example);
		} catch (Exception e) {
			throw new RuntimeException("数据重复性校验失败");
		}
		
		if(count > 0){
			return true;
		}
		return false;
	}

}
