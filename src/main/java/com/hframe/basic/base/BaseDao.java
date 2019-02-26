package com.hframe.basic.base;

import java.util.LinkedHashMap;
import java.util.List;

import com.hframe.basic.common.PageResult;

import tk.mybatis.mapper.entity.Example;


/**
 * 通用Dao基类接口<br>
 * 注意：<br>
 * 1、建议不要在dao层开启事务处理<br>
 * 2、本接口中的删除操作为逻辑删除，对应字段为iState，删除后值为0<br>
 * 3、物理查询（查询所有状态的记录）、逻辑查询（只查询状态为1的记录）<br>
 * 4、Dao层只对本类Service层开放，不允许Controller层直接使用<br>
 * 5、请在项目Dao实现类中重新实现checkExist(T entity)方法，自定义判断逻辑<br>
 * 6、建议在项目Dao实现类中，直接调用基类的方法（super.xxx()），而不是调用项目Mapper中的原始方法
 * @author Horgn黄小锤
 * @date 2019年1月23日 下午12:44:14
 * @version V1.0
 */
public interface BaseDao<T extends BaseDTO>{
	
	
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
	 * @date 2019年1月23日 下午1:45:41
	 * @param example 参数实体
	 * @param page 分页查询
	 * @return 分页查询
	 */
	public PageResult findByPage(Example example,PageResult page);
	
	
	/**
	 * 根据实体中不为空的属性进行分页查询<br>
	 * 逻辑查询<br>
	 * 注意：<br>
	 * 1、本方法无法进行模糊查询和排序
	 * @author Horgn黄小锤
	 * @date 2019年1月30日 下午12:32:23
	 * @param entity
	 * @param page
	 * @return
	 */
	public PageResult findByPage(T entity, PageResult page);
	
	
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
	 * @param example 参数实体
	 * @return
	 */
	public int findCount(Example example);
	
	
	/**
	 * 根据参数实体中不为空的属性查询实体数量<br>
	 * 逻辑查询
	 * @author Horgn黄小锤
	 * @date 2019年1月23日 下午1:48:34
	 * @param entity 参数实体
	 * @return
	 */
	public int findCount(T entity);
	
	
	/**
	 * 查询所有记录<br>
	 * 逻辑查询
	 * @author Horgn黄小锤
	 * @date 2019年2月21日 下午1:36:54
	 * @return
	 */
	public int findAllCount();
	
	
	/**
	 * 判断参数实体是否已存在，如果已存在返回true<br>
	 * 注意：<br>
	 * 1、具体判断方式请在自己的Dao实现类中覆盖重写，实现判断逻辑<br>
	 * 2、可复制 BaseDaoImpl 中 checkExist 方法中被注释的代码到新实现类中再修改需要判断的属性<br>
	 * 3、在自己的Dao实现类中，最后返回的应该是 false, 而不是 super.checkExist(entity) ,否则重写的逻辑将无效
	 * 4、（警告：不能在 BaseDaoImpl 中直接写判断逻辑，否则将会影响所有项目）<br>
	 * @author Horgn黄小锤
	 * @date 2019年1月30日 上午9:11:02
	 * @param dataDict
	 * @return
	 */
	boolean checkExist(T entity);
	
	
	/**
	 * 自定义sql查询语句，支持多表关联查询<br>
	 * 注意：<br>
	 * 1、本方法只对Service层开放，不允许Controller层直接调用，防止Sql注入<br>
	 * 2、使用本方法注意对版本号、状态的判断<br>
	 * 3、本方法查询结果使用LinkedHashMap集合，按sql语句中的查询字段的顺序有序排序<br>
	 * 4、如果查找的字段的值为null，则该字段不会出现在查询结果中，请在sql语句中进行处理<br>
	 * @author Horgn黄小锤
	 * @date 2019年1月23日 下午2:45:40
	 * @param sql
	 * @return
	 */
	public List<LinkedHashMap<String, Object>> selectObjBySql(String sql);
	
	
	/**
	 * 自定义sql语句查询记录数量<br>
	 * 注意：<br>
	 * 1、本方法只对Service层开放，不允许Controller层直接调用，防止Sql注入<br>
	 * 2、使用本方法注意对版本号、状态的判断<br>
	 * @author Horgn黄小锤
	 * @date 2019年1月23日 下午3:49:39
	 * @param sql
	 * @return
	 */
	public int countBySql(String sql);
	
	
	/**
	 * 自定义sql语句查询单条字段记录<br>
	 * 注意：<br>
	 * 1、本方法只对Service层开放，不允许Controller层直接调用，防止Sql注入<br>
	 * 2、使用本方法注意对版本号、状态的判断<br>
	 * @author Horgn黄小锤
	 * @date 2019年1月23日 下午6:14:14
	 * @param sql
	 * @return
	 */
	public List<Object> selectOneColumnBySql(String sql);
	
	
	/**
	 * 自定义sql语句查询单个记录<br>
	 * 注意：<br>
	 * 1、本方法只对Service层开放，不允许Controller层直接调用，防止Sql注入<br>
	 * 2、使用本方法注意对版本号、状态的判断<br>
	 * @author Horgn黄小锤
	 * @date 2019年1月25日 上午8:30:53
	 * @param sql
	 * @return
	 */
	public T selectBySql(String sql);
	
	
	/**
	 * 自定义sql查询多个实体记录<br>
	 * 注意：<br>
	 * 1、本方法只对Service层开放，不允许Controller层直接调用，防止Sql注入<br>
	 * 2、使用本方法注意对版本号、状态的判断<br>
	 * @author Horgn黄小锤
	 * @date 2019年1月25日 下午1:34:03
	 * @param sql
	 * @return
	 */
	public List<T> selectListBySql(String sql);
	
	
	/**
	 * 自定义sql语句删除数据<br>
	 * 注意：<br>
	 * 1、本方法只对Service层开放，不允许Controller层直接调用，防止Sql注入<br>
	 * 2、使用本方法注意对版本号、状态的判断<br>
	 * @author Horgn黄小锤
	 * @date 2019年2月4日 下午3:18:47
	 * @param sql
	 * @return
	 */
	public int deleteBySql(String sql);
	
	
	/**
	 * 自定义sql语句更新数据<br>
	 * 注意：<br>
	 * 1、本方法只对Service层开放，不允许Controller层直接调用，防止Sql注入<br>
	 * 2、使用本方法注意对版本号、状态的判断<br>
	 * @author Horgn黄小锤
	 * @date 2019年2月4日 下午3:20:27
	 * @param sql
	 * @return
	 */
	public int updateBySql(String sql);
	
}
