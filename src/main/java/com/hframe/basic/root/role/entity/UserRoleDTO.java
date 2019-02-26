package com.hframe.basic.root.role.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hframe.basic.base.BaseDTO;
import com.hframe.basic.root.RootUtils;
import com.hframe.basic.util.StringUtils;


/**
 * 系统用户角色列表实体
 * @author Horgn黄小锤
 * @date 2019年1月19日 上午9:58:29
 * @version V1.0
 */
@Entity
@Table(name="HF_User_Role")
public class UserRoleDTO extends BaseDTO{

	private static final long serialVersionUID = 888357005233497028L;
	
	/**用户id*/
	@Column(name="sUserId")
	private String sUserId;
	
	/**角色id*/
	@Column(name="sRoleId")
	private String sRoleId;
	
	/**角色实体*/
	@Transient
	private RoleDTO role;
	
	public UserRoleDTO(){}
	
	/**
	 * 构造器 UserRole
	 * @param sUserId 用户id
	 */
	public UserRoleDTO(String sUserId){
		this.sUserId = sUserId;
	}
	
	/**
	 * 构造器 UserRole
	 * @param sUserId 用户id
	 * @param sRoleId 角色id
	 */
	public UserRoleDTO(String sUserId, String sRoleId){
		this.sUserId = sUserId;
		this.sRoleId = sRoleId;
	}
	

	/** 
	 * 获取  角色实体 
	 * @return role 角色实体 
	 */
	@JsonBackReference
	public RoleDTO getRole() {
		if(role == null && StringUtils.isNotEmpty(sRoleId)){
			role = RootUtils.RoleService.findById(sRoleId);
		}
		return role;
	}

	/** 
	 * 获取  用户id 
	 * @return sUserId 用户id 
	 */
	public String getsUserId() {
		return sUserId;
	}

	/** 
	 * 设置 用户id 
	 * @param sUserId 用户id 
	 */
	public void setsUserId(String sUserId) {
		this.sUserId = sUserId;
	}

	/** 
	 * 获取  角色id 
	 * @return sRoleId 角色id 
	 */
	public String getsRoleId() {
		return sRoleId;
	}

	/** 
	 * 设置 角色id 
	 * @param sRoleId 角色id 
	 */
	public void setsRoleId(String sRoleId) {
		this.sRoleId = sRoleId;
	}
	
	

}
