package com.hframe.basic.root.user.controller;

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
import com.hframe.basic.exception.PageException;
import com.hframe.basic.root.RootUtils;
import com.hframe.basic.root.role.service.RoleService;
import com.hframe.basic.root.role.service.UserRoleService;
import com.hframe.basic.root.user.entity.UserDTO;
import com.hframe.basic.root.user.service.UserService;
import com.hframe.basic.util.SessionUtils;
import com.hframe.basic.util.StringUtils;
import com.hframe.basic.util.Validation;


/**
 * 系统用户Controller
 * @author Horgn黄小锤
 * @date 2019年1月31日 下午4:09:17
 * @version V1.0
 */
@Controller
@RequestMapping("/r/u")
public class UserController {
	
	
	@Autowired
	private UserService service;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private RoleService roleService;
	
	/**页面路径前缀*/
	private final String path = "pages/root/user/";
	
	
	/**获取主页页面*/
	@RequestMapping({"/","init"})
	@RequiresPermissions(value={"admin","sys:conf:user"},logical=Logical.OR)
	public String getInitPage(Model model, PageResult page){
		
		if(null == page){
			throw new PageException("分页参数不能为空");
		}
		
		model.addAttribute("page", service.findByPage(page));
		return path + "index";
	}
	
	
	/**获取添加页面*/
	@GetMapping("add")
	@RequiresPermissions(value={"admin","sys:user:add"},logical=Logical.OR)
	public String getAddPage(Model model){
		
		model.addAttribute("securecode", RootUtils.getSecureCode());
		return path + "add";
	}
	
	
	/**获取更新页面*/
	@GetMapping("edit")
	@RequiresPermissions(value={"admin","sys:user:edit"},logical=Logical.OR)
	public String getEditPage(Model model, String sId){
		
		if(StringUtils.isEmpty(sId)){
			throw new PageException("参数不能为空");
		}
		//TODO 其它操作
		model.addAttribute("entity", service.getUserInfo(sId));
		return path + "edit";
	}
	
	
	/**获取详细页面*/
	@GetMapping("detail")
	public String getDetailPage(Model model, String sId){
		
		if(StringUtils.isEmpty(sId)){
			throw new PageException("参数不能为空");
		}
		//TODO 其它操作
		model.addAttribute("entity", service.getUserInfo(sId));
		return path + "detail";
	}
	
	
	/**根据主键是否为空进行添加、更新操作*/
	@PostMapping("save")
	@ResponseBody
	@RequiresPermissions(value={"admin","sys:user:add","sys:user:edit"},logical=Logical.OR)
	public JsonResult doSave(UserDTO entity, String password2, String checkcode){
		
		if(null == entity){
			return Results.Error("参数实体不能为空");
		}
		
		if(StringUtils.isEmpty(entity.getsId())){
			//校验验证码
			RootUtils.checkVerifyCode(checkcode);
			
			if(StringUtils.isEmpty(password2)){
				return Results.Error("请输入确认密码");
			}
			
			Validation.notNull(entity, "sUsername","sPassword");//数据校验，自行添加需要校验的属性名
			String pwd = RootUtils.getSecureCode(entity.getsPassword());
			String pwd2 = RootUtils.getSecureCode(password2);
			if(!pwd.equals(pwd2)){
				return Results.Error("再次密码不相同");
			}
			
			entity.setsPassword(pwd);
			service.save(entity);
			return Results.OK();
		}
		
		Validation.notNull(entity, "sId", "iVersion", "sUsername", "sNickname");//数据校验，自行添加需要校验的属性名
		service.updateSelective(entity);
		//如果更新的是当前用户，则刷新session
		if(RootUtils.getLoginUserId().equals(entity.getsId())){
			SessionUtils.setSessionAttribute(RootUtils.LOGIN_USER, service.getUserInfo(entity.getsId()));
		}
		
		return Results.OK();
	}
	
	
	/**根据主键进行删除操作*/
	@PostMapping("del")
	@ResponseBody
	@RequiresPermissions(value={"admin","sys:user:delete"},logical=Logical.OR)
	public JsonResult doDelete(String sId){
		
		if(StringUtils.isEmpty(sId)){
			return Results.Error("参数不能为空");
		}
		if("admin".equals(sId)){
			return Results.Error("超级管理员不可删除");
		}
		
		String loginUserId = RootUtils.getLoginUserId();
		if(loginUserId.equals(sId)){
			return Results.Error("不能删除当前用户");
		}
		service.delete(sId);
		return Results.OK();
	}
	
	
	/**获取修改密页面*/
	@GetMapping("pwd")
	public String getPwdPage(Model model, String sId){
		
		if(StringUtils.isEmpty(sId)){
			throw new PageException("参数不能为空");
		}
		
		UserDTO entity = service.getUserInfo(sId);
		model.addAttribute("securecode", RootUtils.getSecureCode());
		model.addAttribute("entity", entity);
		return path + "pwd";
	}
	
	
	/**执行更新用户密码*/
	@PostMapping("pwd")
	@ResponseBody
	public JsonResult doUpdatePwd(String sId, String sPassword, String npwd, String npwd2, Integer iVersion, String checkcode){
		//校验验证码
		RootUtils.checkVerifyCode(checkcode);
		
		if(StringUtils.isEmpty(sId,sPassword,npwd,npwd2) || null == iVersion){
			return Results.Error("请输入完整信息");
		}
		service.updatePwd(sId, RootUtils.getSecureCode(sPassword),RootUtils.getSecureCode(npwd),RootUtils.getSecureCode(npwd2), iVersion);
		return Results.OK();
	}
	
	
	/**获取指定用户的角色列表*/
	@GetMapping("role")
	@RequiresPermissions(value={"admin","sys:user:role"},logical=Logical.OR)
	public String getUserRoleList(Model model , String sUserId){
		
		if(StringUtils.isEmpty(sUserId)){
			throw new PageException("请选择指定用户");
		}
		model.addAttribute("entity", service.getUserInfo(sUserId));
		model.addAttribute("userRoles", userRoleService.findUserRolesId(sUserId));
		model.addAttribute("allRoles", roleService.findAll());
		
		return path + "role";
	}
	
	
	/**保存用户角色*/
	@PostMapping("role")
	@ResponseBody
	@RequiresPermissions(value={"admin","sys:user:role"},logical=Logical.OR)
	public JsonResult doSaveUserRoles(HttpServletRequest request, String sUserId){
		
		if(StringUtils.isEmpty(sUserId)){
			return Results.Error("请选择指定用户");
		}
		
		String roleIdArrays = request.getParameter("roleIdArrays");
		List<String> roleIds = new ArrayList<>();
		if(StringUtils.isNotEmpty(roleIdArrays)){
			roleIds = JSONArray.parseArray(roleIdArrays, String.class);
		}
		
		service.changeUserRoles(sUserId, roleIds);
		return Results.OK();
	}

}
