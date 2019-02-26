package com.hframe.basic.root;

import java.util.List;
import javax.annotation.PostConstruct;

import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hframe.basic.root.user.entity.UserDTO;
import com.hframe.basic.root.user.service.UserService;
import com.hframe.basic.root.user.service.UserInfoService;
import com.hframe.basic.util.SecurityUtils;
import com.hframe.basic.util.SessionUtils;
import com.hframe.basic.util.StringUtils;
import com.hframe.basic.util.VerifyCodeUtils;
import  com.hframe.basic.root.role.service.RoleService;
import com.hframe.basic.root.role.service.UserRoleService;
import com.hframe.basic.root.dept.service.DeptartmentService;
import com.hframe.basic.root.dept.service.UserDeptService;
import com.hframe.basic.root.dict.entity.DictionaryDTO;
import com.hframe.basic.root.dict.service.DictionaryService;
import com.hframe.basic.root.role.service.PermissionService;
import com.hframe.basic.root.menu.service.MenuService;

/**
 * 系统权限工具类
 * @author Horgn黄小锤
 * @date 2019年1月19日 上午9:15:12
 * @version V1.0
 */
@Component
public class RootUtils {
	
	@Value("${hframe.verify-code}")
	private Boolean sOpenVriCode;
	
	@Value("${hframe.web.root-path}")
	private String sUploadPath;
	
	private static Boolean openVriCode;
	private static String uploadPath;
	
	/**系统用户Service*/
	public static UserService UserService;
	/**系统用户用户信息Service*/
	public static UserInfoService UserInfoService;
	/**系统用户角色Service*/
	public static RoleService RoleService;
	/**系统用户角色列表Service*/
	public static UserRoleService UserRoleService;
	/**系统角色权限Service*/
	public static PermissionService PermissionService;
	/**系统菜单Service*/
	public static MenuService MenuService;
	/**系统数据字典Service*/
	public static DictionaryService DictionaryService;
	/**系统部门Service*/
	public static DeptartmentService DeptartmentService;
	/**系统-用户部门关联表Service*/
	public static UserDeptService UserDeptService;
	
	
	/**当前登录用户在session中保存的参数名*/
	public static final String LOGIN_USER = "currentUser";
	/**验证码在session中保存的参数名*/
	public static final String CHECK_CODE = "checkCode";
	/**登录跳转地址参数名*/
	public static final String REDIRECT_URL = "redirectUrl";
	/**页面令牌长度*/
	public static final Integer SECURE_CODE = 8;
	
	
	@Autowired
	private UserService userService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private PermissionService permissionService;
	@Autowired
	private MenuService menuService;
	@Autowired
	private DictionaryService dictService;
	@Autowired
	private DeptartmentService deptService;
	@Autowired
	private UserDeptService userDeptService;
	
	
	@PostConstruct
	public void init(){
		uploadPath = sUploadPath;
		openVriCode = sOpenVriCode;
		UserService = userService;
		UserInfoService = userInfoService;
		RoleService = roleService;
		UserRoleService = userRoleService;
		PermissionService = permissionService;
		MenuService = menuService;
		DictionaryService = dictService;
		DeptartmentService = deptService;
		UserDeptService = userDeptService;
	}
	
