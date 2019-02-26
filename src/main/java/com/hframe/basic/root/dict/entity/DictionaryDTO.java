package com.hframe.basic.root.dict.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.ibatis.type.JdbcType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hframe.basic.base.BaseDTO;
import com.hframe.basic.common.SysConstants;
import com.hframe.basic.root.RootUtils;
import com.hframe.basic.util.StringUtils;

import tk.mybatis.mapper.annotation.ColumnType;

/**
 * 系统数据字典
 * @author Horgn黄小锤
 * @date 2019年1月30日 上午8:35:35
 * @version V1.0
 */
@Entity
@Table(name="HF_Dictionary")
public class DictionaryDTO extends BaseDTO{

	private static final long serialVersionUID = 2632125371359795214L;
	
	/**字典名*/
	@Column(name="sDictName")
	private String sDictName;
	
	/**字典值*/
	@Column(name="sDictValue")
	private String sDictValue;
	
	/**字典类型*/
	@Column(name="iDictType")
	private Integer iDictType;
	
	/**父级id*/
	@Column(name="sParentId")
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String sParentId;
	
	/**数据等级*/
	@Column(name="iDictLevel")
	private Integer iDictLevel;
	
	/**本字典下的所有子字典*/
	@Transient
	private List<DictionaryDTO> dataDicts;
	
	/**父级字典*/
	@Transient
	private DictionaryDTO parentDTO;
	
	

	public DictionaryDTO() {}
	
	/**
	 * 构造方法
	 * @param value 字典值
	 */
	public DictionaryDTO(String value){
		this.sDictValue = value;
	}
	
	/**
	 * 构造器 DataDict
	 * @param iDictLevel 数据等级
	 */
	public DictionaryDTO(Integer iDictLevel){
		this.iDictLevel = iDictLevel;
	}
	
	/**
	 * 构造方法
	 * 构造器 DataDict
	 * @param sParentId 父级id
	 * @param iDictLevel 数据等级
	 */
	public DictionaryDTO(String sParentId, Integer iDictLevel){
		this.sParentId = sParentId;
		this.iDictLevel = iDictLevel;
	}

	/** 
	 * 获取  本字典下的所有子字典 
	 * @return dataDicts 本字典下的所有子字典 
	 */
	@JsonBackReference
	public List<DictionaryDTO> getDataDicts() {
		if(null == dataDicts && StringUtils.isNotEmpty(sParentId) && null != iDictLevel && iDictLevel < SysConstants.Dict_Max_Lev){
			dataDicts = RootUtils.DictionaryService.getSonDataDicts(sParentId, iDictLevel + 1);
		}
		return dataDicts;
	}
	
	/** 
	 * 获取  父级字典 
	 * @return parentDTO 父级字典 
	 */
	public DictionaryDTO getParentDTO() {
		if(null == parentDTO && StringUtils.isNotEmpty(sParentId)){
			parentDTO  = RootUtils.DictionaryService.findById(sParentId);
		}
		return parentDTO;
	}

	/** 
	 * 获取  字典名 
	 * @return sDictName 字典名 
	 */
	public String getsDictName() {
		return sDictName;
	}

	/** 
	 * 设置 字典名 
	 * @param sDictName 字典名 
	 */
	public void setsDictName(String sDictName) {
		this.sDictName = sDictName;
	}

	/** 
	 * 获取  字典值 
	 * @return sDictValue 字典值 
	 */
	public String getsDictValue() {
		return sDictValue;
	}

	/** 
	 * 设置 字典值 
	 * @param sDictValue 字典值 
	 */
	public void setsDictValue(String sDictValue) {
		this.sDictValue = sDictValue;
	}

	/** 
	 * 获取  字典类型 
	 * @return iDictType 字典类型 
	 */
	public Integer getiDictType() {
		return iDictType;
	}

	/** 
	 * 设置 字典类型 
	 * @param iDictType 字典类型 
	 */
	public void setiDictType(Integer iDictType) {
		this.iDictType = iDictType;
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
	 * 获取  数据等级 
	 * @return iDictLevel 数据等级 
	 */
	public Integer getiDictLevel() {
		return iDictLevel;
	}

	/** 
	 * 设置 数据等级 
	 * @param iDictLevel 数据等级 
	 */
	public void setiDictLevel(Integer iDictLevel) {
		this.iDictLevel = iDictLevel;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder string  = new StringBuilder("DictionaryDTO[ ");
		string.append(" sId="+ getsId());
		string.append(", sDictName="+sDictName);
		string.append(", sDictValue="+sDictValue);
		string.append(", sParentId="+sParentId);
		string.append(", iDictType="+iDictType);
		string.append(", iDictLevel="+iDictLevel);
		string.append(super.toString());
		string.append(" ]; ");
		return string.toString();
	}
	

}
