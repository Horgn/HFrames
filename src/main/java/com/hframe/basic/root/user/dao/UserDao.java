package com.hframe.basic.root.user.dao;

import java.util.List;

import com.hframe.basic.base.BaseDao;
import com.hframe.basic.root.user.entity.UserDTO;

/**
 * 系统用户Dao接口
 * @author Horgn黄小锤
 * @date 2019年1月23日 下午4:41:24
 * @version V1.0
 */
public interface UserDao extends BaseDao<UserDTO>{
	
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
	 * 根据用户id获取用户信息
	 * @author Horgn黄小锤
	 * @date 2019年2月21日 下午6:27:27
	 * @param ids
	 * @return
	 */
	List<UserDTO> getUsersByIds(List<Object> ids);

}
