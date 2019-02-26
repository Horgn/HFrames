package com.hframe.basic.base;

import java.util.List;
import com.hframe.basic.common.PageResult;

import tk.mybatis.mapper.entity.Example;

/**
 * 通用Service接口基类<br>
 * 注意：<br>
 * 1、事务只能在Service层开启，不能在Dao层开启<br>
 * 2、如果在Service的方法中开启事务，不能使用try捕获<br>
 * 3、开启事务在类或方法上添加注解 @Transactional(rollbackFor = Exception.class)<br>
 * 4、本基类Service实现类中，已对 update、updateSelective、save 三个方法添加了 checkExist 数据重复判断， 只需在项目Dao实现类中重写该方法的判断逻辑即可<br>
 * 5、若重写 update、updateSelective、save 方法，请注意对 checkExist 方法的调用
 * @author Horgn黄小锤
 * @date 2019年1月23日 下午3:09:51
 * @version V1.0
 */
public interface BaseService<T extends BaseDTO> {
	
	/**
	 * 根据主键查询单个实体<br>
	 * 物理查询
	 * @author Horgn黄小锤
	 * @date 2019年1月23日 下午1:43:40
	 * @param sId 主键
	 * @return 单个实体
	 */
	public T findById(String sId);
	
	/**
	 * 根据参数实体中不为空的属性查询单个实体<br>
	 * 逻辑查询
	 * @author Horgn黄小锤
	 * @date 2019年1月23日 下午1:44:13
	 * @param entity 参数实体
	 * @return 单个实体
	 */
	public T find(T entity);
	
	/**
	 * 根据Example参数查询多个实体<br>
	 * 逻辑查询
	 * @author Horgn黄小锤
	 * @date 2019年1月23日 下午1:44:50
	 * @param example 查询参数
	 * @return 实体列表
	 */
	public List<T> findList(Example example);
	
	/**
	 * 根据实体中不为空的字段查询多个记录<br>
	 * 逻辑查询<br>
	 * 注意：本方法无法进行自定义排序
	 * @author Horgn黄小锤
	 * @date 2019年1月30日 上午10:36:17
	 * @param entity
	 * @return
	 */
	public List<T> findList(T entity);
	
	/**
	 * 查询所有实体<br>
	 * 逻辑查询<br>
	 * 注意：本方法默认使用创建时间降序排序
	 * @author Horgn黄小锤
	 * @date 2019年1月23日 下午1:45:27
	 * @return
	 */
	public List<T> findAll();
	
	/**
	 * 根据Example参数实体进行分页查询<br>
	 * 逻辑查询
	 * @author Horgn黄小锤
	 * @date 2019年1月23日 下午6:28:26
	 * @param page 分页参数实体
	 * @param example 查询参数实体，可为空。如果有多个参数，只使用第一个作为查询参数
	 * @return
	 */
	public PageResult findByPage(PageResult page, Example... example);
	
	/**
	 * 根据主键更新实体所有属性
	 * @author Horgn黄小锤
	 * @date 2019年1月23日 下午1:47:00
	 * @param entity 实体
	 * @return
	 */
	public int update(T entity);
	
	/**
	 * 根据主键更新不为空的属性
	 * @author Horgn黄小锤
	 * @date 2019年1月23日 下午1:47:18
	 * @param entity 实体
	 * @return
	 */
	public int updateSelective(T entity);
	
	/**
	 * 保存单个实体<br>
	 * null值不会被保存，将使用字段默认值
	 * @author Horgn黄小锤
	 * @date 2019年1月23日 下午1:47:43
	 * @param entity 实体
	 * @return
	 */
	public String save(T entity);
	
	/**
	 * 根据主键删除单个实体<br>
	 * 逻辑删除
	 * @author Horgn黄小锤
	 * @date 2019年1月23日 下午1:47:55
	 * @param sId 主键
	 * @return
	 */
	public int delete(String sId);
	
	/**
	 * 根据Example参数查询实体数量<br>
	 * 逻辑查询
	 * @author Horgn黄小锤
	 * @date 2019年1月23日 下午1:48:10
	 * @param example 参数实体，可为空。如果有多个参数，只使用第一个参数
	 * @return
	 */
	public int findCount(Example... example);
	
	/**
	 * 根据参数实体中不为空的属性查询实体数量<br>
	 * 逻辑查询
	 * @author Horgn黄小锤
	 * @date 2019年1月23日 下午1:48:34
	 * @param entity 参数实体
	 * @return
	 */
	public int findCount(T entity);
	

}
