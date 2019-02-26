package com.hframe.note.category.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hframe.basic.base.BaseDTO;
import com.hframe.basic.util.StringUtils;
import com.hframe.note.HNoteUtils;
import com.hframe.note.note.entity.HNoteDTO;

/**
 * 小锤笔记--笔记菜单实体
 * @author Horgn黄小锤
 * @date 2019年2月6日 下午8:07:15
 * @version V1.0
 */
@Entity
@Table(name="HN_Category")
public class HCategoryDTO extends BaseDTO{

	private static final long serialVersionUID = -5018187449592146036L;
	
	/**菜单名称*/
	@Column(name="sCategoryName")
	private String sCategoryName;
	
	/**上级菜单id*/
	@Column(name="sParentId")
	private String sParentId;
	
	/**菜单层级*/
	@Column(name="iCategoryLev")
	private Integer iCategoryLev;
	
	/**该分类的子分类*/
	@Transient
	private List<HCategoryDTO> sonCategorys;
	
	/**该分类下所有笔记*/
	@Transient
	private List<HNoteDTO> notes;
	
	

	/** 
	 * 获取  该分类的子分类 
	 * @return sonCategorys 该分类的子分类 
	 */
	@JsonBackReference
	public List<HCategoryDTO> getSonCategorys() {
		if(null == sonCategorys && StringUtils.isNotEmpty(sParentId)){
			sonCategorys = HNoteUtils.CategroyService.getSonCotegorys(sParentId);
		}
		return sonCategorys;
	}

	/** 
	 * 获取  该分类下所有笔记 
	 * @return notes 该分类下所有笔记 
	 */
	@JsonBackReference
	public List<HNoteDTO> getNotes() {
		if(null == notes && StringUtils.isNotEmpty(getsId())){
			notes = HNoteUtils.NoteService.getNoteNameByCategory(getsId());
		}
		return notes;
	}

	/** 
	 * 获取  菜单名称 
	 * @return sCategoryName 菜单名称 
	 */
	public String getsCategoryName() {
		return sCategoryName;
	}

	/** 
	 * 设置 菜单名称 
	 * @param sCategoryName 菜单名称 
	 */
	public void setsCategoryName(String sCategoryName) {
		this.sCategoryName = sCategoryName;
	}

	/** 
	 * 获取  上级菜单id 
	 * @return sParentId 上级菜单id 
	 */
	public String getsParentId() {
		return sParentId;
	}

	/** 
	 * 设置 上级菜单id 
	 * @param sParentId 上级菜单id 
	 */
	public void setsParentId(String sParentId) {
		this.sParentId = sParentId;
	}

	/** 
	 * 获取  菜单层级 
	 * @return iCategoryLev 菜单层级 
	 */
	public Integer getiCategoryLev() {
		return iCategoryLev;
	}

	/** 
	 * 设置 菜单层级 
	 * @param iCategoryLev 菜单层级 
	 */
	public void setiCategoryLev(Integer iCategoryLev) {
		this.iCategoryLev = iCategoryLev;
	}
	
	

}
