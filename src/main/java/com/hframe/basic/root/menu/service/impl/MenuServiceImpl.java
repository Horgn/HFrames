package com.hframe.basic.root.menu.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hframe.basic.base.BaseServiceImpl;
import com.hframe.basic.common.SysConstants;
import com.hframe.basic.common.SysConstants.MenuTypes;
import com.hframe.basic.root.menu.dao.MenuDao;
import com.hframe.basic.root.menu.entity.MenuDTO;
import com.hframe.basic.root.menu.service.MenuService;
import com.hframe.basic.util.ObjectUtils;
import com.hframe.basic.util.StringUtils;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class MenuServiceImpl extends BaseServiceImpl<MenuDTO> implements MenuService{
	
	@Autowired
	private MenuDao dao;
	
	@PostConstruct
	public void init(){
		super.dao = dao;
	}
	

	@Override
	public List<MenuDTO> getMainMenus(){
		
		Example example = new Example(MenuDTO.class);
		Criteria criteria = example.createCriteria();
		
		criteria.andEqualTo("iMenuType", MenuTypes.Main_Menu);
		example.setOrderByClause(" iOrder desc ");
		
		return dao.findList(example);
	}
	
	
	@Override
	public List<MenuDTO> getGroupMenus(String sParentId) {
		
		if(StringUtils.isEmpty(sParentId)){
			throw new RuntimeException("[SSZ-MENU001] 参数不能为空");
		}
		
		return getMenusByType(sParentId,MenuTypes.Group_Menu);
	}
	

	@Override
	public List<MenuDTO> getPageMenus(String sParentId) {
		
		if(StringUtils.isEmpty(sParentId)){
			throw new RuntimeException("[SSZ-MENU002] 参数不能为空");
		}
		
		return getMenusByType(sParentId,MenuTypes.Page_Menu);
	}
	

	@Override
	public List<MenuDTO> getElementMenus(String sParentId) {
		
		if(StringUtils.isEmpty(sParentId)){
			throw new RuntimeException("[SSZ-MENU003] 参数不能为空");
		}
		
		return getMenusByType(sParentId,MenuTypes.Element_Menu);
	}
	
	
	/**根据上级id及菜单类型获取菜单列表*/
	private List<MenuDTO> getMenusByType(String sParentId, Integer iMenuType){
		
		Example example = new Example(MenuDTO.class);
		Criteria criteria = example.createCriteria();
		
		criteria.andEqualTo("iMenuType", iMenuType);
		criteria.andEqualTo("sParentId", sParentId);
		example.setOrderByClause(" iOrder desc ");
		
		return dao.findList(example);
	}
	
	
	@Override
	public List<MenuDTO> getMenusByType(Integer iMenuType){
		
		List<MenuDTO> list = new ArrayList<>();
		if(null == iMenuType){
			return list;
		}
		
		Example example = new Example(MenuDTO.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("iMenuType", iMenuType);
		example.setOrderByClause(" iOrder desc ");
		return dao.findList(example);
	}
	

	@Override
	public Map<String, List<MenuDTO>> getZtreeMenu() {
		Map<String, List<MenuDTO>> map = new HashMap<>();
		//1、获取主菜单
		List<MenuDTO> mainMenus = getMainMenus();
		if(ObjectUtils.isNullList(mainMenus)){
			return map;
		}
		map.put("mainMenus", mainMenus);
		
		//2、获取分组菜单
		for(MenuDTO mainDto : mainMenus){
			List<MenuDTO> groupMenus = getGroupMenus(mainDto.getsId());
			if(ObjectUtils.isNotNullList(groupMenus)){
				map.put(mainDto.getsId(), groupMenus);
				
				//3、获取页面菜单
				for(MenuDTO groupDto : groupMenus){
					List<MenuDTO> pageMenus = getPageMenus(groupDto.getsId());
					if(ObjectUtils.isNotNullList(pageMenus)){
						map.put(groupDto.getsId(), pageMenus);
						
						//4、获取元素菜单
						for(MenuDTO pageDto : pageMenus){
							List<MenuDTO> elementMenus = getElementMenus(pageDto.getsId());
							if(ObjectUtils.isNotNullList(elementMenus)){
								map.put(pageDto.getsId(), elementMenus);
							}
						}
					}
				}
			}
		}
		return map;
	}
	

	@Override
	public List<MenuDTO> getSonMenus(MenuDTO menuDTO) {
		
		List<MenuDTO> list = new ArrayList<>();
		if(ObjectUtils.isNull(menuDTO) || SysConstants.MenuTypes.Element_Menu == menuDTO.getiMenuType()){
			return list;
		}
		
		if(SysConstants.MenuTypes.Main_Menu == menuDTO.getiMenuType()){
			return getGroupMenus(menuDTO.getsId());
		}
		
		if(SysConstants.MenuTypes.Group_Menu == menuDTO.getiMenuType()){
			return getPageMenus(menuDTO.getsId());
		}
		
		if(SysConstants.MenuTypes.Page_Menu == menuDTO.getiMenuType()){
			return getElementMenus(menuDTO.getsId());
		}
		return list;
	}
	

	@Override
	public boolean checkSonCount(String sParentId) {
		
		if(StringUtils.isEmpty(sParentId)){
			throw new RuntimeException("参数不能为空");
		}
		
		MenuDTO menuDTO = new MenuDTO();
		menuDTO.setsParentId(sParentId);
		int count = dao.findCount(menuDTO);
		
		if(count > 0){
			return true;
		}
		return false;
	}
	

	/* (non-Javadoc)
	 * @see com.hframe.basic.base.BaseServiceImpl#delete(java.lang.String)
	 */
	@Override
	public int delete(String sId) {
		
		if(checkSonCount(sId)){
			throw new RuntimeException("删除失败：该菜单下存在子菜单");
		}
		
		return super.delete(sId);
	}
	

	/* (non-Javadoc)
	 * @see com.hframe.basic.base.BaseServiceImpl#save(com.hframe.basic.base.BaseDTO)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String save(MenuDTO entity) {
		
		String sId = super.save(entity);
		if(SysConstants.MenuTypes.Main_Menu == entity.getiMenuType()){
			
			MenuDTO group = new MenuDTO();
			group.setsParentId(sId);
			group.setiMenuType(MenuTypes.Group_Menu);
			group.setsMenuName(entity.getsMenuName());
			group.setiOrder(99);
			group.setsRoleSign(entity.getsRoleSign());
			
			super.save(group);
		}
		return sId;
	}


	@Override
	public List<MenuDTO> getMenuByLoginUserPermission(String sParentId, Integer iMenuType) {
		return dao.getMenuByLoginUserPermission(sParentId, iMenuType);
	}


	@Override
	public List<MenuDTO> getMenusByIds(List<String> menuIds) {
		Example example = new Example(MenuDTO.class);
		Criteria criteria = example.createCriteria();
		
		criteria.andIn("sId", menuIds);
		example.setOrderByClause(" iOrder desc ");
		
		return dao.findList(example);
	}
	
	
	
	
	
	

}
