package com.hframe.basic.base;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import com.hframe.basic.common.PageResult;
import com.hframe.basic.util.ReflectUtils;
import com.hframe.basic.util.StringUtils;

import tk.mybatis.mapper.entity.Example;

/**
 * 通用Service接口实现类<br>
 * 注意：<br>
 * 1、事务只能在Service层开启，不能在Dao层开启<br>
 * 2、如果在Service的方法中开启事务，不能使用try捕获<br>
 * 3、开启事务在类或方法上添加注解 @Transactional(rollbackFor = Exception.class)<br>
 * 4、本基类Service实现类中，已对 update、updateSelective、save 三个方法添加了 checkExist 数据重复判断，只需在项目Dao实现类中重写该方法的判断逻辑即可<br>
 * 5、若重写 update、updateSelective、save 方法，请注意对 checkExist 方法的调用
 * @author Horgn黄小锤
 * @date 2019年1月23日 下午3:10:14
 * @version V1.0
 */
public class BaseServiceImpl<T extends BaseDTO> implements BaseService<T>{
	
	
	@SuppressWarnings("unchecked")
	private Class <T> entityClass = (Class <T>) ((ParameterizedType) 
			getClass().getGenericSuperclass()).getActualTypeArguments()[0]; 
	
	
	public BaseDao<T> dao;
	
	
	@Override
	public T findById(String sId) {
		
		if(StringUtils.isEmpty(sId)){
			throw new RuntimeException("主键不能为空");
		}
		return dao.findById(sId);
	}
	

	@Override
	public T find(T entity) {
		
		if(null == entity){
			throw new RuntimeException("参数实体不能为空");
		}
		return dao.find(entity);
	}
	

	@Override
	public List<T> findList(Example example) {
		
		if(null == example){
			throw new RuntimeException("参数实体不能为空");
		}
		return dao.findList(example);
	}
	
	
	@Override
	public List<T> findList(T entity){
		
		if(null == entity){
			throw new RuntimeException("参数实体不能为空");
		}
		return dao.findList(entity);
	}
	

	@Override
	public List<T> findAll() {
		
		return dao.findAll();
	}
	

	@Override
	public PageResult findByPage(PageResult page, Example... examples) {
		
		Example example = null;
		if(null == examples || examples.length == 0){
			example = new Example(entityClass);
		}else{
			example = examples[0];
		}
		
		if(null == page){
			throw new RuntimeException("分页实体参数不能为空");
		}
		return dao.findByPage(example, page);
	}
	

	@Override
	public int update(T entity) {
		
		if(null == entity){
			throw new RuntimeException("参数实体不能为空");
		}
		return dao.update(entity);
	}

	@Override
	public int updateSelective(T entity) {
		
		if(null == entity){
			throw new RuntimeException("参数实体不能为空");
		}
		return dao.updateSelective(entity);
	}
	

	@Override
	public String save(T entity) {
		
		if(null == entity){
			throw new RuntimeException("参数实体不能为空");
		}
		return dao.save(entity);
	}
	

	@Override
	public int delete(String sId) {
		
		if(StringUtils.isEmpty(sId)){
			throw new RuntimeException("主键不能为空");
		}
		return dao.delete(sId);
	}
	

	@Override
	public int findCount(Example... examples) {
		
		Example example = null;
		if(null == examples || examples.length == 0){
			example = new Example(entityClass);
		}else{
			example = examples[0];
		}
		
		return dao.findCount(example);
	}
	

	@Override
	public int findCount(T entity) {
		
		if(null == entity){
			throw new RuntimeException("实体参数不能为空");
		}
		return dao.findCount(entity);
	}
	
	
	/**
	 * 获取本实体在数据库中的表名
	 * @author Horgn黄小锤
	 * @date 2019年2月4日 下午3:34:57
	 * @return
	 */
	protected final String getTableName(){
		
		String tableName = ReflectUtils.getTableName(entityClass);
		if(StringUtils.isEmpty(tableName)){
			throw new RuntimeException("获取TableName失败");
		}
		return tableName;
	}


}
