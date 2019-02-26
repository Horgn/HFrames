package com.hframe.basic.base;

import java.util.LinkedHashMap;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import tk.mybatis.mapper.common.Mapper;

/**
 * 通用Mapper基类接口<br>
 * 注意：<br>
 * 1、本接口只需继承，无需实现<br>
 * 2、继承本接口的所有子接口，都需要放在com.hframe.**.mapper包下，否则无法被扫描
 * @author Horgn黄小锤
 * @date 2019年1月23日 下午12:44:31
 * @version V1.0
 */
public interface BaseMapper<T extends BaseDTO> extends Mapper<T>{
	
	/**
	 * 自定义sql查询语句查询多条记录，支持多表关联查询<br>
	 * 注意：<br>
	 * 1、本方法查询结果使用LinkedHashMap集合，按sql语句中的查询字段的顺序有序排序<br>
	 * 2、如果查找的字段的值为null，则该字段不会出现在查询结果中，请在sql语句中进行处理<br>
	 * @author Horgn黄小锤
	 * @date 2019年1月23日 下午2:45:40
	 * @param sql
	 * @return
	 */
	@Select("${sql}")
	public List<LinkedHashMap<String, Object>> selectObjBySql(@Param("sql") String sql);
	
	/**
	 * 自定义sql语句查询记录数量<br>
	 * @author Horgn黄小锤
	 * @date 2019年1月23日 下午3:49:39
	 * @param sql
	 * @return
	 */
	@Select("${sql}")
	public int countBySql(@Param("sql") String sql);
	
	/**
	 * 自定义sql语句查询单条字段记录<br>
	 * @author Horgn黄小锤
	 * @date 2019年1月23日 下午6:14:14
	 * @param sql
	 * @return
	 */
	@Select("${sql}")
	public List<Object> selectOneColumnBySql(@Param("sql") String sql);
	
	/**
	 * 自定义sql语句查询单个记录<br>
	 * @author Horgn黄小锤
	 * @date 2019年1月25日 上午8:30:53
	 * @param sql
	 * @return
	 */
	@Select("${sql}")
	public T selectBySql(@Param("sql") String sql);
	
	/**
	 * 自定义sql查询多个实体记录
	 * @author Horgn黄小锤
	 * @date 2019年1月25日 下午1:34:03
	 * @param sql
	 * @return
	 */
	@Select("${sql}")
	public List<T> selectListBySql(@Param("sql") String sql);
	
	/**
	 * 自定义sql语句删除数据
	 * @author Horgn黄小锤
	 * @date 2019年2月4日 下午3:18:47
	 * @param sql
	 * @return
	 */
	@Delete("${sql}")
	public int deleteBySql(@Param("sql") String sql);
	
	/**
	 * 自定义sql语句更新数据
	 * @author Horgn黄小锤
	 * @date 2019年2月4日 下午3:20:27
	 * @param sql
	 * @return
	 */
	@Update("${sql}")
	public int updateBySql(@Param("sql") String sql);
	

}
