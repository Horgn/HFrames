package com.hframe.note.friend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.hframe.basic.base.BaseDTO;
import com.hframe.basic.root.RootUtils;
import com.hframe.basic.root.user.entity.UserDTO;
import com.hframe.basic.util.StringUtils;

/**
 * 小锤笔记--好友实体
 * @author Horgn黄小锤
 * @date 2019年2月21日 下午5:35:48
 * @version V1.0
 */
@Entity
@Table(name="HN_Friend")
public class HFriendDTO extends BaseDTO{

	private static final long serialVersionUID = -695202190345961036L;
	
	/**用户id*/
	@Column(name="sUserId")
	private String sUserId;
	
	/**好友id*/
	@Column(name="sFriendId")
	private String sFriendId;
	
	/**好友昵称*/
	@Column(name="sFedNickname")
	private String sFedNickname;
	
	/**对外状态*/
	@Column(name="iOutterState")
	private Integer iOutterState;
	
	/**对内状态*/
	@Column(name="iInnerState")
	private Integer iInnerState;
	
	/**好友亲密度*/
	@Column(name="iIntimacy")
	private Integer iIntimacy;
	
	/**好友实体*/
	@Transient
	private UserDTO friend;
	
	
	/** 
	 * 获取  好友实体 
	 * @return friend 好友实体 
	 */
	public UserDTO getFriend() {
		if(null == friend && StringUtils.isNotEmpty(sFriendId)){
			friend = RootUtils.UserService.findById(sFriendId);
		}
		return friend;
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
	 * 获取  好友id 
	 * @return sFriendId 好友id 
	 */
	public String getsFriendId() {
		return sFriendId;
	}

	/** 
	 * 设置 好友id 
	 * @param sFriendId 好友id 
	 */
	public void setsFriendId(String sFriendId) {
		this.sFriendId = sFriendId;
	}

	/** 
	 * 获取  好友昵称 
	 * @return sFedNickname 好友昵称 
	 */
	public String getsFedNickname() {
		return sFedNickname;
	}

	/** 
	 * 设置 好友昵称 
	 * @param sFedNickname 好友昵称 
	 */
	public void setsFedNickname(String sFedNickname) {
		this.sFedNickname = sFedNickname;
	}

	/** 
	 * 获取  对外状态 
	 * @return iOutterState 对外状态 
	 */
	public Integer getiOutterState() {
		return iOutterState;
	}

	/** 
	 * 设置 对外状态 
	 * @param iOutterState 对外状态 
	 */
	public void setiOutterState(Integer iOutterState) {
		this.iOutterState = iOutterState;
	}

	/** 
	 * 获取  对内状态 
	 * @return iInnerState 对内状态 
	 */
	public Integer getiInnerState() {
		return iInnerState;
	}

	/** 
	 * 设置 对内状态 
	 * @param iInnerState 对内状态 
	 */
	public void setiInnerState(Integer iInnerState) {
		this.iInnerState = iInnerState;
	}

	/** 
	 * 获取  好友亲密度 
	 * @return iIntimacy 好友亲密度 
	 */
	public Integer getiIntimacy() {
		return iIntimacy;
	}

	/** 
	 * 设置 好友亲密度 
	 * @param iIntimacy 好友亲密度 
	 */
	public void setiIntimacy(Integer iIntimacy) {
		this.iIntimacy = iIntimacy;
	}


	
	
	

}
