package com.hframe.basic.root.role.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hframe.basic.base.BaseDTO;
import com.hframe.basic.root.RootUtils;
import com.hframe.basic.util.StringUtils;


/**
 * 系统用户角色实体
 * @author Horgn黄小锤
 * @date 2019年1月19日 上午9:54:36
 * @version V1.0
 */
@Entity
@Table(name="HF_Role")
public class RoleDTO extends BaseDTO{

	private static final long serialVersionUID = -799098376113145378L;
	
	/**角色名称*/
	@Column(name="sRoleName")
	private String sRoleName;
	
	/**角色标识*/
	@Column(name="sRoleSign")
	private String sRoleSign;
	
	/**角色等级*/
	@Column(name="iRoleLev")
	private Integer iRoleLev;
	
	/**获取角色所有权限*/
	@Transient
	private List<PermissionDTO> permissions;
	
	

	/** 
	 * 获取  获取角色所有权限 
	 * @return permissions 获取角色所有权限 
	 */
	@JsonBackReference
	public List<PermissionDTO> getPermissions() {
		if(permissions == null && StringUtils.isNotEmpty(getsId())){
			permissions = RootUtils.PermissionService.getPermissions(getsId());
		}
		return permissions;
	}

	/** 
	 * 获取  角色名称 
	 * @return sRoleName 角色名称 
	 */
	public String getsRoleName() {
		return sRoleName;
	}

	/** 
	 * 设置 角色名称 
	 * @param sRoleName 角色名称 
	 */
	public void setsRoleName(String sRoleName) {
		this.sRoleName = sRoleName;
	}

	/** 
	 * 获取  角色标识 
	 * @return sRoleSign 角色标识 
	 */
	public String getsRoleSign() {
		return sRoleSign;
	}

	/** 
	 * 设置 角色标识 
	 * @param sRoleSign 角色标识 
	 */
	public void setsRoleSign(String sRoleSign) {
		this.sRoleSign = sRoleSign;
	}

	/** 
	 * 获取  角色等级 
	 * @return iRoleLev 角色等级 
	 */
	public Integer getiRoleLev() {
		return iRoleLev;
	}

	/** 
	 * 设置 角色等级 
	 * @param iRoleLev 角色等级 
	 */
	public void setiRoleLev(Integer iRoleLev) {
		this.iRoleLev = iRoleLev;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder string  = new StringBuilder("RoleDTO[ ");
		string.append(" sId="+ getsId());
		string.append(", sRoleName="+sRoleName);
		string.append(", sRoleSign="+sRoleSign);
		string.append(super.toString());
		string.append(" ]; ");
		return string.toString();
	}

}
