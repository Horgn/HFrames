package com.hframe.basic.root.menu.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hframe.basic.common.JsonResult;
import com.hframe.basic.common.Results;
import com.hframe.basic.common.SysConstants.MenuTypes;
import com.hframe.basic.exception.PageException;
import com.hframe.basic.root.dict.entity.DictionaryDTO;
import com.hframe.basic.root.dict.service.DictionaryService;
import com.hframe.basic.root.menu.entity.MenuDTO;
import com.hframe.basic.root.menu.service.MenuService;
import com.hframe.basic.util.StringUtils;
import com.hframe.basic.util.Validation;


/**
 * 系统--菜单Controller
 * @author Horgn黄小锤
 * @date 2019年2月6日 下午6:33:45
 * @version V1.0
 */
@Controller
@RequestMapping("/r/m")
public class MenuController {
	
	
	@Autowired
	private MenuService service;
	@Autowired
	private DictionaryService dictionaryService;
	
	/**页面路径前缀*/
	private final String path = "pages/root/menu/";
	
	
	/**获取主页页面*/
	@RequestMapping({"/","init"})
	@RequiresPermissions(value={"admin","sys:conf:menu"},logical=Logical.OR)
	public String getInitPage(Model model){
		//TODO 其它操作
		model.addAttribute("mainMenus", service.getMenusByType(MenuTypes.Main_Menu));
		model.addAttribute("groupMenus", service.getMenusByType(MenuTypes.Group_Menu));
		model.addAttribute("pageMenus", service.getMenusByType(MenuTypes.Page_Menu));
		model.addAttribute("elementMenus", service.getMenusByType(MenuTypes.Element_Menu));
		
		return path + "index";
	}
	
	
	/**获取添加页面*/
	@GetMapping("add")
	@RequiresPermissions(value={"admin","sys:menu:add"},logical=Logical.OR)
	public String getAddPage(Model model, String sId){
		if(StringUtils.isNotEmpty(sId)){
			MenuDTO parent = service.findById(sId);
			model.addAttribute("parent", parent);
		}
		List<DictionaryDTO> pValues = dictionaryService.getDictsByPValues("SystemDictionaries.sysMenuType", 2);
		model.addAttribute("pValues", pValues);
		return path + "add";
	}
	
	
	/**获取更新页面*/
	@GetMapping("edit")
	@RequiresPermissions(value={"admin","sys:menu:edit"},logical=Logical.OR)
	public String getEditPage(Model model, String sId){
		if(StringUtils.isEmpty(sId)){
			throw new PageException("参数不能为空");
		}
		//TODO 其它操作
		model.addAttribute("entity", service.findById(sId));
		return path + "edit";
	}
	
	
	/**获取详细页面*/
	@GetMapping("detail")
	public String getDetailPage(Model model, String sId){
		if(StringUtils.isEmpty(sId)){
			throw new PageException("参数不能为空");
		}
		List<DictionaryDTO> pValues = dictionaryService.getDictsByPValues("SystemDictionaries.sysMenuType", 2);
		MenuDTO entity = service.findById(sId);
		List<MenuDTO> sonMenus = service.getSonMenus(entity);
		model.addAttribute("entity", entity);
		model.addAttribute("pValues", pValues);
		model.addAttribute("sonMenus", sonMenus);
		return path + "detail";
	}
	
	
	/**根据主键是否为空进行添加、更新操作*/
	@PostMapping("save")
	@ResponseBody
	@RequiresPermissions(value={"admin","sys:menu:add","sys:menu:edit"},logical=Logical.OR)
	public JsonResult doSave(MenuDTO entity){
		if(null == entity){
			return Results.Error("参数实体不能为空");
		}
		
		if(StringUtils.isEmpty(entity.getsId())){
			Validation.notNull(entity, "sMenuName", "sRoleSign", "iMenuType");//数据校验，自行添加需要校验的属性名
			//TODO 添加操作
			service.save(entity);
			return Results.OK("saveEntity", entity, "isSave", true);
		}
		
		Validation.notNull(entity, "sId", "iVersion", "sMenuName", "sRoleSign");//数据校验，自行添加需要校验的属性名
		//TODO 更新操作
		service.updateSelective(entity);
		
		return Results.OK();
	}
	
	
	/**根据主键进行删除操作*/
	@PostMapping("del")
	@ResponseBody
	@RequiresPermissions(value={"admin","sys:menu:delete"},logical=Logical.OR)
	public JsonResult doDelete(String sId){
		
		if(StringUtils.isEmpty(sId)){
			return Results.Error("参数不能为空");
		}
		//TODO 其它删除校验
		service.delete(sId);
		return Results.OK("entityId",sId,"isDelete",true);
	}
	
	
	/**获取ztree菜单列表*/
//	@GetMapping("ztree")
//	@ResponseBody
//	public JsonResult getMenuZtree(){
//		Map<String, List<MenuDTO>> ztreeMenu = service.getZtreeMenu();
//		return Results.OK("ztreeMenu", ztreeMenu);
//	}

}
