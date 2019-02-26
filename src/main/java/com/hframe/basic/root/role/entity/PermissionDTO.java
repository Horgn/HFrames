package com.hframe.basic.root.role.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.hframe.basic.base.BaseDTO;


/**
 * 每组角色权限实体
 * @author Horgn黄小锤
 * @date 2019年1月19日 上午10:19:21
 * @version V1.0
 */
@Entity
@Table(name="HF_Permission")
public class PermissionDTO extends BaseDTO{

	private static final long serialVersionUID = 790440631379733385L;
	
	/**角色id*/
	@Column(name="sRoleId")
	private String sRoleId;
	
	/**菜单id*/
	@Column(name="sMenuId")
	private String sMenuId;
	
	/**权限标识*/
	@Column(name="sPremission")
	private String sPremission;
	
	public PermissionDTO(){}
	
	/**
	 * 构造器 PermissionDTO
	 * @param sRoleId 角色id
	 * @param sPermission 权限id
	 */
	public PermissionDTO(String sRoleId,String sMenuId, String sPermission){
		this.sRoleId = sRoleId;
		this.sMenuId = sMenuId;
		this.sPremission = sPermission;
	}
	
	/**
	 * 构造器 Permission
	 * @param sRoleId 角色id
	 */
	public PermissionDTO(String sRoleId){
		this.sRoleId = sRoleId;
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

	/** 
	 * 获取  权限标识 
	 * @return sPremission 权限标识 
	 */
	public String getsPremission() {
		return sPremission;
	}

	/** 
	 * 设置 权限标识 
	 * @param sPremission 权限标识 
	 */
	public void setsPremission(String sPremission) {
		this.sPremission = sPremission;
	}

	/** 
	 * 获取  菜单id 
	 * @return sMenuId 菜单id 
	 */
	public String getsMenuId() {
		return sMenuId;
	}

	/** 
	 * 设置 菜单id 
	 * @param sMenuId 菜单id 
	 */
	public void setsMenuId(String sMenuId) {
		this.sMenuId = sMenuId;
	}
	
	

}