	/**
	 * 数据库密码加密方法，加密后将密文写到application.properties中
	 * @author Horgn黄小锤
	 * @date 2019年2月18日 下午5:36:20
	 * @param args
	 */
	public static void main(String[] args) {
		
		String content = "0c134ea5";//待加密明文
		
		System.out.println("明文："+content);
		String srypto = SecurityUtils.Crypto.encode(content);
		System.out.println("密文："+srypto);
		System.out.println("原文："+ SecurityUtils.Crypto.decode(srypto));
	}
	
	
	/**
	 * 获取当前登录用户的用户id
	 * @author Horgn黄小锤
	 * @date 2019年1月21日 下午3:19:30
	 * @return
	 */
	public static final String getLoginUserId(){
		
		UserDTO loginUser = getLoginUser();
		if(StringUtils.isEmpty(loginUser.getsId())){
			throw new RuntimeException("获取当前登录用户id失败");
		}
		return loginUser.getsId();
	}
	
	
	/**
	 * 获取当前登录用户实体信息
	 * @author Horgn黄小锤
	 * @date 2019年1月21日 下午3:19:25
	 * @return
	 */
	public static final UserDTO getLoginUser(){
		
		UserDTO loginUser = (UserDTO)SessionUtils.getSessionAttribute(LOGIN_USER);
		if(null == loginUser){
			throw new RuntimeException("获取当前登录用户失败");
		}
		return loginUser;
	}
	
	
	/**
	 * 获取当前用户登录状态，已登录返回true
	 * @author Horgn黄小锤
	 * @date 2019年1月21日 下午3:19:06
	 * @return
	 */
	public static final boolean getLoginState(){
		
		UserDTO loginUser = (UserDTO)SessionUtils.getSessionAttribute(LOGIN_USER);
		if(loginUser == null){
			return false;
		}
		return true;
	}
	
	
	/**
	 * 执行用户注销操作，移除session中保存的登录用户信息<br>
	 * 注意：本方法同时进行shiro注解操作
	 * @author Horgn黄小锤
	 * @date 2019年1月21日 下午3:18:54
	 */
	protected static final void logout(){
		
		org.apache.shiro.SecurityUtils.getSubject().logout();
		SessionUtils.removeSessionAttribute(LOGIN_USER);
	}
	
	
	/**
	 * 执行登录操作，将登录用户实体保存到session中，参数名为 currentUser 
	 * @author Horgn黄小锤
	 * @date 2019年1月21日 下午3:18:41
	 * @param user
	 */
	protected static final void login(UserDTO user){
		
		if(user == null || StringUtils.isEmpty(user.getsId())){
			throw new NullPointerException("登录失败：用户不能为空");
		}
		
		user.setsPassword(null);
		user.setsPwdSecond(null);
		user.setsToken(null);
		
		SessionUtils.setSessionAttribute(LOGIN_USER, user);
	}
	
	
	/**
	 * 刷新当前登录用户保存session中的实体信息，如果参数为空，则自动从session中获取用户id
	 * @author Horgn黄小锤
	 * @date 2019年1月21日 下午3:22:50
	 * @param users
	 */
	protected static final void refreshLoginUser(UserDTO... users){
		
		if(users == null || users.length <= 0){
			login(UserService.findById(getLoginUserId()));
		}
		
		login(users[0]);
	}
	
	
	/**
	 * 获取页面图形验证码
	 * @author Horgn黄小锤
	 * @date 2019年2月18日 下午6:12:44
	 * @param param 可为空，第一个参数为宽（默认120），第二个参数为高（默认40），第三个参数为验证码长度（默认5），其它参数无效，顺序不可变
	 */
	public static final void getVerifyCode(Integer... param){
		VerifyCodeUtils.getVerifyCode(param);
	}
	
	
	/**
	 * 判断验证码是否正确<br>
	 * 1、如果验证码为空，则抛出异常“请输入验证码”<br>
	 * 2、如果验证码不正确，则抛出异常“验证码错误”<br>
	 * 3、如果验证码正确，则从Session中移除该验证码
	 * @author Horgn黄小锤
	 * @date 2019年1月21日 下午3:28:30
	 * @param code 页面返回的验证码（未加密的验证码）
	 * @return
	 */
	public static final void checkVerifyCode(String code){
		
		// 如果配置文件中设置相校验状态为 false， 则跳过验证码校验
		if(null == openVriCode || false == openVriCode){
			return;
		}
		
		String checkCode =  (String) SessionUtils.getSessionAttribute(CHECK_CODE);
		if (StringUtils.isEmpty(checkCode,code)) {
			throw new RuntimeException("请输入验证码");
		}
		
		if(!checkCode.equals(SecurityUtils.MD5.getMD5(code.toUpperCase()))){
			throw new RuntimeException("验证码错误");
		}
		
		SessionUtils.removeSessionAttribute(CHECK_CODE);
	}
	
	
	/**
	 * 判断当前登录用户是否有指定角色中的一个角色<br>
	 * 注意：只要该用户有其中一个角色，即返回true
	 * @author Horgn黄小锤
	 * @date 2019年2月1日 上午8:32:04
	 * @param roles
	 * @return
	 */
//	public static final Boolean hasOneRole(String... roles){
//		
//		Subject subject = org.apache.shiro.SecurityUtils.getSubject();
//		
//		for(String role : roles){
//			if(subject.hasRole(role)){
//				return true;
//			}
//		}
//		
//		return false;
//	}
	
	
	/**
	 * 判断当前登录用户是否有指定角色中的所有角色<br>
	 * 注意：必须拥有指定角色中的全部角色才返回true
	 * @author Horgn黄小锤
	 * @date 2019年2月1日 上午9:30:00
	 * @param roles
	 * @return
	 */
//	public static final Boolean hasAllRoles(String... roles){
//		
//		Subject subject = org.apache.shiro.SecurityUtils.getSubject();
//		return subject.hasAllRoles(Arrays.asList(roles));
//	}
	
	
	/**
	 * 判断当前登录用户是否超级管理员账号
	 * @author Horgn黄小锤
	 * @date 2019年2月1日 上午8:32:36
	 * @return
	 */
	public static final Boolean isAdminUser(){
		
		String userId = getLoginUserId();
		if("admin".equals(userId)){
			return true;
		}
		
		return false;
	}
	
	
	/**
	 * 判断当前登录用户是否有超级管理员角色（admin 角色）
	 * @author Horgn黄小锤
	 * @date 2019年2月17日 下午2:51:16
	 * @return
	 */
	public static final Boolean hasAdminRole(){
		
		Subject subject = org.apache.shiro.SecurityUtils.getSubject();
		return subject.hasRole("admin");
	}
	
	
	/**
	 * 判断当前登录用户是否有指定权限中的一个权限<br>
	 * 注意：只要用户有其中一个权限，即返回true
	 * @author Horgn黄小锤
	 * @date 2019年2月1日 上午8:34:02
	 * @param permissions
	 * @return
	 */
//	public static final Boolean hasOnePermission(String... permissions){
//		
//		Subject subject = org.apache.shiro.SecurityUtils.getSubject();
//		
//		for(String permission : permissions){
//			if(subject.isPermitted(permission)){
//				return true;
//			}
//		}
//		
//		return false;
//	}
	
	
	/**
	 * 判断当前登录用户是否有指定权限中的全部权限<br>
	 * 注意：只有当用户拥有所有指定的权限才返回true
	 * @author Horgn黄小锤
	 * @date 2019年2月1日 上午9:38:55
	 * @param permissions
	 * @return
	 */
//	public static final Boolean hasAllPermissions(String... permissions){
//		
//		Subject subject = org.apache.shiro.SecurityUtils.getSubject();
//		return subject.isPermittedAll(permissions);
//	}
	
	
	/**
	 * 获取固定长度的页面令牌
	 * @author Horgn黄小锤
	 * @date 2019年2月2日 下午4:51:02
	 * @return
	 */
	public static final String getSecureCode(){
		return StringUtils.generateUUID(SECURE_CODE);
	}
	
	
	/**
	 * 从参数中去除页面令牌，获取真正参数值
	 * @author Horgn黄小锤
	 * @date 2019年2月2日 下午4:53:43
	 * @param key
	 * @return
	 */
	public static final String getSecureCode(String key){
		
		if(StringUtils.isEmpty(key)){
			return null;
		}
		
		return key.substring(SECURE_CODE);
	}
	
	
	/**
	 * 根据父级数据字典值及数据等级获取字典数据<br>
	 * 注意：获取多级字典方式：superValue.secondValue.lastValue，最多为三级，且数据等级要与参数值对应
	 * @author Horgn黄小锤
	 * @date 2019年2月6日 上午12:54:05
	 * @param value
	 * @param iDictLevel
	 * @return
	 */
	public static List<DictionaryDTO> getDictsByPValues(String value, Integer iDictLevel){
		return DictionaryService.getDictsByPValues(value, iDictLevel);
	}
	
	
	/**
	 * 获取系统文件/图片上传根目录
	 * @author Horgn黄小锤
	 * @date 2019年2月23日 下午3:24:14
	 * @return
	 */
	public static String getUploadPath(){
		return uploadPath;
	}
	
	
	/**
	 * 获取文件上传后的回调路径根路径<br>
	 * 项目名称/upload/
	 * @author Horgn黄小锤
	 * @date 2019年2月25日 上午9:00:07
	 * @return
	 */
	public static String getResponseUploadPath(){
		String contextPath = SessionUtils.getContextPath();
		return  contextPath + "/upload/";
	}
	
	
}
