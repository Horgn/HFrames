package com.hframe.basic.root;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hframe.basic.common.JsonResult;
import com.hframe.basic.common.PageResult;
import com.hframe.basic.common.Results;
import com.hframe.basic.common.SysConstants.MenuTypes;
import com.hframe.basic.exception.PageException;
import com.hframe.basic.root.menu.entity.MenuDTO;
import com.hframe.basic.root.user.entity.UserDTO;
import com.hframe.basic.util.SecurityUtils;
import com.hframe.basic.util.StringUtils;
import com.hframe.note.HNoteUtils;
import com.hframe.note.note.entity.HNoteDTO;

/**
 * 系统控制类
 * @author Horgn黄小锤
 * @date 2019年1月19日 上午11:24:04
 * @version V1.0
 */
@Controller
public class IndexController {
	
	private Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	private final String path = "pages/";

	/**
	 * 获取系统主页
	 * @author Horgn黄小锤
	 * @date 2019年1月19日 上午11:38:36
	 * @return
	 */
	@RequestMapping({ "/", "/index" })
	public String getIndexPage(Model model) {
		
		//根据权限获取主菜单
		List<MenuDTO> mainMenus = RootUtils.MenuService.getMenuByLoginUserPermission(null, MenuTypes.Main_Menu);
		model.addAttribute("mainMenus", mainMenus);
		
		return path+"index";
	}

	/**
	 * 主页默认页面路径
	 * @author Horgn黄小锤
	 * @date 2019年1月19日 上午11:38:44
	 * @return
	 */
	@RequestMapping("/default")
	public String getDefaulePage(Model model, PageResult page) {
		
		if(null == page){
			throw new PageException("分页参数不能为空");
		}
		
		model.addAttribute("page", page);
		return path+"default";
	}

	/**
	 * 获取登录页面
	 * @author Horgn黄小锤
	 * @date 2019年1月19日 上午11:38:54
	 * @return
	 */
	@GetMapping("/login")
	public String getLoginPage(Model model) {
		
		model.addAttribute("securecode", RootUtils.getSecureCode());
		return path+"login";
	}
	
	/**
	 * 执行登录操作
	 * @author Horgn黄小锤
	 * @date 2019年1月19日 上午11:39:01
	 * @return
	 */
	@PostMapping("/login")
	@ResponseBody
	public JsonResult doLogin(String username,String password,Boolean rememberMe, String checkcode) {
		
		if(StringUtils.isEmpty(username,password) ){
			return Results.Error("请输入完整的用户名或密码");
		}
		
		// 验证码校验，如果已开启验证码校验，则判断验证码是否正确
		RootUtils.checkVerifyCode(checkcode);
		
		// 添加用户认证信息
		Subject subject = org.apache.shiro.SecurityUtils.getSubject();
		password = SecurityUtils.MD5.getMD5(RootUtils.getSecureCode(password));
		UsernamePasswordToken token = new UsernamePasswordToken(username,password);
		if(null == rememberMe){
			rememberMe = false;
		}
		
		// 进行验证，这里可以捕获异常，然后返回对应信息
		token.setRememberMe(rememberMe);
		try {
			subject.login(token);
		} catch (IncorrectCredentialsException ice) {
			logger.error(ice.getMessage());
			return Results.Error("登录失败：账号或密码错误");
			
		} catch (UnknownAccountException uae) {
			logger.error(uae.getMessage());
			return Results.Error("登录失败：用户不存在");
			
		} catch (LockedAccountException lae) {
			logger.error(lae.getMessage());
			return Results.Error("登录失败：账号已经锁定");
			
		} catch (ExcessiveAttemptsException eae) {
			logger.error(eae.getMessage());
			return Results.Error("登录失败：错误次数太多");
			
		} catch (AuthenticationException ae) {
			logger.error(ae.getMessage());
			return Results.Error("登录失败：账号或密码错误");
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Results.Error("登录失败：登录异常");
		}

		// 将当前登录用户保存到session中
		UserDTO user = RootUtils.UserService.findByUsername(username);
		RootUtils.login(user);

		return Results.OK();
	}

	/**
	 * 获取验证码
	 * @author Horgn黄小锤
	 * @date 2019年1月19日 上午11:39:14
	 */
	@GetMapping("/code")
	@ResponseBody
	public void getCheckCode(){
		RootUtils.getVerifyCode();
	}

	/**
	 * shiro注销登录方法
	 * @author Horgn黄小锤
	 * @date 2019年1月19日 上午11:38:15
	 * @return
	 */
	@GetMapping("/logout")
	@ResponseBody
	public JsonResult doLogout() {
		
		RootUtils.logout();
		return Results.OK();
	}
	
	/**
	 * 查看分享笔记
	 * @author Horgn黄小锤
	 * @date 2019年2月22日 下午2:25:20
	 * @param model
	 * @param sNoteId
	 * @return
	 */
	@RequestMapping("/note/{sNoteId}")
	public String getShareNote(Model model, @PathVariable("sNoteId") String sNoteId){
		
		HNoteDTO note = null;
		try {
			note = HNoteUtils.NoteService.getShareNote(sNoteId);
		} catch (Exception e) {
			throw new PageException("笔记不存在");
		}
		
		model.addAttribute("note", note);
		return  "pages/hnote/share";
	}
	
	
}
