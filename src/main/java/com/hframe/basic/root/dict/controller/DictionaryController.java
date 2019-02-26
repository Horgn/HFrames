package com.hframe.basic.root.dict.controller;

import java.util.List;
import java.util.Map;

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
import com.hframe.basic.common.SysConstants;
import com.hframe.basic.exception.PageException;
import com.hframe.basic.root.dict.entity.DictionaryDTO;
import com.hframe.basic.root.dict.service.DictionaryService;
import com.hframe.basic.util.StringUtils;
import com.hframe.basic.util.Validation;


/**
 * 系统-数据字典Controller
 * @author Horgn黄小锤
 * @date 2019年2月5日 上午11:57:05
 * @version V1.0
 */
@Controller
@RequestMapping("/r/d")
public class DictionaryController {
	
	
	@Autowired
	private DictionaryService service;
	
	/**页面路径前缀*/
	private final String path = "pages/root/dict/";
	
	
	/**获取主页页面*/
	@RequestMapping({"/","init"})
	@RequiresPermissions(value={"admin","sys:conf:dict"},logical=Logical.OR)
	public String getInitPage(Model model){
		
		model.addAttribute("superDicts", service.getDictionarysByLev(SysConstants.Dict_Super_Lev));
		model.addAttribute("sonDicts1", service.getDictionarysByLev(SysConstants.Dict_Super_Lev + 1));
		model.addAttribute("sonDicts2", service.getDictionarysByLev(SysConstants.Dict_Super_Lev + 2));
		model.addAttribute("sonDicts3", service.getDictionarysByLev(SysConstants.Dict_Super_Lev + 3));
		
		return path + "index";
	}
	
	
	/**获取添加页面*/
	@GetMapping("add")
	@RequiresPermissions(value={"admin","sys:dict:add"},logical=Logical.OR)
	public String getAddPage(Model model, String sParentId){
		
		if(StringUtils.isNotEmpty(sParentId)){
			DictionaryDTO dictionaryDTO = service.findById(sParentId);
			if(null == dictionaryDTO){
				throw new PageException("父字典不存在");
			}
			model.addAttribute("parentDTO", dictionaryDTO);
		}
		
		List<DictionaryDTO> byPValues = service.getDictsByPValues("SystemDictionaries.sysDictType", 2);
		model.addAttribute("byPValues", byPValues);
		return path + "add";
	}
	
	
	/**获取更新页面*/
	@GetMapping("edit")
	@RequiresPermissions(value={"admin","sys:dict:edit"},logical=Logical.OR)
	public String getEditPage(Model model, String sId){
		
		if(StringUtils.isEmpty(sId)){
			throw new PageException("参数不能为空");
		}
		
		List<DictionaryDTO> byPValues = service.getDictsByPValues("SystemDictionaries.sysDictType", 2);
		model.addAttribute("byPValues", byPValues);
		model.addAttribute("entity", service.findById(sId));
		return path + "edit";
	}
	
	
	/**获取详细页面*/
	@GetMapping("detail")
	public String getDetailPage(Model model, String sId){
		
		if(StringUtils.isEmpty(sId)){
			throw new PageException("参数不能为空");
		}
		
		DictionaryDTO dictionaryDTO = service.findById(sId);
		if(null == dictionaryDTO){
			throw new PageException("数据字典不存在");
		}
		
		List<DictionaryDTO> sonDataDicts = service.getSonDataDicts(dictionaryDTO.getsId(), dictionaryDTO.getiDictLevel() + 1);
		List<DictionaryDTO> byPValues = service.getDictsByPValues("SystemDictionaries.sysDictType", 2);
		model.addAttribute("entity", dictionaryDTO);
		model.addAttribute("sonDataDicts", sonDataDicts);
		model.addAttribute("byPValues", byPValues);
		
		return path + "detail";
	}
	
	
	/**根据主键是否为空进行添加、更新操作*/
	@PostMapping("save")
	@ResponseBody
	@RequiresPermissions(value={"admin","sys:dict:add","sys:dict:edit"},logical=Logical.OR)
	public JsonResult doSave(DictionaryDTO entity){
		
		if(null == entity){
			return Results.Error("参数实体不能为空");
		}
		
		if(StringUtils.isEmpty(entity.getsId())){
			Validation.notNull(entity, "sDictName","sDictValue","iDictType");//数据校验，自行添加需要校验的属性名
			//TODO 添加操作
			service.save(entity);
			return Results.OK("saveEntity", entity, "isSave", true);
		}
		
		Validation.notNull(entity, "sId", "iVersion","sDictValue","iDictType","iDictType");//数据校验，自行添加需要校验的属性名
		//TODO 更新操作
		service.updateSelective(entity);
		
		return Results.OK();
	}
	
	
	/**根据主键进行删除操作*/
	@PostMapping("del")
	@ResponseBody
	@RequiresPermissions(value={"admin","sys:dict:delete"},logical=Logical.OR)
	public JsonResult doDelete(String sId){
		
		if(StringUtils.isEmpty(sId)){
			return Results.Error("参数不能为空");
		}
		service.delete(sId);
		return Results.OK("entityId",sId,"isDelete",true);
	}
	
	
	/**获取ztree树形列表*/
	@GetMapping("ztree")
	@ResponseBody
	public JsonResult getZtreeDicts(String sParentId){
		Map<String, List<DictionaryDTO>> map = service.getTreeDeptionary(sParentId);
		return Results.OK("dictionaries", map);
	}
	
	
	/**根据指定字典值及字典等级获取直接子字典列表*/
	@RequestMapping("dict")
	@ResponseBody
	public JsonResult getDictList(String sDictValue, Integer iDictLevel){
		
		if(StringUtils.isEmpty(sDictValue) || null == iDictLevel){
			return Results.Error("参数不能为空");
		}
		List<DictionaryDTO> byPValue = service.getDictsByPValues(sDictValue,iDictLevel);
		
		return Results.OK("dictionaries", byPValue);
	}
	

}
