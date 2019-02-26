package com.hframe.basic.root.role.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.hframe.basic.common.JsonResult;
import com.hframe.basic.common.PageResult;
import com.hframe.basic.common.Results;
import com.hframe.basic.common.SysConstants.MenuTypes;
import com.hframe.basic.exception.PageException;
import com.hframe.basic.root.RootUtils;
import com.hframe.basic.root.role.entity.PermissionDTO;
import com.hframe.basic.root.role.entity.RoleDTO;
import com.hframe.basic.root.role.service.PermissionService;
import com.hframe.basic.root.role.service.RoleService;
import com.hframe.basic.root.role.service.UserRoleService;
import com.hframe.basic.util.StringUtils;
import com.hframe.basic.util.Validation;

import tk.mybatis.mapper.entity.Example;


/**
 * 用户角色Controller
 * @author Horgn黄小锤
 * @date 2019年2月4日 下午12:45:57
 * @version V1.0
 */
@Controller
@RequestMapping("/r/r")
public class RoleController {
	
	
	@Autowired
	private RoleService service;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private PermissionService permissionService;
	
	/**页面路径前缀*/
	private final String path = "pages/root/role/";
	
	
	/**获取主页页面*/
	@RequestMapping({"/","init"})
	@RequiresPermissions(value={"admin","sys:conf:role"},logical=Logical.OR)
	public String getInitPage(Model model, PageResult page){
		if(null == page){
			throw new PageException("分页参数不能为空");
		}
		//TODO 其它操作
		Example example = new Example(RoleDTO.class);
		example.setOrderByClause(" iRoleLev desc ");
		model.addAttribute("page", service.findByPage(page, example));
		model.addAttribute("mainMenus", RootUtils.MenuService.getMainMenus());
		model.addAttribute("groupMenus", RootUtils.MenuService.getMenusByType(MenuTypes.Group_Menu));
		model.addAttribute("pageMenus", RootUtils.MenuService.getMenusByType(MenuTypes.Page_Menu));
		model.addAttribute("elementMenus", RootUtils.MenuService.getMenusByType(MenuTypes.Element_Menu));
		return path + "index";
	}
	
	
	/**获取添加页面*/
	@GetMapping("add")
	@RequiresPermissions(value={"admin","sys:role:add"},logical=Logical.OR)
	public String getAddPage(Model model){
		//TODO 其它操作
		return path + "add";
	}
	
	
	/**获取更新页面*/
	@GetMapping("edit")
	@RequiresPermissions(value={"admin","sys:role:edit"},logical=Logical.OR)
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
		//TODO 其它操作
		model.addAttribute("entity", service.findById(sId));
		return path + "detail";
	}
	
	
	/**根据主键是否为空进行添加、更新操作*/
	@PostMapping("save")
	@ResponseBody
	@RequiresPermissions(value={"admin","sys:role:add","sys:role:edit"},logical=Logical.OR)
	public JsonResult doSave(RoleDTO entity){
		if(null == entity){
			return Results.Error("参数实体不能为空");
		}
		if(null == entity.getiRoleLev()){
			entity.setiRoleLev(1);
		}else{
			if(entity.getiRoleLev() <= 0){
				entity.setiRoleLev(1);
			}
			if(entity.getiRoleLev() > 99){
				entity.setiRoleLev(99);
			}
		}
		
		if(StringUtils.isEmpty(entity.getsId())){
			Validation.notNull(entity, "sRoleName", "sRoleSign" );//数据校验，自行添加需要校验的属性名
			service.save(entity);
			return Results.OK();
		}
		
		Validation.notNull(entity, "sId", "iVersion", "sRoleName", "sRoleSign");//数据校验，自行添加需要校验的属性名
		if("admin".equals(entity.getsId())){
			return Results.Error("超级管理员角色，不可编辑");
		}
		service.updateSelective(entity);
		
		return Results.OK();
	}
	
	
	/**根据主键进行删除操作*/
	@PostMapping("del")
	@ResponseBody
	@RequiresPermissions(value={"admin","sys:role:delete"},logical=Logical.OR)
	public JsonResult doDelete(String sId){
		if(StringUtils.isEmpty(sId)){
			return Results.Error("参数不能为空");
		}
		//超级管理员不可删除
		if("admin".equals(sId)){
			return Results.Error("超级管理员角色，不可删除");
		}
		//如果该角色下存在用户，则不可删除
		int count = userRoleService.getRoleUserCount(sId);
		if(count > 0){
			return Results.Error("该角色存在用户，不可删除");
		}
		service.delete(sId);
		return Results.OK();
	}
	
	
	/**保存角色权限*/
	@PostMapping("permi")
	@ResponseBody
	@RequiresPermissions(value={"admin","sys:role:permi"},logical=Logical.OR)
	public JsonResult doSaveRolePermission(String sRoleId, HttpServletRequest request){
		if(StringUtils.isEmpty(sRoleId)){
			return Results.Error("请选择指定角色");
		}
		String menuIdsStr = request.getParameter("menuIds");
		List<String> menuIds = new ArrayList<>();
		if(StringUtils.isNotEmpty(menuIdsStr)){
			menuIds = JSONArray.parseArray(menuIdsStr, String.class);
		}
		service.changeRolePermissions(sRoleId, menuIds);
		return Results.OK();
	}
	
	
	/**根据角色id获取该角色所有权限*/
	@GetMapping("rpermi")
	@ResponseBody
	@RequiresPermissions(value={"admin","sys:role:permi"},logical=Logical.OR)
	public JsonResult getRolePermis(String sRoleId){
		
		if(StringUtils.isEmpty(sRoleId)){
			return Results.Error("请选择指定角色");
		}
		
		RoleDTO entity = service.findById(sRoleId);
		if(null == entity){
			return Results.Error("角色不存在");
		}
		List<PermissionDTO> permissions = permissionService.getPermissions(sRoleId);
		
		return Results.OK("permissions",permissions,"entity",entity);
	}
	
	
}
