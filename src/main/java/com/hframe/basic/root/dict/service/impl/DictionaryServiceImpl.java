package com.hframe.basic.root.dict.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hframe.basic.base.BaseServiceImpl;
import com.hframe.basic.common.SysConstants;
import com.hframe.basic.root.RootUtils;
import com.hframe.basic.root.dict.dao.DictionaryDao;
import com.hframe.basic.root.dict.entity.DictionaryDTO;
import com.hframe.basic.root.dict.service.DictionaryService;
import com.hframe.basic.util.StringUtils;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;


@Service
public class DictionaryServiceImpl extends BaseServiceImpl<DictionaryDTO> implements DictionaryService{
	
	@Autowired
	private DictionaryDao dao;
	
	@PostConstruct
	public void init(){
		super.dao = dao;
	}

	
	@Override
	public List<DictionaryDTO> getSonDataDicts(String sParentId, Integer iDictLevel) {
		
		if(StringUtils.isEmpty(sParentId) || null == iDictLevel){
			throw new RuntimeException("参数不能为空");
		}
		
		if(SysConstants.Dict_Max_Lev < iDictLevel || SysConstants.Dict_Super_Lev > iDictLevel){
			return new ArrayList<>();
		}
		
		Example example = new Example(DictionaryDTO.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("sParentId", sParentId);
		criteria.andEqualTo("iDictLevel", iDictLevel);
		example.setOrderByClause(" iOrder desc ");
		
		return dao.findList(example);
	}

	@Override
	public List<DictionaryDTO> getParentDicts() {
		
		Example example = new Example(DictionaryDTO.class);
		Criteria criteria = example.createCriteria();
		
		criteria.andEqualTo("iDictLevel", SysConstants.Dict_Super_Lev);
		example.setOrderByClause(" iOrder desc ");
		
		return dao.findList(example);
	}

	/* (non-Javadoc)
	 * @see com.hframe.basic.base.BaseServiceImpl#update(com.hframe.basic.base.BaseEntity)
	 */
	@Override
	public int update(DictionaryDTO entity) {
		
		if(null != entity){
			if(StringUtils.isNotEmpty(entity.getsParentId())){
				DictionaryDTO parent = dao.findById(entity.getsParentId());
				if(null == parent){
					throw new RuntimeException("父级字典不存在");
				}
				
				int level = parent.getiDictLevel();
				if(level == SysConstants.Dict_Max_Lev){
					throw new RuntimeException("数据等级已达上限，最多创建4级数据字典");
				}
				
				entity.setiDictLevel(level + 1);
			}else{
				entity.setiDictLevel(SysConstants.Dict_Super_Lev);
			}
		}
		
		return super.update(entity);
	}

	/* (non-Javadoc)
	 * @see com.hframe.basic.base.BaseServiceImpl#updateSelective(com.hframe.basic.base.BaseEntity)
	 */
	@Override
	public int updateSelective(DictionaryDTO entity) {
		
		if(null != entity){
			if(StringUtils.isNotEmpty(entity.getsParentId())){
				DictionaryDTO parent = dao.findById(entity.getsParentId());
				
				if(null == parent){
					throw new RuntimeException("父级字典不存在");
				}
				
				int level = parent.getiDictLevel();
				if(level == SysConstants.Dict_Max_Lev){
					throw new RuntimeException("数据等级已达上限，最多创建4级数据字典");
				}
				
				entity.setiDictLevel(level + 1);
			}else{
				entity.setiDictLevel(SysConstants.Dict_Super_Lev);
			}
		}
		
		return super.updateSelective(entity);
	}

	/* (non-Javadoc)
	 * @see com.hframe.basic.base.BaseServiceImpl#save(com.hframe.basic.base.BaseEntity)
	 */
	@Override
	public String save(DictionaryDTO entity) {
		
		if(null != entity){
			if(StringUtils.isNotEmpty(entity.getsParentId())){
				DictionaryDTO parent = dao.findById(entity.getsParentId());
				if(null == parent){
					throw new RuntimeException("父级字典不存在");
				}
				
				int level = parent.getiDictLevel();
				if(level == SysConstants.Dict_Max_Lev){
					throw new RuntimeException("数据等级已达上限，最多创建4级数据字典");
				}
				
				entity.setiDictLevel(level + 1);
			}else{
				entity.setiDictLevel(SysConstants.Dict_Super_Lev);
			}
		}
		
		return super.save(entity);
	}
	
	@Override
	public List<DictionaryDTO> getDictsByPValues(String value, Integer iDictLevel){
		
		if(StringUtils.isEmpty(value) || null == iDictLevel){
			throw new RuntimeException("参数不能为空");
		}
		
		String[] vPStrings = value.trim().split("\\.");
		int len = vPStrings.length;
		if(len <= 0 || StringUtils.isEmpty(vPStrings)){
			throw new RuntimeException("字典值不正确");
		}
		
		if(len != iDictLevel || SysConstants.Dict_Max_Lev < iDictLevel || SysConstants.Dict_Super_Lev >= iDictLevel){
			throw new RuntimeException("数据等级不正确");
		}
		
		DictionaryDTO parentSuper = null;//根字典
		DictionaryDTO parentSercend = null;
		
		DictionaryDTO dictionaryDTO = new DictionaryDTO();
		dictionaryDTO.setsDictValue(vPStrings[0]);
		dictionaryDTO.setiDictLevel(0);
		parentSuper = dao.find(dictionaryDTO);
		
		if(null == parentSuper){
			throw new RuntimeException("字典值不存在："+vPStrings[0]);
		}
		
		if(1== len){
			Example example = new Example(DictionaryDTO.class);
			Criteria criteria = example.createCriteria();
			criteria.andEqualTo("sParentId", parentSuper.getsId());
			criteria.andEqualTo("iDictLevel", 1);
			example.setOrderByClause(" iOrder desc ");
			return dao.findList(example);
		}
		
		dictionaryDTO = new DictionaryDTO();
		dictionaryDTO.setsDictValue(vPStrings[1]);
		dictionaryDTO.setiDictLevel(1);
		dictionaryDTO.setsParentId(parentSuper.getsId());
		parentSercend = dao.find(dictionaryDTO);
		
		if(null == parentSercend){
			throw new RuntimeException("字典值不存在："+vPStrings[1]);
		}
		
		if(2== len){
			Example example = new Example(DictionaryDTO.class);
			Criteria criteria = example.createCriteria();
			criteria.andEqualTo("sParentId", parentSercend.getsId());
			criteria.andEqualTo("iDictLevel", 2);
			example.setOrderByClause(" iOrder desc ");
			return dao.findList(example);
		}
		
		dictionaryDTO = new DictionaryDTO();
		dictionaryDTO.setsDictValue(vPStrings[2]);
		dictionaryDTO.setiDictLevel(2);
		dictionaryDTO.setsParentId(parentSercend.getsId());
		parentSercend = dao.find(dictionaryDTO);
		
		if(null == parentSercend){
			throw new RuntimeException("字典值不存在："+vPStrings[1]);
		}
		
		if(3== len){
			Example example = new Example(DictionaryDTO.class);
			Criteria criteria = example.createCriteria();
			criteria.andEqualTo("sParentId", parentSercend.getsId());
			criteria.andEqualTo("iDictLevel", 3);
			example.setOrderByClause(" iOrder desc ");
			return dao.findList(example);
		}
		
		return new ArrayList<>();
	}


	@Override
	public Map<String, List<DictionaryDTO>> getTreeDeptionary(String sParentId) {
		
		Map<String, List<DictionaryDTO>> map = new HashMap<>();
		List<DictionaryDTO> parentDicts = null;
		
		if(StringUtils.isEmpty(sParentId)){
			parentDicts = getParentDicts();
			map.put("parentDicts", parentDicts);
			
			for(DictionaryDTO dictionary1 : parentDicts){
				map.putAll(loopZtreeDicts(dictionary1));
			}
			return map;
		}
		
		DictionaryDTO parent = dao.findById(sParentId);
		if(null== parent){
			return map;
		}
		map.putAll(loopZtreeDicts(parent));
		
		return map;
	}
	
	/**
	 * 递归获取数据字典
	 * @author Horgn黄小锤
	 * @date 2019年2月6日 上午10:35:37
	 * @param dictionaryDTO
	 * @return
	 */
	private Map<String, List<DictionaryDTO>> loopZtreeDicts(DictionaryDTO dictionaryDTO){
		
		Map<String, List<DictionaryDTO>> map = new HashMap<>();
		if(null == dictionaryDTO || SysConstants.Dict_Max_Lev <= dictionaryDTO.getiDictLevel()){
			return map;
		}
		
		List<DictionaryDTO> sonDataDicts = getSonDataDicts(dictionaryDTO.getsId(), dictionaryDTO.getiDictLevel() + 1);
		if(null == sonDataDicts || sonDataDicts.size() <= 0){
			return map;
		}
		
		map.put(dictionaryDTO.getsId(), sonDataDicts);
		for(DictionaryDTO dto : sonDataDicts){
			map.putAll(loopZtreeDicts(dto));
		}
		return map;
	}

	/* (non-Javadoc)
	 * @see com.hframe.basic.base.BaseServiceImpl#delete(java.lang.String)
	 */
	@Override
	public int delete(String sId) {
		
		if(StringUtils.isEmpty(sId)){
			throw new RuntimeException("参数不能为空");
		}
		
		DictionaryDTO dictionaryDTO = dao.findById(sId);
		if(null == dictionaryDTO){
			throw new RuntimeException("删除失败：字典不存在");
		}
		
		//系统字典只能由admin账户删除
		if(SysConstants.DictTypes.System_Dict == dictionaryDTO.getiDictType() && !RootUtils.getLoginUserId().equals("admin")){
			throw new RuntimeException("删除失败：系统字典只能由超级管理员删除");
		}
		
		if(hasSonDicts(sId))	{
			throw new RuntimeException("删除失败：该字典下存在子字典");
		}
		return super.delete(sId);
	}

	/**
	 * 判断指定字典下是否存在子字典，如果存在则返回true
	 * @author Horgn黄小锤
	 * @date 2019年2月6日 下午2:46:56
	 * @param sId
	 * @return
	 */
	private boolean hasSonDicts(String sId) {
		
		if(StringUtils.isEmpty(sId)){
			throw new RuntimeException("参数不能为空");
		}
		
		DictionaryDTO dictionaryDTO = new DictionaryDTO();
		dictionaryDTO.setsParentId(sId);
		int count = dao.findCount(dictionaryDTO);
		
		if(count > 0){
			return true;
		}
		return false;
	}


	@Override
	public DictionaryDTO getDictionaryValue(String value, Integer iDictLevel, Integer iOrder) {
		if(null == iOrder){
			throw new RuntimeException("字典下标（序号）不能为空");
		}
		List<DictionaryDTO> dictsByPValues = getDictsByPValues(value, iDictLevel);
		for(DictionaryDTO dictionary : dictsByPValues){
			if(dictionary.getiOrder() == iOrder){
				return dictionary;
			}
		}
		return null;
	}


	@Override
	public List<DictionaryDTO> getDictionarysByLev(Integer iDictLevel) {
		if(null == iDictLevel){
			throw new RuntimeException("参数不能为空");
		}
		
		Example example = new Example(DictionaryDTO.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("iDictLevel", iDictLevel);
		example.selectProperties("sId", "sDictName", "sDictValue", "sParentId", "iDictLevel");
		example.setOrderByClause("iOrder desc");
		
		return dao.findList(example);
	}
	
	
	

}
