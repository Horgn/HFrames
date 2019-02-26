package com.hframe.basic.config;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hframe.basic.root.role.entity.PermissionDTO;
import com.hframe.basic.root.role.entity.RoleDTO;
import com.hframe.basic.root.role.entity.UserRoleDTO;
import com.hframe.basic.root.user.entity.UserDTO;
import com.hframe.basic.root.user.service.UserService;

/**
 * Shiro身份校验核心类;
 * 
 * @author Horgn黄小锤
 * @date 2019年1月11日 上午11:08:33
 * @version V1.0
 */
public class ShiroRealm extends AuthorizingRealm {

	@Autowired
	private UserService userService;
	
	private Logger logger = LoggerFactory.getLogger(ShiroRealm.class);

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		// 获取登录用户名
		String username = (String) principals.getPrimaryPrincipal();
		// 获取用户信息
		UserDTO user = userService.findByUsername(username);
		
		logger.info("========获取角色权限[ " + username + " ]===========");
		
		// 添加角色和权限
		// 添加角色
		List<UserRoleDTO> userRoles = user.getUserRoles();
		for (UserRoleDTO userRole : userRoles) {
			RoleDTO role = userRole.getRole();
			
			simpleAuthorizationInfo.addRole(role.getsRoleSign());
			simpleAuthorizationInfo.addStringPermission(role.getsRoleSign());
			//添加权限
			List<PermissionDTO> permissions = role.getPermissions();
			permissions.forEach((permi) -> {
				simpleAuthorizationInfo.addStringPermission(permi.getsPremission());
			});
		}
		
		return simpleAuthorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		
		// 加这一步的目的是在Post请求的时候会先进认证，然后在到请求
		if (token.getPrincipal() == null) {
			return null;
		}
		
		// 获取用户信息
		String username = token.getPrincipal().toString();
		UserDTO user = userService.findByUsername(username);

		if (user == null) {
			// 这里返回后会报出对应异常
			return null;
		} else {
			
			// 上面添加账号时候生成的加密盐
			ByteSource bytes = ByteSource.Util.bytes(user.getsUsername());
			// 这里验证authenticationToken和simpleAuthenticationInfo的信息
			SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username,
					user.getsPassword(), bytes, getName());
			
			return simpleAuthenticationInfo;
		}
	}

	/**
	 * 重写方法,清除当前用户的的 授权缓存
	 */
	@Override
	public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
	}

	/**
	 * 重写方法，清除当前用户的 认证缓存
	 */
	@Override
	public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
		super.clearCachedAuthenticationInfo(principals);
	}

	@Override
	public void clearCache(PrincipalCollection principals) {
		super.clearCache(principals);
	}

	/**
	 * 自定义方法：清除所有 授权缓存
	 * @author Horgn黄小锤
	 * @date 2019年1月30日 下午5:21:45
	 */
	public void clearAllCachedAuthorizationInfo() {
		getAuthorizationCache().clear();
	}

	/**
	 * 自定义方法：清除所有 认证缓存
	 * @author Horgn黄小锤
	 * @date 2019年1月30日 下午5:21:39
	 */
	public void clearAllCachedAuthenticationInfo() {
		getAuthenticationCache().clear();
	}

	/**
	 * 自定义方法：清除所有的 认证缓存 和 授权缓存
	 * @author Horgn黄小锤
	 * @date 2019年1月30日 下午5:21:25
	 */
	public void clearAllCache() {
		clearAllCachedAuthenticationInfo();
		clearAllCachedAuthorizationInfo();
	}

}