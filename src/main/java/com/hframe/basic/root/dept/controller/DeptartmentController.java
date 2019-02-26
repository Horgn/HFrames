package com.hframe.basic.root.dept.controller;

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
import com.hframe.basic.common.PageResult;
import com.hframe.basic.common.Results;
import com.hframe.basic.common.SysConstants;
import com.hframe.basic.common.SysConstants.DepartmentLevs;
import com.hframe.basic.exception.PageException;
import com.hframe.basic.root.dept.entity.DeptartmentDTO;
import com.hframe.basic.root.dept.service.DeptartmentService;
import com.hframe.basic.util.StringUtils;
import com.hframe.basic.util.Validation;

/**
 * 系统--部门（分组）Controller
 * @author Horgn黄小锤
 * @date 2019年2月9日 下午12:19:35
 * @version V1.0
 */
@Controller
@RequestMapping("/r/p/")
public class DeptartmentController {
	
	@Autowired
	private DeptartmentService service;
	
	/**页面路径前缀*/
	private final String path = "pages/root/dept/";
	
	
	/**获取主页页面*/
	@RequestMapping({"/","init"})
	@RequiresPermissions(value={"admin","sys:conf:dept"},logical=Logical.OR)
	public String getInitPage(Model model, PageResult page){
		
		if(null == page){
			throw new PageException("分页参数不能为空");
		}
		//获取部门列表
		model.addAttribute("superDepts", service.getDepartmentsByLev(DepartmentLevs.Dept_Super));
		model.addAttribute("sonDept1", service.getDepartmentsByLev(DepartmentLevs.Dept_Son_1));
		model.addAttribute("sonDept2", service.getDepartmentsByLev(DepartmentLevs.Dept_Son_2));
		model.addAttribute("sonDept3", service.getDepartmentsByLev(DepartmentLevs.Dept_Son_3));
		model.addAttribute("sonDept4", service.getDepartmentsByLev(DepartmentLevs.Dept_Son_4));
		model.addAttribute("sonDept5", service.getDepartmentsByLev(DepartmentLevs.Dept_Son_5));
		model.addAttribute("page", service.findByPage(page));
		return path + "index";
	}
	
	
	/**获取添加页面*/
	@GetMapping("add")
	@RequiresPermissions(value={"admin","sys:dept:add"},logical=Logical.OR)
	public String getAddPage(Model model, String sParentId){
		
		if(StringUtils.isNotEmpty(sParentId)){
			DeptartmentDTO entity = service.findById(sParentId);
			if(null == entity){
				throw new PageException("获取上级部门失败");
			}
			model.addAttribute("entity",entity );
		}
		return path + "add";
	}
	
	
	/**获取更新页面*/
	@GetMapping("edit")
	@RequiresPermissions(value={"admin","sys:dept:edit"},logical=Logical.OR)
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
	@RequiresPermissions(value={"admin","sys:dept:detail"},logical=Logical.OR)
	public String getDetailPage(Model model, String sId){
		
		if(StringUtils.isEmpty(sId)){
			throw new PageException("参数不能为空");
		}
		//TODO 其它操作
		model.addAttribute("entity", service.findById(sId));
		model.addAttribute("sonDepts", service.getDeptartments(sId));
		return path + "detail";
	}
	
	
	/**根据主键是否为空进行添加、更新操作*/
	@PostMapping("save")
	@ResponseBody
	@RequiresPermissions(value={"admin","sys:dept:add","sys:dept:edit"},logical=Logical.OR)
	public JsonResult doSave(DeptartmentDTO entity){
		
		if(null == entity){
			return Results.Error("参数实体不能为空");
		}
		
		if(null == entity.getiDeptLev() || SysConstants.Dept_Super_Lev > entity.getiDeptLev() || 
				SysConstants.Dept_Min_Lev  < entity.getiDeptLev()){
			return Results.Error("部门等级不正确");
		}
		
		if(StringUtils.isEmpty(entity.getsId())){
			Validation.notNull(entity, "sDeptName" ,"sDeptSign", "iDeptLev" ,"sDeptRole");//数据校验，自行添加需要校验的属性名
			//TODO 添加操作
			service.save(entity);
			return Results.OK("saveEntity",entity);
		}
		
		Validation.notNull(entity, "sId", "iVersion" ,"sDeptName" ,"sDeptRole" ,"iDeptLev" );//数据校验，自行添加需要校验的属性名
		//TODO 更新操作
		service.updateSelective(entity);
		
		return Results.OK("editEntity",entity);
	}
	
	
	/**根据主键进行删除操作*/
	@PostMapping("del")
	@ResponseBody
	@RequiresPermissions(value={"admin","sys:dept:delete"},logical=Logical.OR)
	public JsonResult doDelete(String sId){
		
		if(StringUtils.isEmpty(sId)){
			return Results.Error("参数不能为空");
		}
		
		if(service.hasSonDepts(sId)){
			return Results.Error("该部门存在子部门，不可删除");
		}
		
		service.delete(sId);
		return Results.OK("entityId",sId,"isDelete",true);
	}

}
