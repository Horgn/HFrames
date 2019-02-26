package com.hframe.note.note.controller;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hframe.basic.common.JsonResult;
import com.hframe.basic.common.Results;
import com.hframe.basic.exception.PageException;
import com.hframe.basic.root.RootUtils;
import com.hframe.basic.root.dict.entity.DictionaryDTO;
import com.hframe.basic.util.FileUtils;
import com.hframe.basic.util.SecurityUtils;
import com.hframe.basic.util.SessionUtils;
import com.hframe.basic.util.StringUtils;
import com.hframe.basic.util.Validation;
import com.hframe.note.category.entity.HCategoryDTO;
import com.hframe.note.category.service.HCategoryService;
import com.hframe.note.note.entity.HNoteDTO;
import com.hframe.note.note.service.HNoteService;

/**
 * 小锤笔记--笔记Controller
 * @author Horgn黄小锤
 * @date 2019年2月9日 下午8:12:48
 * @version V1.0
 */
@Controller
@RequestMapping("/hn/nt/")
public class HNoteController {
	
	@Autowired
	private HNoteService service;
	@Autowired
	private HCategoryService cgService;
	
	/**页面路径前缀*/
	private final String path = "pages/hnote/";
	
	
	/**获取主页页面*/
	@RequestMapping({"/","init"})
	@RequiresPermissions(value={"admin","note:my:index"},logical=Logical.OR)
	public String getInitPage(Model model){
		
		model.addAttribute("pCategorys", cgService.getHCotegorysByUser(null));
		model.addAttribute("sCategorys", cgService.getSonHCotegorysByUser(null));
		model.addAttribute("sNotes", service.getNoteNameByUser(null));
		return path + "index";
	}
	
	
	/**获取添加页面*/
	@GetMapping("add")
	@RequiresPermissions(value={"admin","note:nt:add"},logical=Logical.OR)
	public String getAddPage(Model model, String sCategoryId){
		
		if(StringUtils.isEmpty(sCategoryId)){
			throw new PageException("请选择指定笔记分类");
		}
		model.addAttribute("category", cgService.findById(sCategoryId));
		model.addAttribute("showTypes", RootUtils.getDictsByPValues("HorgnNote.showType", 2));
		return path + "add_nt";
	}
	
	
	/**获取更新页面*/
	@GetMapping("edit")
	@RequiresPermissions(value={"admin","note:nt:edit"},logical=Logical.OR)
	public String getEditPage(Model model, String sId){
		
		if(StringUtils.isEmpty(sId)){
			throw new PageException("参数不能为空");
		}
		
		HNoteDTO note = service.findById(sId);
		HCategoryDTO category = cgService.findById(note.getsCategoryId());
		model.addAttribute("pCategorys", cgService.getHCotegorysByUser(null));
		model.addAttribute("sCategorys", cgService.getSonHCotegorysByUser(null));
		model.addAttribute("showTypes", RootUtils.getDictsByPValues("HorgnNote.showType", 2));
		model.addAttribute("entity", note);
		model.addAttribute("category", category);
		
		return path + "edit_nt";
	}
	
	
	/**获取详细页面*/
	@GetMapping("detail")
	@RequiresPermissions(value={"admin","note:nt:detail"},logical=Logical.OR)
	public String getDetailPage(Model model, String sId){
		
		if(StringUtils.isEmpty(sId)){
			throw new PageException("参数不能为空");
		}
		
		HNoteDTO note = service.findById(sId);
		HCategoryDTO category = cgService.findById(note.getsCategoryId());
		model.addAttribute("showTypes", RootUtils.getDictsByPValues("HorgnNote.showType", 2));
		model.addAttribute("note", note);
		model.addAttribute("category", category);
		
		return path + "detail_nt";
	}
	
	
	/**根据主键是否为空进行添加、更新操作*/
	@PostMapping("save")
	@ResponseBody
	@RequiresPermissions(value={"admin","note:nt:add","note:nt:edit"},logical=Logical.OR)
	public JsonResult doSave(HNoteDTO entity){
		
		if(null == entity){
			return Results.Error("参数实体不能为空");
		}
		
		if(StringUtils.isEmpty(entity.getsId())){
			Validation.notNull(entity, "sNoteTitle","sNoteContent","iShowType","sCategoryId");//数据校验，自行添加需要校验的属性名
			//TODO 添加操作
			service.save(entity);
			return Results.OK("saveEntity",entity);
		}
		
		Validation.notNull(entity, "sId", "iVersion");//数据校验，自行添加需要校验的属性名
		//TODO 更新操作
		service.updateSelective(entity);
		
		return Results.OK("editEntity",entity);
	}
	
	
	/**根据主键进行删除操作*/
	@PostMapping("del")
	@ResponseBody
	@RequiresPermissions(value={"admin","note:nt:delete"},logical=Logical.OR)
	public JsonResult doDelete(String sId){
		
		if(StringUtils.isEmpty(sId)){
			return Results.Error("参数不能为空");
		}
		//TODO 其它删除校验
		service.delete(sId);
		return Results.OK("entityId",sId,"isDelete",true);
	}
	
	
	/**
	 * 图片上传接口
	 * @author Horgn黄小锤
	 * @date 2019年1月17日 上午9:11:10
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/uimg")
	@ResponseBody
	public Map<String, Object> doUploadImg(HttpServletRequest request) throws IOException {
		
		Map<String, Object> result = new HashMap<String, Object>(); //kingeditor 编辑器图片上传返回数据
		DictionaryDTO dictionary = RootUtils.DictionaryService.getDictionaryValue("HorgnNote.NoteConfig", 2, 9);
		String pathName = dictionary.getsDictValue();//小锤笔记图片保存文件夹名称，例：hnote/     （注意：需要带斜杠）
		
		String paramName = "imgFile"; //图片保存在请求中的参数名
		String imgName = StringUtils.generateUUID(8); //随机生成图片名称
		String imgType = ".jpg"; //图片保存类型后缀
		String uploadPath = RootUtils.getUploadPath() + pathName; //图片保存路径
		
		try {
			FileUtils.uploadFile(request, paramName, uploadPath, imgName, imgType);
		} catch (Exception e) {
			result.put("error", 1);
			result.put("message", e.getMessage());
			return result;
		}
		
		result.put("error", 0);
		result.put("url", RootUtils.getResponseUploadPath() + pathName + imgName + imgType);
		return result;
	}
	
	
	/**
	 * 获取笔记分享链接
	 * @author Horgn黄小锤
	 * @date 2019年2月22日 下午3:11:23
	 * @param sNoteId
	 * @return
	 */
	@GetMapping("shepath/{sNoteId}")
	@ResponseBody
	public JsonResult getSharePath(@PathVariable("sNoteId") String sNoteId){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		sNoteId = sNoteId + sdf.format(new Date());
		
		HttpServletRequest request = SessionUtils.getRequest();
		StringBuffer url = request.getRequestURL();
		String path = url.substring(0, url.lastIndexOf("/")-13) + "note/" + SecurityUtils.Crypto.encode(sNoteId);
		
		return Results.OK("path", path);
	}
	
	
	
}
