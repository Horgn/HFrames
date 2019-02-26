package com.hframe.basic.base;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hframe.basic.common.JsonResult;
import com.hframe.basic.common.PageResult;
import com.hframe.basic.common.Results;
import com.hframe.basic.exception.PageException;
import com.hframe.basic.util.StringUtils;
import com.hframe.basic.util.Validation;

/**
 * 系统基类Controller
 * @author Horgn黄小锤
 * @date 2019年1月30日 下午12:53:41
 * @version V1.0
 */
@Controller
@RequestMapping("/base")
public class BaseController<T extends BaseDTO> {
	
//	@Autowired
	private BaseService<T> service;

	/**页面路径前缀*/
	private final String path = "pages/";
	
	
	/**获取主页页面*/
	@RequestMapping({"/","init"})
	@RequiresPermissions(value={"admin","admin"},logical=Logical.OR)
	public String getInitPage(Model model, PageResult page){
		
		if(null == page){
			page = new PageResult();
		}
		//TODO 其它查询操作
		model.addAttribute("page", service.findByPage(page));
		return path + "index";
	}
	
	
	/**获取添加页面*/
	@GetMapping("add")
	@RequiresPermissions(value={"admin","admin"},logical=Logical.OR)
	public String getAddPage(Model model){
		
		//TODO 其它操作
		return path + "add";
	}
	
	
	/**获取更新页面*/
	@GetMapping("edit")
	@RequiresPermissions(value={"admin","admin"},logical=Logical.OR)
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
	@RequiresPermissions(value={"admin","admin"},logical=Logical.OR)
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
	@RequiresPermissions(value={"admin","admin","admin"},logical=Logical.OR)
	public JsonResult doSave(T entity){
		
		if(null == entity){
			return Results.Error("参数不能为空");
		}
		
		//添加操作
		if(StringUtils.isEmpty(entity.getsId())){
			Validation.notNull(entity);//数据校验，自行添加需要校验的属性名
			//TODO 其它添加操作
			service.save(entity);
			return Results.OK();
		}
		
		//更新操作
		Validation.notNull(entity, "sId", "iVersion");//数据校验，自行添加需要校验的属性名
		//TODO 其它更新操作
		service.updateSelective(entity);
		
		return Results.OK();
	}
	
	
	/**根据主键进行删除操作*/
	@PostMapping("del")
	@ResponseBody
	@RequiresPermissions(value={"admin","admin"},logical=Logical.OR)
	public JsonResult doDelete(String sId){
		
		if(StringUtils.isEmpty(sId)){
			return Results.Error("参数不能为空");
		}
		//TODO 其它删除校验
		service.delete(sId);
		return Results.OK();
	}
	
}
