package com.hframe.note.note.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.hframe.basic.base.BaseDTO;

/**
 * 小锤笔记-笔记实体
 * @author Horgn黄小锤
 * @date 2019年2月6日 下午7:54:46
 * @version V1.0
 */
@Entity
@Table(name="HN_Note")
public class HNoteDTO extends BaseDTO{

	private static final long serialVersionUID = 7783054369714391707L;
	
	/**笔记标题*/
	@Column(name="sNoteTitle")
	private String sNoteTitle;
	
	/**笔记内容*/
	@Column(name="sNoteContent")
	private String sNoteContent;
	
	/**笔记标签*/
	@Column(name="sNoteTag")
	private String sNoteTag;
	
	/**笔记可见类型*/
	@Column(name="iShowType")
	private Integer iShowType;
	
	/**所属菜单*/
	@Column(name="sCategoryId")
	private String sCategoryId;
	
	/**笔记访问量*/
	@Column(name="iReadCount")
	private Integer iReadCount;
	

	/** 
	 * 获取  笔记标题 
	 * @return sNoteTitle 笔记标题 
	 */
	public String getsNoteTitle() {
		return sNoteTitle;
	}

	/** 
	 * 设置 笔记标题 
	 * @param sNoteTitle 笔记标题 
	 */
	public void setsNoteTitle(String sNoteTitle) {
		this.sNoteTitle = sNoteTitle;
	}

	/** 
	 * 获取  笔记内容 
	 * @return sNoteContent 笔记内容 
	 */
	public String getsNoteContent() {
		return sNoteContent;
	}

	/** 
	 * 设置 笔记内容 
	 * @param sNoteContent 笔记内容 
	 */
	public void setsNoteContent(String sNoteContent) {
		this.sNoteContent = sNoteContent;
	}

	/** 
	 * 获取  笔记可见类型 
	 * @return iShowType 笔记可见类型 
	 */
	public Integer getiShowType() {
		return iShowType;
	}

	/** 
	 * 设置 笔记可见类型 
	 * @param iShowType 笔记可见类型 
	 */
	public void setiShowType(Integer iShowType) {
		this.iShowType = iShowType;
	}

	/** 
	 * 获取  所属菜单 
	 * @return sCategoryId 所属菜单 
	 */
	public String getsCategoryId() {
		return sCategoryId;
	}

	/** 
	 * 设置 所属菜单 
	 * @param sCategoryId 所属菜单 
	 */
	public void setsCategoryId(String sCategoryId) {
		this.sCategoryId = sCategoryId;
	}

	/** 
	 * 获取  笔记标签 
	 * @return sNoteTag 笔记标签 
	 */
	public String getsNoteTag() {
		return sNoteTag;
	}

	/** 
	 * 设置 笔记标签 
	 * @param sNoteTag 笔记标签 
	 */
	public void setsNoteTag(String sNoteTag) {
		this.sNoteTag = sNoteTag;
	}

	/** 
	 * 获取  笔记访问量 
	 * @return iReadCount 笔记访问量 
	 */
	public Integer getiReadCount() {
		return iReadCount;
	}

	/** 
	 * 设置 笔记访问量 
	 * @param iReadCount 笔记访问量 
	 */
	public void setiReadCount(Integer iReadCount) {
		this.iReadCount = iReadCount;
	}

	
	

}
