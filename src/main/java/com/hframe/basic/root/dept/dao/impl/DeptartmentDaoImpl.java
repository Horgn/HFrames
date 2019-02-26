package com.hframe.basic.root.dept.dao.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hframe.basic.base.BaseDaoImpl;
import com.hframe.basic.common.SysConstants;
import com.hframe.basic.root.dept.dao.DeptartmentDao;
import com.hframe.basic.root.dept.entity.DeptartmentDTO;
import com.hframe.basic.root.dept.mapper.DeptartmentMapper;
import com.hframe.basic.util.StringUtils;

@Repository
public class DeptartmentDaoImpl extends BaseDaoImpl<DeptartmentDTO> implements DeptartmentDao {

	@Autowired
	private DeptartmentMapper mapper;

	@PostConstruct
	public void init() {
		super.mapper = mapper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hframe.basic.base.BaseDaoImpl#checkExist(com.hframe.basic.base.
	 * BaseEntity)
	 */
	@Override
	public boolean checkExist(DeptartmentDTO entity) {
		if (null == entity) {
			throw new RuntimeException("实体参数不能为空");
		}
		tk.mybatis.mapper.entity.Example example = new tk.mybatis.mapper.entity.Example(entity.getClass());
		tk.mybatis.mapper.entity.Example.Criteria criteria = example.createCriteria();
		
		criteria.andEqualTo("sDeptRole",entity.getsDeptRole());
		criteria.orEqualTo("sDeptSign", entity.getsDeptSign());
		
		if (com.hframe.basic.util.StringUtils.isNotEmpty(entity.getsId())) {// 如果是更新操作，则将跳过与自身的对比
			tk.mybatis.mapper.entity.Example.Criteria criteria2 = example.createCriteria();
			criteria2.andNotEqualTo("sId", entity.getsId());
			example.and(criteria2);
		}
		int i = 0;
		try {
			i = findCount(example);
		} catch (Exception e1) {
			throw new RuntimeException("数据重复性校验失败");
		}
		if(i > 0 ){
			throw new RuntimeException("部门编号或权限标识已存在");
		}
		
		example = new tk.mybatis.mapper.entity.Example(entity.getClass());
		criteria = example.createCriteria();
		
		criteria.andEqualTo("sDeptName", entity.getsDeptName());
		
		tk.mybatis.mapper.entity.Example.Criteria criteria3 = example.createCriteria();
		if(SysConstants.Dept_Super_Lev == entity.getiDeptLev() && StringUtils.isEmpty(entity.getsParentId())){
			criteria3.andEqualTo("iDeptLev", SysConstants.Dept_Super_Lev);
		}else{
			criteria3.andEqualTo("sParentId", entity.getsParentId());
		}
		example.and(criteria3);
		
		if (com.hframe.basic.util.StringUtils.isNotEmpty(entity.getsId())) {// 如果是更新操作，则将跳过与自身的对比
			tk.mybatis.mapper.entity.Example.Criteria criteria2 = example.createCriteria();
			criteria2.andNotEqualTo("sId", entity.getsId());
			example.and(criteria2);
		}
		int count = 0;
		try {
			count = super.findCount(example);
		} catch (Exception e) {
			throw new RuntimeException("数据重复性校验失败");
		}
		if (count > 0) {
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.hframe.basic.base.BaseDaoImpl#save(com.hframe.basic.base.BaseDTO)
	 */
	@Override
	public String save(DeptartmentDTO entity) {
		
		String sId = StringUtils.generateUUID(8);
		entity.setsId(sId);
		if(SysConstants.Dict_Super_Lev == entity.getiDeptLev()){
			entity.setsDeptRoot(sId + ",");
		}else{
			entity.setsDeptRoot(entity.getParentDept().getsDeptRoot() + sId + ",");
		}
		
		return super.save(entity);
	}

	@Override
	public List<Object> getAllSonDeptIds(String sId) {
		
		if(StringUtils.isEmpty(sId)){
			throw new RuntimeException("参数不能为空");
		}
		
		String sql = "select sId from " + getTableName() + " where sDeptRoot like '%" + sId + "%' and iState=1 ";
		return super.selectOneColumnBySql(sql);
	}
	
	

}
