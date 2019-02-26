package com.hframe.basic.root.user.dao.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hframe.basic.base.BaseDaoImpl;
import com.hframe.basic.root.user.dao.UserDao;
import com.hframe.basic.root.user.entity.UserDTO;
import com.hframe.basic.root.user.mapper.UserMapper;
import com.hframe.basic.util.ObjectUtils;
import com.hframe.basic.util.StringUtils;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Repository
public class UserDaoImpl extends BaseDaoImpl<UserDTO> implements UserDao{
	
	@Autowired
	private UserMapper mapper;
	
	@PostConstruct
	public void init(){
		super.mapper = mapper;
	}

	/* (non-Javadoc)
	 * @see com.hframe.basic.base.BaseDaoImpl#checkExist(com.hframe.basic.base.BaseEntity)
	 */
	@Override
	public boolean checkExist(UserDTO entity) {
		
		if(null == entity){
			throw new RuntimeException("实体参数不能为空");
		}
		
		Example example = new Example(UserDTO.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("sUsername", entity.getsUsername());
		
		if (StringUtils.isNotEmpty(entity.getsId())) {
			Example.Criteria criteria2 = example.createCriteria();
			criteria2.andNotEqualTo("sId", entity.getsId());
			example.and(criteria2);
		}
		
		int count = 0;
		try {
			count = super.findCount(example);
		} catch (Exception e) {
			doExceptionMsg(e, "数据重复性校验失败");
		}
		
		if(count > 0){
			return true;
		}
		return false;
	}

	@Override
	public UserDTO findByUsername(String sUsername) {
		
		if(StringUtils.isEmpty(sUsername)){
			throw new RuntimeException("获取用户信息失败");
		}
		
		Example example = new Example(UserDTO.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("sUsername", sUsername);
		example.selectProperties("sId", "sUsername", "sPassword", "sNickname", "iVersion");
		
		List<UserDTO> findList = super.findList(example);
		if(findList.size() >= 2){
			throw new RuntimeException("获取用户信息失败：用户重复");
		}
		if(findList.size() == 1){
			return findList.get(0);
		}
		
		return null;
	}

	@Override
	public UserDTO getUserInfo(String sId) {
		
		if(StringUtils.isEmpty(sId)){
			throw new RuntimeException("获取用户信息失败");
		}
		
		Example example = new Example(UserDTO.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("sId", sId);
		example.selectProperties("sId", "sUsername", "sNickname", "iVersion");
		
		List<UserDTO> findList = super.findList(example);
		if(findList.size() == 1){
			return findList.get(0);
		}
		
		return null;
	}

	@Override
	public List<UserDTO> getUsersByIds(List<Object> ids) {
		
		if(ObjectUtils.isNullList(ids)){
			return null;
		}
		
		Example example = new Example(UserDTO.class);
		Criteria criteria = example.createCriteria();
		example.selectProperties("sId", "sUsername", "sNickname");
		criteria.andIn("sId", ids);
		
		return super.findList(example);
	}
	
	

}
