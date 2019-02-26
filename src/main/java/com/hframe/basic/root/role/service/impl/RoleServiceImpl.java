package com.hframe.basic.root.role.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hframe.basic.base.BaseServiceImpl;
import com.hframe.basic.root.RootUtils;
import com.hframe.basic.root.menu.entity.MenuDTO;
import com.hframe.basic.root.role.dao.PermissionDao;
import com.hframe.basic.root.role.dao.RoleDao;
import com.hframe.basic.root.role.entity.PermissionDTO;
import com.hframe.basic.root.role.entity.RoleDTO;
import com.hframe.basic.root.role.service.RoleService;
import com.hframe.basic.util.ObjectUtils;
import com.hframe.basic.util.StringUtils;

import tk.mybatis.mapper.entity.Example;

@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleDTO> implements RoleService{

	@Autowired
	private RoleDao dao;
	@Autowired
	private PermissionDao permissionDao;
	
	@PostConstruct
	public void init(){
		super.dao = dao;
	}

	/* (non-Javadoc)
	 * @see com.hframe.basic.base.BaseServiceImpl#findAll()
	 */
	@Override
	public List<RoleDTO> findAll() {
		
		Example example = new Example(RoleDTO.class);
		example.setOrderByClause(" iRoleLev desc ");
		return dao.findList(example);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void changeRolePermissions(String sRoleId, List<String> menuIds) {
		
		if(StringUtils.isEmpty(sRoleId)){
			throw new RuntimeException("请选择指定角色");
		}
		
		permissionDao.deleteRolePermi(sRoleId);
		
		if(ObjectUtils.isNotNullList(menuIds)){
			List<MenuDTO> menus = RootUtils.MenuService.getMenusByIds(menuIds);
			menus.forEach((menu) -> {
				PermissionDTO permissionDTO = new PermissionDTO(sRoleId,menu.getsId(),menu.getsRoleSign());
				permissionDao.save(permissionDTO);
			});
		}
	}
	
	
}
