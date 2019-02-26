package com.hframe.basic.root.user.service;

import java.util.List;

import com.hframe.basic.base.BaseService;
import com.hframe.basic.root.user.entity.UserDTO;

/**
 * 系统用户Service接口
 * @author Horgn黄小锤
 * @date 2019年1月23日 下午4:45:55
 * @version V1.0
 */
public interface UserService extends BaseService<UserDTO>{
	
	/**
	 * 根据用户名查询用户登录实体
	 * @author Horgn黄小锤
	 * @date 2019年1月26日 上午8:44:53
	 * @param sUsername
	 * @return
	 */
	UserDTO findByUsername(String sUsername);
	
	/**
	 * 获取用户信息，去除密码等关键字段
	 * @author Horgn黄小锤
	 * @date 2019年2月2日 下午2:56:25
	 * @param sId
	 * @return
	 */
	UserDTO getUserInfo(String sId);
	
	/**
	 * 更新用户密码
	 * @author Horgn黄小锤
	 * @date 2019年2月2日 下午3:06:30
	 * @param sId
	 * @param sPassword 去除页面令牌的原密码
	 * @param npwd 去除页面令牌的新密码
	 * @param npwd2 去除页面令牌的新密码2
	 * @param iVersion
	 */
	void updatePwd(String sId, String sPassword, String npwd, String npwd2, Integer iVersion);
	
	/**
	 * 更新用户角色
	 * @author Horgn黄小锤
	 * @date 2019年2月4日 下午4:37:08
	 * @param sUserId
	 * @param roleIds
	 */
	void changeUserRoles(String sUserId, List<String> roleIds);
	
	

}
