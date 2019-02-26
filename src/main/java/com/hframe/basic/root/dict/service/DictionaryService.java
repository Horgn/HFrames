package com.hframe.basic.root.dict.service;

import java.util.List;
import java.util.Map;

import com.hframe.basic.base.BaseService;
import com.hframe.basic.root.dict.entity.DictionaryDTO;

/**
 * 系统数据字典Service
 * @author Horgn黄小锤
 * @date 2019年1月30日 上午8:43:14
 * @version V1.0
 */
public interface DictionaryService extends BaseService<DictionaryDTO>{
	
	/**
	 * 根据父级id及子数据等级获取字典数据
	 * @author Horgn黄小锤
	 * @date 2019年1月30日 上午8:47:42
	 * @param sParentId 父级id
	 * @param iDictLevel 子数据等级(父等级+1)
	 * @return
	 */
	public List<DictionaryDTO> getSonDataDicts(String sParentId, Integer iDictLevel);
	
	/**
	 * 获取最上级数据字典
	 * @author Horgn黄小锤
	 * @date 2019年1月30日 上午8:53:12
	 * @param iDictLevel
	 * @return
	 */
	public List<DictionaryDTO> getParentDicts();
	
	/**
	 * 根据父级数据字典值及数据等级获取字典数据<br>
	 * 注意：获取多级字典方式：superValue.secondValue.lastValue，最多为三级，且数据等级要与参数值对应
	 * @author Horgn黄小锤
	 * @date 2019年2月6日 上午12:54:05
	 * @param value
	 * @param iDictLevel
	 * @return
	 */
	public List<DictionaryDTO> getDictsByPValues(String value, Integer iDictLevel);
	
	/**
	 * 获取指定字典下的所有子字典，如果参数为空，则获取所有
	 * @author Horgn黄小锤
	 * @date 2019年2月5日 下午9:14:05
	 * @param sParentId
	 * @return
	 */
	public Map<String, List<DictionaryDTO>> getTreeDeptionary(String sParentId);
	
	/**
	 * 根据父级数据字典值及数据等级、数据序号获取字典数据<br>
	 * 注意：<br>
	 * 1、获取多级字典方式：superValue.secondValue.lastValue，最多为三级，且数据等级要与参数值对应<br>
	 * 2、序号即排序的值，如果有多个相同的值，将返回第一个
	 * @author Horgn黄小锤
	 * @date 2019年2月12日 下午8:43:06
	 * @param value 父字典值组
	 * @param iDictLevel 数据等级
	 * @param iOrder 数据下标
	 * @return
	 */
	public DictionaryDTO getDictionaryValue(String value, Integer iDictLevel, Integer iOrder);
	
	/**
	 * 根据字典等级获取所有数据字典
	 * @author Horgn黄小锤
	 * @date 2019年2月19日 上午9:25:16
	 * @param iDictLevel
	 * @return
	 */
	public List<DictionaryDTO> getDictionarysByLev(Integer iDictLevel);
}
