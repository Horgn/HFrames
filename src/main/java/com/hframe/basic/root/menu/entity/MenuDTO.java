package com.hframe.basic.root.menu.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hframe.basic.base.BaseDTO;
import com.hframe.basic.common.SysConstants.MenuTypes;
import com.hframe.basic.root.RootUtils;
import com.hframe.basic.util.StringUtils;

/**
 * 系统菜单实体
 * @author Horgn黄小锤
 * @date 2019年1月30日 上午10:53:02
 * @version V1.0
 */
@Entity
@Table(name="HF_Menu")
public class MenuDTO extends BaseDTO{

	private static final long serialVersionUID = -1013917179522321707L;
	
	/**菜单名称*/
	@Column(name="sMenuName")
	private String sMenuName;
	
	/**权限标识*/
	@Column(name="sRoleSign")
	private String sRoleSign;
	
	/**菜单链接地址*/
	@Column(name="sMenuLink")
	private String sMenuLink;
	
	/**菜单图标*/
	@Column(name="sMenuImg")
	private String sMenuImg;
	
	/**父级id*/
	@Column(name="sParentId")
	private String sParentId;
	
	/**菜单类型*/
	@Column(name="iMenuType")
	private Integer iMenuType;
	
	/**本菜单下的分组菜单，主菜单可调用*/
	@Transient
	private List<MenuDTO> groupMenus;
	
	/**本菜单下的页面菜单，分组菜单可调用*/
	@Transient
	private List<MenuDTO> pageMenus;
	
	/**本菜单下的元素菜单，页面菜单可调用*/
	@Transient
	private List<MenuDTO> elementMenus;
	
	/**根据当前登录用户权限，获取本菜单下的分组菜单，主菜单可调用*/
	@Transient
	private List<MenuDTO> groupMenusByPermi;
	
	/**根据当前登录用户权限，获取本菜单下的页面菜单，分组菜单可调用*/
	@Transient
	private List<MenuDTO> pageMenusByPermi;
	
	/**根据当前登录用户权限，获取本菜单下的元素菜单，页面菜单可调用*/
	@Transient
	private List<MenuDTO> elementMenusByPermi;
	
	/**父级菜单*/
	@Transient
	private MenuDTO parentDTO;
	
	public MenuDTO(){}
	
	/**
	 * 构造器 Menu
	 * @param iMenuType 菜单类型
	 */
	public MenuDTO(Integer iMenuType){
		this.iMenuType = iMenuType;
	}
	
	/**
	 * 构造器 Menu
	 * @param sParentId 父级id，可空
	 * @param iMenuType 菜单类型
	 */
	public MenuDTO(String sParentId, Integer iMenuType){
		this.sParentId = sParentId;
		this.iMenuType = iMenuType;
	}

	/** 
	 * 获取  本菜单下的分组菜单，主菜单可调用 
	 * @return groupMenus 本菜单下的分组菜单，主菜单可调用 
	 */
	@JsonBackReference
	public List<MenuDTO> getGroupMenus() {
		if(null == groupMenus && StringUtils.isNotEmpty(getsId()) && null != iMenuType && MenuTypes.Main_Menu == iMenuType){
			groupMenus = RootUtils.MenuService.getGroupMenus(getsId());
		}
		return groupMenus;
	}

	/** 
	 * 获取  本菜单下的页面菜单，分组菜单可调用 
	 * @return pageMenus 本菜单下的页面菜单，分组菜单可调用 
	 */
	@JsonBackReference
	public List<MenuDTO> getPageMenus() {
		if(null == pageMenus && StringUtils.isNotEmpty(getsId()) && null != iMenuType && MenuTypes.Group_Menu == iMenuType ){
			pageMenus = RootUtils.MenuService.getPageMenus(getsId());
		}
		return pageMenus;
	}

	/** 
	 * 获取  本菜单下的元素菜单，页面菜单可调用 
	 * @return elementMenus 本菜单下的元素菜单，页面菜单可调用 
	 */
	@JsonBackReference
	public List<MenuDTO> getElementMenus() {
		if(null == elementMenus && StringUtils.isNotEmpty(getsId()) && null != iMenuType && MenuTypes.Page_Menu == iMenuType){
			elementMenus = RootUtils.MenuService.getElementMenus(getsId());
		}
		return elementMenus;
	}
	

