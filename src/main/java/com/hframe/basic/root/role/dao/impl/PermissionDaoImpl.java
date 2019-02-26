package com.hframe.basic.root.role.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hframe.basic.base.BaseDaoImpl;
import com.hframe.basic.root.role.dao.PermissionDao;
import com.hframe.basic.root.role.entity.PermissionDTO;
import com.hframe.basic.root.role.mapper.PermissionMapper;
import com.hframe.basic.util.StringUtils;

@Repository
public class PermissionDaoImpl extends BaseDaoImpl<PermissionDTO> implements PermissionDao{
	
	@Autowired
	private PermissionMapper mapper;
	
	@PostConstruct
	public void init(){
		super.mapper = mapper;
	}

	/* (non-Javadoc)
	 * @see com.hframe.basic.base.BaseDaoImpl#checkExist(com.hframe.basic.base.BaseEntity)
	 */
	@Override
	public boolean checkExist(PermissionDTO entity) {
//		if (null == entity) {
//			throw new RuntimeException("实体参数不能为空");
//		}
//		tk.mybatis.mapper.entity.Example example = new tk.mybatis.mapper.entity.Example(entity.getClass());
//		tk.mybatis.mapper.entity.Example.Criteria criteria = example.createCriteria();
//		criteria.andEqualTo("sRoleId", entity.getsRoleId());
//		criteria.andEqualTo("sPremission", entity.getsPremission());
//		//TODO
//		if (com.hframe.basic.util.StringUtils.isNotEmpty(entity.getsId())) {//如果是更新操作，则将跳过与自身的对比
//			tk.mybatis.mapper.entity.Example.Criteria criteria2 = example.createCriteria();
//			criteria2.andNotEqualTo("sId", entity.getsId());
//			example.and(criteria2);
//		}
//		int count = 0;
//		try {
//			count = findCount(example);
//		} catch (Exception e) {
//			throw new RuntimeException("数据重复性校验失败");
//		}
//		if (count > 0) {
//			return true;
//		}
		return false;
	}
	
	
	@Override
	public void deleteRolePermi(String sRoleId) {
		
		if(StringUtils.isEmpty(sRoleId)){
			throw new RuntimeException("请选择指定角色");
		}
		String sql = "DELETE FROM "+getTableName()+" WHERE sRoleId='"+sRoleId+"'";
		super.deleteBySql(sql);
	}
	
	
	@Override
	public List<Object> getRolePermissionIds(String sRoleId) {
		
		if(StringUtils.isEmpty(sRoleId)){
			return new ArrayList<>();
		}
		String sql = "SELECT sPremission FROM "+getTableName()+" WHERE sRoleId='"+sRoleId+"'";
		return super.selectOneColumnBySql(sql);
	}

}
