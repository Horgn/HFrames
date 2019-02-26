package com.hframe.basic.root.dept.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hframe.basic.base.BaseDTO;
import com.hframe.basic.root.RootUtils;
import com.hframe.basic.root.user.entity.UserDTO;
import com.hframe.basic.util.StringUtils;

/**
 * 系统--部门负责人记录实体
 * @author Horgn黄小锤
 * @date 2019年2月9日 上午11:57:47
 * @version V1.0
 */
@Entity
@Table(name="HF_Dept_Leader")
public class DeptLeaderDTO extends BaseDTO{

	private static final long serialVersionUID = 5330011962514068328L;
	
	/**部门id*/
	@Column(name="sDeptId")
	private String sDeptId;
	
	/**负责人id*/
	@Column(name="sLeaderId")
	private String sLeaderId;
	
	/**开始负责时间*/
	@Column(name="dStartDate")
	private Date dStartDate;
	
	/**结束负责时间*/
	@Column(name="dEndDate")
	private Date dEndDate;
	
	/**负责人状态*/
	@Column(name="iLeaderState")
	private Integer iLeaderState;
	
	/**负责人实体*/
	@Transient
	private UserDTO leaderDTO;
	
	/**部门实体*/
	@Transient
	private DeptartmentDTO deptartmentDTO;
	
	public DeptLeaderDTO(){}
	
	/**
	 * 构造器 DeptLeaderDTO
	 * @param sDeptId 部门id
	 * @param sLeaderId 负责人id
	 */
	public DeptLeaderDTO(String sDeptId, String sLeaderId){
		this.sDeptId = sDeptId;
		this.sLeaderId = sLeaderId;
	}
	

	/** 
	 * 获取  负责人实体 
	 * @return leaderDTO 负责人实体 
	 */
	@JsonBackReference
	public UserDTO getLeaderDTO() {
		if(null == leaderDTO && StringUtils.isNotEmpty(sLeaderId)){
			leaderDTO = RootUtils.UserService.findById(sLeaderId);
		}
		return leaderDTO;
	}

	/** 
	 * 获取  部门实体 
	 * @return deptartmentDTO 部门实体 
	 */
	@JsonBackReference
	public DeptartmentDTO getDeptartmentDTO() {
		if(null == deptartmentDTO && StringUtils.isNotEmpty(sDeptId)){
			deptartmentDTO = RootUtils.DeptartmentService.findById(sDeptId);
		}
		return deptartmentDTO;
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
	 * 获取  负责人id 
	 * @return sLeaderId 负责人id 
	 */
	public String getsLeaderId() {
		return sLeaderId;
	}

	/** 
	 * 设置 负责人id 
	 * @param sLeaderId 负责人id 
	 */
	public void setsLeaderId(String sLeaderId) {
		this.sLeaderId = sLeaderId;
	}

	/** 
	 * 获取  开始负责时间 
	 * @return dStartDate 开始负责时间 
	 */
	public Date getdStartDate() {
		return dStartDate;
	}

	/** 
	 * 设置 开始负责时间 
	 * @param dStartDate 开始负责时间 
	 */
	public void setdStartDate(Date dStartDate) {
		this.dStartDate = dStartDate;
	}

	/** 
	 * 获取  结束负责时间 
	 * @return dEndDate 结束负责时间 
	 */
	public Date getdEndDate() {
		return dEndDate;
	}

	/** 
	 * 设置 结束负责时间 
	 * @param dEndDate 结束负责时间 
	 */
	public void setdEndDate(Date dEndDate) {
		this.dEndDate = dEndDate;
	}

	/** 
	 * 获取  负责人状态 
	 * @return iLeaderState 负责人状态 
	 */
	public Integer getiLeaderState() {
		return iLeaderState;
	}

	/** 
	 * 设置 负责人状态 
	 * @param iLeaderState 负责人状态 
	 */
	public void setiLeaderState(Integer iLeaderState) {
		this.iLeaderState = iLeaderState;
	}
	
	
	

}