	/** 
	 * 获取  根据当前登录用户权限，获取本菜单下的分组菜单，主菜单可调用 
	 * @return groupMenusByPermi 根据当前登录用户权限，获取本菜单下的分组菜单，主菜单可调用 
	 */
	@JsonBackReference
	public List<MenuDTO> getGroupMenusByPermi() {
		if(null == groupMenusByPermi && StringUtils.isNotEmpty(getsId()) && null != iMenuType && MenuTypes.Main_Menu == iMenuType){
			groupMenusByPermi = RootUtils.MenuService.getMenuByLoginUserPermission(getsId(), MenuTypes.Group_Menu);
		}
		return groupMenusByPermi;
	}

	/** 
	 * 获取  根据当前登录用户权限，获取本菜单下的页面菜单，分组菜单可调用 
	 * @return pageMenusByPermi 根据当前登录用户权限，获取本菜单下的页面菜单，分组菜单可调用 
	 */
	@JsonBackReference
	public List<MenuDTO> getPageMenusByPermi() {
		if(null == pageMenusByPermi && StringUtils.isNotEmpty(getsId()) && null != iMenuType && MenuTypes.Group_Menu == iMenuType){
			pageMenusByPermi = RootUtils.MenuService.getMenuByLoginUserPermission(getsId(), MenuTypes.Page_Menu);
		}
		return pageMenusByPermi;
	}

	/** 
	 * 获取  根据当前登录用户权限，获取本菜单下的元素菜单，页面菜单可调用 
	 * @return elementMenusByPermi 根据当前登录用户权限，获取本菜单下的元素菜单，页面菜单可调用 
	 */
	@JsonBackReference
	public List<MenuDTO> getElementMenusByPermi() {
		if(null == elementMenusByPermi && StringUtils.isNotEmpty(getsId()) && null != iMenuType && MenuTypes.Page_Menu == iMenuType){
			elementMenusByPermi = RootUtils.MenuService.getMenuByLoginUserPermission(getsId(), MenuTypes.Element_Menu);
		}
		return elementMenusByPermi;
	}

	/** 
	 * 获取  父级菜单 
	 * @return parentDTO 父级菜单 
	 */
	@JsonBackReference
	public MenuDTO getParentDTO() {
		if(null == parentDTO && StringUtils.isNotEmpty(sParentId) && null != iMenuType && MenuTypes.Main_Menu != iMenuType){
			parentDTO = RootUtils.MenuService.findById(sParentId);
		}
		return parentDTO;
	}

	/** 
	 * 获取  菜单名称 
	 * @return sMenuName 菜单名称 
	 */
	public String getsMenuName() {
		return sMenuName;
	}

	/** 
	 * 设置 菜单名称 
	 * @param sMenuName 菜单名称 
	 */
	public void setsMenuName(String sMenuName) {
		this.sMenuName = sMenuName;
	}

	/** 
	 * 获取  权限标识 
	 * @return sRoleSign 权限标识 
	 */
	public String getsRoleSign() {
		return sRoleSign;
	}

	/** 
	 * 设置 权限标识 
	 * @param sRoleSign 权限标识 
	 */
	public void setsRoleSign(String sRoleSign) {
		this.sRoleSign = sRoleSign;
	}

	/** 
	 * 获取  菜单链接地址 
	 * @return sMenuLink 菜单链接地址 
	 */
	public String getsMenuLink() {
		return sMenuLink;
	}

	/** 
	 * 设置 菜单链接地址 
	 * @param sMenuLink 菜单链接地址 
	 */
	public void setsMenuLink(String sMenuLink) {
		this.sMenuLink = sMenuLink;
	}

	/** 
	 * 获取  菜单图标 
	 * @return sMenuImg 菜单图标 
	 */
	public String getsMenuImg() {
		return sMenuImg;
	}

	/** 
	 * 设置 菜单图标 
	 * @param sMenuImg 菜单图标 
	 */
	public void setsMenuImg(String sMenuImg) {
		this.sMenuImg = sMenuImg;
	}

	/** 
	 * 获取  父级id 
	 * @return sParentId 父级id 
	 */
	public String getsParentId() {
		return sParentId;
	}

	/** 
	 * 设置 父级id 
	 * @param sParentId 父级id 
	 */
	public void setsParentId(String sParentId) {
		this.sParentId = sParentId;
	}

	/** 
	 * 获取  菜单类型 
	 * @return iMenuType 菜单类型 
	 */
	public Integer getiMenuType() {
		return iMenuType;
	}

	/** 
	 * 设置 菜单类型 
	 * @param iMenuType 菜单类型 
	 */
	public void setiMenuType(Integer iMenuType) {
		this.iMenuType = iMenuType;
	}

	
}
