package com.hframe.basic.root.role.dao.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hframe.basic.base.BaseDaoImpl;
import com.hframe.basic.root.role.dao.RoleDao;
import com.hframe.basic.root.role.entity.RoleDTO;
import com.hframe.basic.root.role.mapper.RoleMapper;
import com.hframe.basic.util.StringUtils;

import tk.mybatis.mapper.entity.Example;

@Repository
public class RoleDaoImpl extends BaseDaoImpl<RoleDTO> implements RoleDao{

	@Autowired
	private RoleMapper mapper;
	
	@PostConstruct
	public void init(){
		super.mapper = mapper;
	}

	/* (non-Javadoc)
	 * @see com.hframe.basic.base.BaseDaoImpl#checkExist(com.hframe.basic.base.BaseEntity)
	 */
	@Override
	public boolean checkExist(RoleDTO entity) {
		if (null == entity) {
			throw new RuntimeException("实体参数不能为空");
		}
		Example example = new Example(entity.getClass());
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("sRoleName", entity.getsRoleName());
		criteria.orEqualTo("sRoleSign", entity.getsRoleSign());
		
		if (StringUtils.isNotEmpty(entity.getsId())) {
			Example.Criteria criteria2 = example.createCriteria();
			criteria2.andNotEqualTo("sId", entity.getsId());
			example.and(criteria2);
		}
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
