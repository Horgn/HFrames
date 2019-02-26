package com.hframe.basic.root.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.ibatis.type.JdbcType;

import com.hframe.basic.base.BaseDTO;

import tk.mybatis.mapper.annotation.ColumnType;

/**
 * 系统用户用户信息实体
 * @author Horgn黄小锤
 * @date 2019年1月19日 上午9:40:28
 * @version V1.0
 */
@Entity
@Table(name="HF_User_Info")
public class UserInfoDTO extends BaseDTO{

	private static final long serialVersionUID = -1625131974122685824L;
	
	public UserInfoDTO() {}
	
	/**
	 * 构造器 UserInfo
	 * @param sId 用户实体id
	 */
	public UserInfoDTO(String sId){
		super.setsId(sId);
	}
	
	/**用户真实姓名*/
	@Column(name="sRealName")
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String sRealName;
	
	

	/** 
	 * 获取  用户真实姓名 
	 * @return sRealName 用户真实姓名 
	 */
	public String getsRealName() {
		return sRealName;
	}

	/** 
	 * 设置 用户真实姓名 
	 * @param sRealName 用户真实姓名 
	 */
	public void setsRealName(String sRealName) {
		this.sRealName = sRealName;
	}
	
	

}
