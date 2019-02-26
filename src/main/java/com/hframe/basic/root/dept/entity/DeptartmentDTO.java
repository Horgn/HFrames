package com.hframe.basic.root.dept.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.ibatis.type.JdbcType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hframe.basic.base.BaseDTO;
import com.hframe.basic.root.RootUtils;
import com.hframe.basic.root.user.entity.UserDTO;
import com.hframe.basic.util.StringUtils;

import tk.mybatis.mapper.annotation.ColumnType;

/**
 * 系统--部门实体
 * @author Horgn黄小锤
 * @date 2019年2月4日 下午5:53:48
 * @version V1.0
 */
@Entity
@Table(name="HF_Deptartment")
public class DeptartmentDTO extends BaseDTO{

	private static final long serialVersionUID = -8879427926070914128L;
	
	/**部门名称*/
	@Column(name="sDeptName")
	private String sDeptName;
	
	/**部门代码*/
	@Column(name="sDeptSign")
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String sDeptSign;
	
	/**部门说明*/
	@Column(name="sDeptExplain")
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String sDeptExplain;
	
	/**部门等级（最高级为0，最低级为5）*/
	@Column(name="iDeptLev")
	private Integer iDeptLev;
	
	/**上级部门*/
	@Column(name="sParentId")
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String sParentId;
	
	/**部门负责人id*/
	@Column(name="sLeaderId")
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String sLeaderId;
	
	/**部门权限标识*/
	@Column(name="sDeptRole")
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String sDeptRole;
	
	/**部门id关联*/
	@Column(name="sDeptRoot")
	private String sDeptRoot;
	
	/**部门负责人实体*/
	@Transient
	private UserDTO leader;
	
	/**上级部门实体*/
	@Transient
	private DeptartmentDTO parentDept;
	
	/**该部门下所有用户（包括子部门）*/
	@Transient
	private List<UserDTO> deptUsers;
	
	/**该部门的直接子部门*/
	@Transient
	private List<DeptartmentDTO> deptartments;
	
	public DeptartmentDTO(){}
	
	/**
	 * 构造器 DeptartmentDTO
	 * @param sParentId 上级部门id
	 */
	public DeptartmentDTO(String sParentId){
		this.sParentId = sParentId;
	}
	
	/**
	 * 构造器 DeptartmentDTO
	 * @param sParentId 上级部门id
	 * @param iDeptLev 部门等级
	 */
	public DeptartmentDTO(String sParentId, Integer iDeptLev){
		this.sParentId = sParentId;
		this.iDeptLev = iDeptLev;
	}
	

	/** 
	 * 获取  部门负责人实体 
	 * @return leader 部门负责人实体 
	 */
	@JsonBackReference
	public UserDTO getLeader() {
		if(null == leader && StringUtils.isNotEmpty(sLeaderId)){
			leader = RootUtils.UserService.findById(sLeaderId);
		}
		return leader;
	}
	
	/** 
	 * 获取  上级部门实体 
	 * @return parentDept 上级部门实体 
	 */
	@JsonBackReference
	public DeptartmentDTO getParentDept() {
		if(null == parentDept && StringUtils.isNotEmpty(sParentId)){
			parentDept = RootUtils.DeptartmentService.findById(sParentId);
		}
		return parentDept;
	}

	/** 
	 * 获取  该部门下所有用户 （包括子部门）
	 * @return deptUsers 该部门下所有用户 
	 */
	@JsonBackReference
	public List<UserDTO> getDeptUsers() {
		return deptUsers;
	}

	/** 
	 * 获取  该部门的直接子部门 
	 * @return deptartments 该部门的直接子部门 
	 */
	@JsonBackReference
	public List<DeptartmentDTO> getDeptartments() {
		if(null == deptartments && StringUtils.isNotEmpty(getsId())){
			deptartments = RootUtils.DeptartmentService.getDeptartments(getsId());
		}
		return deptartments;
	}

	/** 
	 * 获取  部门名称 
	 * @return sDeptName 部门名称 
	 */
	public String getsDeptName() {
		return sDeptName;
	}

	/** 
	 * 设置 部门名称 
	 * @param sDeptName 部门名称 
	 */
	public void setsDeptName(String sDeptName) {
		this.sDeptName = sDeptName;
	}

	/** 
	 * 获取  部门代码 
	 * @return sDeptSign 部门代码 
	 */
	public String getsDeptSign() {
		return sDeptSign;
	}

	/** 
	 * 设置 部门代码 
	 * @param sDeptSign 部门代码 
	 */
	public void setsDeptSign(String sDeptSign) {
		this.sDeptSign = sDeptSign;
	}

	/** 
	 * 获取  部门说明 
	 * @return sDeptExplain 部门说明 
	 */
	public String getsDeptExplain() {
		return sDeptExplain;
	}

	/** 
	 * 设置 部门说明 
	 * @param sDeptExplain 部门说明 
	 */
	public void setsDeptExplain(String sDeptExplain) {
		this.sDeptExplain = sDeptExplain;
	}

	/** 
	 * 获取  部门等级（最高级为0，最低级为9） 
	 * @return iDeptLev 部门等级（最高级为0，最低级为9） 
	 */
	public Integer getiDeptLev() {
		return iDeptLev;
	}

	/** 
	 * 设置 部门等级（最高级为0，最低级为9） 
	 * @param iDeptLev 部门等级（最高级为0，最低级为9） 
	 */
	public void setiDeptLev(Integer iDeptLev) {
		this.iDeptLev = iDeptLev;
	}

	/** 
	 * 获取  上级部门 
	 * @return sParentId 上级部门 
	 */
	public String getsParentId() {
		return sParentId;
	}

	/** 
	 * 设置 上级部门 
	 * @param sParentId 上级部门 
	 */
	public void setsParentId(String sParentId) {
		this.sParentId = sParentId;
	}

	/** 
	 * 获取  部门负责人id 
	 * @return sLeaderId 部门负责人id 
	 */
	public String getsLeaderId() {
		return sLeaderId;
	}

	/** 
	 * 设置 部门负责人id 
	 * @param sLeaderId 部门负责人id 
	 */
	public void setsLeaderId(String sLeaderId) {
		this.sLeaderId = sLeaderId;
	}

	/** 
	 * 获取  部门权限标识 
	 * @return sDeptRole 部门权限标识 
	 */
	public String getsDeptRole() {
		return sDeptRole;
	}

	/** 
	 * 设置 部门权限标识 
	 * @param sDeptRole 部门权限标识 
	 */
	public void setsDeptRole(String sDeptRole) {
		this.sDeptRole = sDeptRole;
	}

	/** 
	 * 获取  部门id关联 
	 * @return sDeptRoot 部门id关联 
	 */
	public String getsDeptRoot() {
		return sDeptRoot;
	}

	/** 
	 * 设置 部门id关联 
	 * @param sDeptRoot 部门id关联 
	 */
	public void setsDeptRoot(String sDeptRoot) {
		this.sDeptRoot = sDeptRoot;
	}
	
	
	
	

}
