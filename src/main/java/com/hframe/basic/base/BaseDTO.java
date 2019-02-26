package com.hframe.basic.base;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.ibatis.type.JdbcType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hframe.basic.root.RootUtils;
import com.hframe.basic.root.user.entity.UserDTO;
import com.hframe.basic.util.StringUtils;

import tk.mybatis.mapper.annotation.ColumnType;


/**
 * 系统实体基类
 * @author Horgn黄小锤
 * @date 2019年1月19日 上午9:07:28
 * @version V1.0
 */
@SuppressWarnings("serial")
public class BaseDTO implements Serializable{
	
	
	/**主键*/
	@Id
	@Column(name="sId")
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	protected String sId;
	
	/**备注*/
	@Column(name="sMemo")
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	protected String sMemo;
	
	/**创建人*/
	@Column(name="sCreator")
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	protected String sCreator;
	
	/**创建时间*/
	@Column(name="dCreateDate")
	@ColumnType(jdbcType=JdbcType.DATETIMEOFFSET)
	protected Date dCreateDate;
	
	/**修改人*/
	@Column(name="sModifieder")
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	protected String sModifieder;
	
	/**修改时间*/
	@Column(name="dModifiedDate")
	@ColumnType(jdbcType=JdbcType.DATETIMEOFFSET)
	protected Date dModifiedDate;
	
	/**排序*/
	@Column(name="iOrder")
	@ColumnType(jdbcType=JdbcType.NUMERIC)
	protected Integer iOrder;
	
	/**状态*/
	@Column(name="iState")
	@ColumnType(jdbcType=JdbcType.NUMERIC)
	protected Integer iState;
	
	/**版本*/
	@Version
	@Column(name="iVersion")
	@ColumnType(jdbcType=JdbcType.NUMERIC)
	protected Integer iVersion;
	
	/**创建人实体*/
	@Transient
	protected UserDTO creator;
	
	/**修改人实体*/
	@Transient
	protected UserDTO modifieder;
	
	

	/** 
	 * 获取  创建人实体 
	 * @return creator 创建人实体 
	 */
	@JsonBackReference
	public UserDTO getCreator() {
		if(creator == null && StringUtils.isNotEmpty(sCreator)){
			creator = RootUtils.UserService.findById(sCreator);
		}
		return creator;
	}

	/** 
	 * 获取  修改人实体 
	 * @return modifieder 修改人实体 
	 */
	@JsonBackReference
	public UserDTO getModifieder() {
		if(modifieder == null && StringUtils.isNotEmpty(sModifieder)){
			modifieder = RootUtils.UserService.findById(sModifieder);
		}
		return modifieder;
	}

	/** 
	 * 获取  主键 
	 * @return sId 主键 
	 */
	public String getsId() {
		return sId;
	}

	/** 
	 * 设置 主键 
	 * @param sId 主键 
	 */
	public void setsId(String sId) {
		this.sId = sId;
	}

	/** 
	 * 获取  备注 
	 * @return sMemo 备注 
	 */
	public String getsMemo() {
		return sMemo;
	}

	/** 
	 * 设置 备注 
	 * @param sMemo 备注 
	 */
	public void setsMemo(String sMemo) {
		this.sMemo = sMemo;
	}

	/** 
	 * 获取  创建人 
	 * @return sCreator 创建人 
	 */
	public String getsCreator() {
		return sCreator;
	}

	/** 
	 * 设置 创建人 
	 * @param sCreator 创建人 
	 */
	public void setsCreator(String sCreator) {
		this.sCreator = sCreator;
	}

	/** 
	 * 获取  创建时间 
	 * @return dCreateDate 创建时间 
	 */
	public Date getdCreateDate() {
		return dCreateDate;
	}

	/** 
	 * 设置 创建时间 
	 * @param dCreateDate 创建时间 
	 */
	public void setdCreateDate(Date dCreateDate) {
		this.dCreateDate = dCreateDate;
	}

	/** 
	 * 获取  修改人 
	 * @return sModifieder 修改人 
	 */
	public String getsModifieder() {
		return sModifieder;
	}

	/** 
	 * 设置 修改人 
	 * @param sModifieder 修改人 
	 */
	public void setsModifieder(String sModifieder) {
		this.sModifieder = sModifieder;
	}

	/** 
	 * 获取  修改时间 
	 * @return dModifiedDate 修改时间 
	 */
	public Date getdModifiedDate() {
		return dModifiedDate;
	}

	/** 
	 * 设置 修改时间 
	 * @param dModifiedDate 修改时间 
	 */
	public void setdModifiedDate(Date dModifiedDate) {
		this.dModifiedDate = dModifiedDate;
	}

	/** 
	 * 获取  排序 
	 * @return iOrder 排序 
	 */
	public Integer getiOrder() {
		return iOrder;
	}

	/** 
	 * 设置 排序 
	 * @param iOrder 排序 
	 */
	public void setiOrder(Integer iOrder) {
		this.iOrder = iOrder;
	}

	/** 
	 * 获取  状态 
	 * @return iState 状态 
	 */
	public Integer getiState() {
		return iState;
	}

	/** 
	 * 设置 状态 
	 * @param iState 状态 
	 */
	public void setiState(Integer iState) {
		this.iState = iState;
	}

	/** 
	 * 获取  版本 
	 * @return iVersion 版本 
	 */
	public Integer getiVersion() {
		return iVersion;
	}

	/** 
	 * 设置 版本 
	 * @param iVersion 版本 
	 */
	public void setiVersion(Integer iVersion) {
		this.iVersion = iVersion;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder string  = new StringBuilder("");
		string.append(", sCreator="+sCreator);
		string.append(", sModifieder="+sModifieder);
		string.append(", iState="+iState);
		string.append(", iOrder="+iOrder);
		string.append(", iVersion="+iVersion);
		return string.toString();
	}
	
}
