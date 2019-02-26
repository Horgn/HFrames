package com.hframe.basic.root.user.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hframe.basic.base.BaseServiceImpl;
import com.hframe.basic.root.role.dao.UserRoleDao;
import com.hframe.basic.root.role.entity.UserRoleDTO;
import com.hframe.basic.root.user.dao.UserDao;
import com.hframe.basic.root.user.dao.UserInfoDao;
import com.hframe.basic.root.user.entity.UserDTO;
import com.hframe.basic.root.user.service.UserService;
import com.hframe.basic.util.SecurityUtils;
import com.hframe.basic.util.StringUtils;



@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<UserDTO> implements UserService{
	
	@Autowired
	private UserDao dao;
	@Autowired
	private UserInfoDao infoDao; 
	@Autowired
	private UserRoleDao userRoleDao;
	
	@PostConstruct
	public void init(){
		super.dao = dao;
	}

	/* (non-Javadoc)
	 * @see com.hframe.basic.base.BaseServiceImpl#save(com.hframe.basic.base.BaseEntity)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String save(UserDTO user) {
		
		user.setsPassword(SecurityUtils.MD5.getMD5(user.getsPassword()));
		if(StringUtils.isNotEmpty(user.getsPwdSecond())){
			user.setsPwdSecond(SecurityUtils.MD5.getMD5(user.getsPwdSecond()));
		}
		
		if(StringUtils.isNotEmpty(user.getsToken())){
			user.setsToken(SecurityUtils.MD5.getMD5(user.getsToken()));
		}
		super.save(user);
		//保存用户登录实体后保存用户信息实体
		//登录实体与信息实体共用主键
		infoDao.saveByUser(user);
		return user.getsId();
	}
	

	@Override
	public UserDTO findByUsername(String sUsername) {
		
		if(StringUtils.isEmpty(sUsername)){
			throw new RuntimeException("获取用户信息失败");
		}
		return dao.findByUsername(sUsername);
	}
	

	@Override
	public UserDTO getUserInfo(String sId) {
		
		if(StringUtils.isEmpty(sId)){
			throw new RuntimeException("获取用户信息失败");
		}
		return dao.getUserInfo(sId);
	}
	

	@Override
	public void updatePwd(String sId, String sPassword, String npwd, String npwd2, Integer iVersion) {
		
		if(StringUtils.isEmpty(sId,sPassword,npwd,npwd2) || null == iVersion){
			throw new RuntimeException("请输入完整信息");
		}
		
		UserDTO user = dao.findById(sId);
		if(user == null){
			throw new RuntimeException("更新用户失败，用户不存在");
		}
		
		if(!npwd.equals(npwd2)){
			throw new RuntimeException("更新用户失败，再次密码不相同");
		}
		
		String op = SecurityUtils.MD5.getMD5(sPassword);
		if(!op.equals(user.getsPassword())){
			throw new RuntimeException("更新用户失败，原密码不正确");
		}
		
		String np = SecurityUtils.MD5.getMD5(npwd);
		if(np.equals(user.getsPassword())){
			throw new RuntimeException("更新用户失败，新密码与原密码不能相同");
		}
		
		user.setsPassword(np);
		user.setiVersion(iVersion);
		dao.update(user);
	}
	

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void changeUserRoles(String sUserId, List<String> roleIds) {
		
		if(StringUtils.isEmpty(sUserId)){
			throw new RuntimeException("请选择指定用户");
		}
		//删除当前角色
		userRoleDao.deleteRolesByUser(sUserId);
		
		//
		if("admin".equals(sUserId)){
			UserRoleDTO userRole = new UserRoleDTO(sUserId, "admin");
			userRoleDao.save(userRole);
		}
		if(null == roleIds || roleIds.size() <= 0){
			return;
		}
		
		//保存新角色
		for(String sId : roleIds){
			UserRoleDTO userRole = new UserRoleDTO(sUserId, sId);
			userRoleDao.save(userRole);
		}
		
	}


}
