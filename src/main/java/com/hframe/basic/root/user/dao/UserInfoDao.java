package com.hframe.basic.root.user.dao;

import com.hframe.basic.base.BaseDao;
import com.hframe.basic.root.user.entity.UserDTO;
import com.hframe.basic.root.user.entity.UserInfoDTO;

/**
 * 系统用户信息Dao接口
 * @author Horgn黄小锤
 * @date 2019年1月23日 下午4:48:19
 * @version V1.0
 */
public interface UserInfoDao extends BaseDao<UserInfoDTO>{
	
	/**
	 * 根据用户实体创建用户信息，用户实体与用户信息实体共用主键sId
	 * @author Horgn黄小锤
	 * @date 2019年1月30日 上午11:39:02
	 * @param user
	 * @return
	 */
	String saveByUser(UserDTO user);

}
