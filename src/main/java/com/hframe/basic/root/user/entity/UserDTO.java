package com.hframe.basic.root.user.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.ibatis.type.JdbcType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hframe.basic.base.BaseDTO;
import com.hframe.basic.root.RootUtils;
import com.hframe.basic.root.role.entity.UserRoleDTO;
import com.hframe.basic.util.StringUtils;

import tk.mybatis.mapper.annotation.ColumnType;

/**
 * 系统用户实体
 * @author Horgn黄小锤
 * @date 2019年1月19日 上午9:29:44
 * @version V1.0
 */
@Entity
@Table(name="HF_User")
public class UserDTO extends BaseDTO{
	
	private static final long serialVersionUID = 5530085862769780889L;

	/**登录用户名*/
	@Column(name="sUsername")
	private String sUsername;
	
	/**登录密码*/
	@Column(name="sPassword")
	private String sPassword;
	
	/**用户昵称*/
	@Column(name="sNickname")
	private String sNickname;
	
	/**二级密码*/
	@Column(name="sPwdSecond")
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String sPwdSecond;
	
	/**用户令牌*/
	@Column(name="sToken")
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String sToken;
	
	/**用户信息实体*/
	@Transient
	private UserInfoDTO userInfo;
	
	/**用户角色列表*/
	@Transient
	private List<UserRoleDTO> userRoles;
	
	

	/** 
	 * 获取  用户信息实体 
	 * @return userInfo 用户信息实体 
	 */
	@JsonBackReference
	public UserInfoDTO getUserInfo() {
		if(userInfo == null && StringUtils.isNotEmpty(getsId())){
			userInfo = RootUtils.UserInfoService.findById(getsId());
		}
		return userInfo;
	}
	
	/** 
	 * 获取  用户角色列表 
	 * @return userRoles 用户角色列表 
	 */
	@JsonBackReference
	public List<UserRoleDTO> getUserRoles() {
		if(userRoles == null && StringUtils.isNotEmpty(getsId())){
			userRoles = RootUtils.UserRoleService.findUserRoles(getsId());
		}
		return userRoles;
	}

	/** 
	 * 获取  登录用户名 
	 * @return sUsername 登录用户名 
	 */
	public String getsUsername() {
		return sUsername;
	}

	/** 
	 * 设置 登录用户名 
	 * @param sUsername 登录用户名 
	 */
	public void setsUsername(String sUsername) {
		this.sUsername = sUsername;
	}

	/** 
	 * 获取  登录密码 
	 * @return sPassword 登录密码 
	 */
	public String getsPassword() {
		return sPassword;
	}

	/** 
	 * 设置 登录密码 
	 * @param sPassword 登录密码 
	 */
	public void setsPassword(String sPassword) {
		this.sPassword = sPassword;
	}

	/** 
	 * 获取  用户昵称 
	 * @return sNickname 用户昵称 
	 */
	public String getsNickname() {
		return sNickname;
	}

	/** 
	 * 设置 用户昵称 
	 * @param sNickname 用户昵称 
	 */
	public void setsNickname(String sNickname) {
		this.sNickname = sNickname;
	}

	/** 
	 * 获取  二级密码 
	 * @return sPwdSecond 二级密码 
	 */
	public String getsPwdSecond() {
		return sPwdSecond;
	}

	/** 
	 * 设置 二级密码 
	 * @param sPwdSecond 二级密码 
	 */
	public void setsPwdSecond(String sPwdSecond) {
		this.sPwdSecond = sPwdSecond;
	}

	/** 
	 * 获取  用户令牌 
	 * @return sToken 用户令牌 
	 */
	public String getsToken() {
		return sToken;
	}

	/** 
	 * 设置 用户令牌 
	 * @param sToken 用户令牌 
	 */
	public void setsToken(String sToken) {
		this.sToken = sToken;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder string  = new StringBuilder("UserDTO[ ");
		string.append(" sId="+ getsId());
		string.append(", sUsername="+sUsername);
		string.append(", sNickname="+sNickname);
		string.append(super.toString());
		string.append(" ]; ");
		return string.toString();
	}
	
}
