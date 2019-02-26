package com.hframe.basic.root.dept.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.hframe.basic.base.BaseDTO;

/**
 * 系统-用户-部门关联实体
 * @author Horgn黄小锤
 * @date 2019年2月4日 下午6:04:17
 * @version V1.0
 */
@Entity
@Table(name="HF_User_Dept")
public class UserDeptDTO extends BaseDTO{

	private static final long serialVersionUID = 8286121288828974469L;
	
	/**用户id*/
	@Column(name="sUserId")
	private String sUserId;
	
	/**部门id*/
	@Column(name="sDeptId")
	private String sDeptId;
	
	/**开始关联时间*/
	@Column(name="dStartDate")
	private Date dStartDate;
	
	/**结束关联时间*/
	@Column(name="dEndDate")
	private Date dEndDate;
	
	/**关联状态*/
	@Column(name="iRelaState")
	private Integer iRelaState;

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
	 * 获取  部门id 
	 * @return sDeptId 部门id 
	 */
	public String getsDeptId() {
		return sDeptId;
	}

	/** 
	 * 设置 部门id 
	 * @param sDeptId 部门id 
	 */
	public void setsDeptId(String sDeptId) {
		this.sDeptId = sDeptId;
	}

	/** 
	 * 获取  开始关联时间 
	 * @return dStartDate 开始关联时间 
	 */
	public Date getdStartDate() {
		return dStartDate;
	}

	/** 
	 * 设置 开始关联时间 
	 * @param dStartDate 开始关联时间 
	 */
	public void setdStartDate(Date dStartDate) {
		this.dStartDate = dStartDate;
	}

	/** 
	 * 获取  结束关联时间 
	 * @return dEndDate 结束关联时间 
	 */
	public Date getdEndDate() {
		return dEndDate;
	}

	/** 
	 * 设置 结束关联时间 
	 * @param dEndDate 结束关联时间 
	 */
	public void setdEndDate(Date dEndDate) {
		this.dEndDate = dEndDate;
	}

	/** 
	 * 获取  关联状态 
	 * @return iRelaState 关联状态 
	 */
	public Integer getiRelaState() {
		return iRelaState;
	}

	/** 
	 * 设置 关联状态 
	 * @param iRelaState 关联状态 
	 */
	public void setiRelaState(Integer iRelaState) {
		this.iRelaState = iRelaState;
	}
	
	

}
