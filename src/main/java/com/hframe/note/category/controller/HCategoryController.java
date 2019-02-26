package com.hframe.note.category.controller;

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
import com.hframe.basic.exception.PageException;
import com.hframe.basic.util.ObjectUtils;
import com.hframe.basic.util.StringUtils;
import com.hframe.basic.util.Validation;
import com.hframe.note.HNoteUtils;
import com.hframe.note.category.entity.HCategoryDTO;
import com.hframe.note.category.service.HCategoryService;
import com.hframe.note.note.entity.HNoteDTO;

/**
 * 小锤笔记--笔记菜单Controller
 * @author Horgn黄小锤
 * @date 2019年2月9日 下午8:10:47
 * @version V1.0
 */
@Controller
@RequestMapping("/hn/cg/")
public class HCategoryController {
	
	@Autowired
	private HCategoryService service;
	
	/**页面路径前缀*/
	private final String path = "pages/hnote/";
	
	
	/**获取添加页面*/
	@GetMapping("add")
	@RequiresPermissions(value={"admin","note:cg:add"},logical=Logical.OR)
	public String getAddPage(Model model, String sParentId){
		
		model.addAttribute("categorys", service.getHCotegorysByUser(null));
		return path + "add_cg";
	}
	
	
	/**获取更新页面*/
	@GetMapping("edit")
	@RequiresPermissions(value={"admin","note:cg:edit"},logical=Logical.OR)
	public String getEditPage(Model model, String sId){
		
		if(StringUtils.isEmpty(sId)){
			throw new PageException("参数不能为空");
		}
		
		model.addAttribute("categorys", service.getHCotegorysByUser(null));
		model.addAttribute("entity", service.findById(sId));
		return path + "edit_cg";
	}
	
	
	/**根据主键是否为空进行添加、更新操作*/
	@PostMapping("save")
	@ResponseBody
	@RequiresPermissions(value={"admin","note:cg:add","note:cg:edit"},logical=Logical.OR)
	public JsonResult doSave(HCategoryDTO entity){
		
		if(null == entity){
			return Results.Error("参数实体不能为空");
		}
		
		if(StringUtils.isEmpty(entity.getsParentId())){
			entity.setiCategoryLev(0);
		}else{
			entity.setiCategoryLev(1);
		}
		
		if(StringUtils.isEmpty(entity.getsId())){
			Validation.notNull(entity, "sCategoryName");//数据校验，自行添加需要校验的属性名
			//TODO 添加操作
			service.save(entity);
			return Results.OK("saveEntity",entity);
		}
		
		Validation.notNull(entity, "sId", "iVersion", "iCategoryLev", "sCategoryName");//数据校验，自行添加需要校验的属性名
		//TODO 更新操作
		service.updateSelective(entity);
		
		return Results.OK("editEntity",entity);
	}
	
	
	/**根据主键进行删除操作*/
	@PostMapping("del")
	@ResponseBody
	@RequiresPermissions(value={"admin","note:cg:delete"},logical=Logical.OR)
	public JsonResult doDelete(String sId){
		
		if(StringUtils.isEmpty(sId)){
			return Results.Error("参数不能为空");
		}
		
		List<HCategoryDTO> sonCotegorys = service.getSonCotegorys(sId);
		if(ObjectUtils.isNotNullList(sonCotegorys)){
			return Results.Error("该分类存在子分类，请先删除子分类");
		}
		
		List<HNoteDTO> noteByCategory = HNoteUtils.NoteService.getNoteNameByCategory(sId);
		if(ObjectUtils.isNotNullList(noteByCategory)){
			return Results.Error("该分类存在笔记，请先删除笔记");
		}
		
		service.delete(sId);
		return Results.OK("entityId",sId,"isDelete",true);
	}

}

