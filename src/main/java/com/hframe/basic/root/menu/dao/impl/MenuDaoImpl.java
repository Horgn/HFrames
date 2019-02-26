package com.hframe.basic.root.menu.dao.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hframe.basic.base.BaseDaoImpl;
import com.hframe.basic.common.SysConstants;
import com.hframe.basic.root.RootUtils;
import com.hframe.basic.root.menu.dao.MenuDao;
import com.hframe.basic.root.menu.entity.MenuDTO;
import com.hframe.basic.root.menu.mapper.MenuMapper;
import com.hframe.basic.util.StringUtils;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Repository
public class MenuDaoImpl extends BaseDaoImpl<MenuDTO> implements MenuDao{
	
	@Autowired
	private MenuMapper mapper;
	
	@PostConstruct
	public void init(){
		super.mapper = mapper;
	}

	/* (non-Javadoc)
	 * @see com.hframe.basic.base.BaseDaoImpl#checkExist(com.hframe.basic.base.BaseEntity)
	 */
	@Override
	public boolean checkExist(MenuDTO entity) {
		
		if (null == entity) {
			throw new RuntimeException("[SDE001] 实体参数不能为空");
		}
		
		//判断菜单权限标识是否已存在
		Example example = new Example(MenuDTO.class);
		Criteria criteria = example.createCriteria();
		criteria.orEqualTo("sRoleSign", entity.getsRoleSign());
		
		if (StringUtils.isNotEmpty(entity.getsId())) {//如果是更新操作，则将跳过与自身的对比
			Example.Criteria criteria2 = example.createCriteria();
			criteria2.andNotEqualTo("sId", entity.getsId());
			example.and(criteria2);
		}
		
		int i = super.findCount(example);
		if(i > 0){
			throw new RuntimeException("[SDE101] 菜单权限标识已存在");
		}
		
		example = new Example(MenuDTO.class);
		criteria = example.createCriteria();
		criteria.andEqualTo("sMenuName", entity.getsMenuName());
		criteria.andEqualTo("iMenuType", entity.getiMenuType());
		
		if(StringUtils.isNotEmpty(entity.getsParentId())){
			criteria.andEqualTo("sParentId", entity.getsParentId());
		}else{
			criteria.andEqualTo("iMenuType", SysConstants.MenuTypes.Main_Menu);
		}
		
		if (StringUtils.isNotEmpty(entity.getsId())) {//如果是更新操作，则将跳过与自身的对比
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

	@Override
	public List<MenuDTO> getMenuByLoginUserPermission(String sParentId, Integer iMenuType) {
		
		String userId = RootUtils.getLoginUserId();
		
		String sql = null;
		if(StringUtils.isEmpty(sParentId)){
			sql = "SELECT DISTINCT m.* FROM hf_menu m LEFT JOIN hf_permission p ON m.sRoleSign = p.sPremission "
					+ "LEFT JOIN hf_user_role ru ON p.sRoleId = ru.sRoleId WHERE ru.iState = 1 AND p.iState = 1 AND m.iState = 1 "
					+ "AND m.iMenuType = "+iMenuType+" AND ru.sUserId = '"+userId+"'  ORDER BY m.iOrder DESC ";
		}else{
			sql = "SELECT DISTINCT m.* FROM hf_menu m LEFT JOIN hf_permission p ON m.sRoleSign = p.sPremission "
					+ "LEFT JOIN hf_user_role ru ON p.sRoleId = ru.sRoleId WHERE ru.iState = 1 AND p.iState = 1 AND m.iState = 1 "
					+ "AND m.iMenuType = "+iMenuType+" AND ru.sUserId = '"+userId+"' AND m.sParentId='"+sParentId+"'  ORDER BY m.iOrder DESC ";
		}
		
		return super.selectListBySql(sql);
	}

	
}
