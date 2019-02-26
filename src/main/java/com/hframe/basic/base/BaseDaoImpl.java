package com.hframe.basic.base;

import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.hframe.basic.common.PageResult;
import com.hframe.basic.common.SysConstants.RecordStatus;
import com.hframe.basic.root.RootUtils;
import com.hframe.basic.util.ReflectUtils;
import com.hframe.basic.util.StringUtils;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;


/**
 * 通用Dao实现类<br>
 * 注意：<br>
 * 1、建议不要在dao层开启事务处理<br>
 * 2、本接口中的删除操作为逻辑删除，对应字段为iState，删除后值为0<br>
 * 3、物理查询（查询所有状态的记录）、逻辑查询（只查询状态为1的记录）<br>
 * 4、Dao层只对本类Service层开放，不允许Controller层直接使用<br>
 * 5、请在项目Dao实现类中重新实现checkExist(T entity)方法，自定义判断逻辑<br>
 * 6、建议在项目Dao实现类中，直接调用基类的方法（super.xxx()），而不是调用项目Mapper中的原始方法
 * @author Horgn黄小锤
 * @date 2019年1月23日 下午2:27:03
 * @version V1.0
 */
public class BaseDaoImpl<T extends BaseDTO> implements BaseDao<T>{
	
	@Value("${hframe.exception.print}")
	private Boolean openExInfo;
	
	
	@SuppressWarnings("unchecked")
	private Class <T> entityClass = (Class <T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]; 
	
	private Logger logger = LoggerFactory.getLogger(BaseDaoImpl.class);
	protected BaseMapper<T> mapper;
	
	
	@Override
	public boolean checkExist(T entity){
//		if(null == entity){
//			throw new RuntimeException("实体参数不能为空");
//		}
//		tk.mybatis.mapper.entity.Example example = new tk.mybatis.mapper.entity.Example(entity.getClass());
//		tk.mybatis.mapper.entity.Example.Criteria criteria = example.createCriteria();
//		//TODO 具体判断方式请在继承类中实现，可复制当前方法中的代码到新实现类中再修改（警告：这是是基类接口，不能在此写判断逻辑）
//		//criteria.andEqualTo("xxx", entity.getXxx());
//		
//		if (com.hframe.basic.util.StringUtils.isNotEmpty(entity.getsId())) {//如果是更新操作，则将跳过与自身的对比
//			tk.mybatis.mapper.entity.Example.Criteria criteria2 = example.createCriteria();
//			criteria2.andNotEqualTo("sId", entity.getsId());
//			example.and(criteria2);
//		}
//		int count = 0;
//		try {
//			count = findCount(example);
//		} catch (Exception e) {
//			doExceptionMsg(e, "数据重复性校验失败");
//		}
//		if(count > 0){
//			return true;
//		}
//		//注意：在项目Dao实现类中，最后返回的应该是 false, 而不是 super.checkExist(entity) ,否则重写的逻辑或将无效
		return false;
	}
	
	
	@Override
	public T findById(String sId) {
		
		if(StringUtils.isEmpty(sId)){
			throw new RuntimeException("主键不能为空");
		}
		
		T entity = null;
		try {
			entity = mapper.selectByPrimaryKey(sId);
		} catch (Exception e) {
			doExceptionMsg(e, "根据主键查询实体失败");
		}
		
		return entity;
	}
	

	@Override
	public T find(T entity) {
		
		if(null == entity){
			throw new RuntimeException("实体参数不能为空");
		}
		
		entity.setiState(RecordStatus.Normal);//查询不为删除状态的数据
		T find = null;
		try {
			find = mapper.selectOne(entity);
		} catch (Exception e) {
			doExceptionMsg(e, "根据实体属性查询实体失败");
		}
		
		return find;
	}
	

	@Override
	public List<T> findList(Example example) {
		
		if(null == example){
			throw new RuntimeException("实体参数不能为空");
		}
		
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("iState", RecordStatus.Normal);//查询不为删除状态的数据
		example.and(criteria);
		List<T> list = null;
		
		try {
			list = mapper.selectByExample(example);
		} catch (Exception e) {
			doExceptionMsg(e, "查询数据失败");
		}
		
		return list;
	}
	

	@Override
	public List<T> findList(T entity){
		
		if(null == entity){
			throw new RuntimeException("实体参数不能为空");
		}
		
		entity.setiState(RecordStatus.Normal);//查询不为删除状态的数据
		List<T> list = null;
		try {
			list = mapper.select(entity);
		} catch (Exception e) {
			doExceptionMsg(e, "查询数据失败");
		}
		
		return list;
	}
	
	
	@Override
	public List<T> findAll() {
		
		Example example = new Example(entityClass);
		example.createCriteria().andEqualTo("iState", RecordStatus.Normal);//查询不为删除状态的数据
		example.setOrderByClause(" dCreateDate DESC ");
		
		List<T> list = null;
		try {
			list = mapper.selectByExample(example);
		} catch (Exception e) {
			doExceptionMsg(e, "查询所有数据失败");
		}
		
		return list;
	}
	

	@Override
	public PageResult findByPage(Example example, PageResult page) {
		
		if(null == example || null == page){
			throw new RuntimeException("实体参数不能为空");
		}
		
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("iState", RecordStatus.Normal);//查询不为删除状态的数据
		example.and(criteria);
		
		try {
			//排序
			if(StringUtils.isNotEmpty(page.getOrderField(), page.getOrderDirection())){
				example.setOrderByClause(page.getOrderField()+" "+page.getOrderDirection());
			}else if(StringUtils.isEmpty(example.getOrderByClause())){
				example.setOrderByClause(" dCreateDate DESC ");
			}
			
			//模糊查询
			if(StringUtils.isNotEmpty(page.getSearchField(), page.getSearchValue())){
				Criteria criteria2 = example.createCriteria();
				criteria2.andLike(page.getSearchField(), "%"+page.getSearchValue()+"%");
				example.and(criteria2);
			}
			
			page.setRowCount(findCount(example));
			page.setData(mapper.selectByExampleAndRowBounds(example,
					new RowBounds(page.getStartIndex(), page.getPageSize())));
			
		} catch (Exception e) {
			doExceptionMsg(e, "分页查询失败");
		}
		
		return page;
	}
	

	@Override
	public PageResult findByPage(T entity, PageResult page){
		
		if(null == entity || null == page){
			throw new RuntimeException("实体参数不能为空");
		}
		
		entity.setiState(RecordStatus.Normal);//查询不为删除状态的数据
		try {
			page.setRowCount(findCount(entity));
			page.setData(mapper.selectByRowBounds(entity, new RowBounds(page.getStartIndex(), page.getPageSize())));
			
		} catch (Exception e) {
			doExceptionMsg(e, "分页查询失败");
		}
		
		return page;
	}
	
	
	@Override
	public int update(T entity) {
		
		if(null == entity){
			throw new RuntimeException("实体参数不能为空");
		}
		
		if(StringUtils.isEmpty(entity.getsId())){
			throw new RuntimeException("更新失败：主键不能为空");
		}
		
		if(null == entity.getiVersion()){
			throw new RuntimeException("更新失败：版本号不能为空");
		}
		
		int version = getVersion(entity.getsId());
		if(entity.getiVersion() != version){
			throw new RuntimeException("更新失败：数据版本不一致");
		}
		
		if(checkExist(entity)){
			throw new RuntimeException("更新失败：已存在相同的数据");
		}
		
		entity.setsModifieder(RootUtils.getLoginUserId());
		entity.setdModifiedDate(new Date());
		entity.setiVersion(entity.getiVersion()+1);
		int count = 0;
		
		try {
			count = mapper.updateByPrimaryKey(entity);
		} catch (Exception e) {
			doExceptionMsg(e, "执行更新失败");
		}
		
		if(count <= 0){
			throw new RuntimeException("更新失败");
		}
		return count;
	}
	

	@Override
	public int updateSelective(T entity) {
		
		if(null == entity){
			throw new RuntimeException("实体参数不能为空");
		}
		
		if(StringUtils.isEmpty(entity.getsId())){
			throw new RuntimeException("更新失败：主键号不能为空");
		}
		
		if(null == entity.getiVersion()){
			throw new RuntimeException("更新失败：版本号不能为空");
		}
		
		int version = getVersion(entity.getsId());
		if(entity.getiVersion() != version){
			throw new RuntimeException("更新失败：数据版本不一致");
		}
		
		if(checkExist(entity)){
			throw new RuntimeException("更新失败：已存在相同的数据");
		}
		
		entity.setsModifieder(RootUtils.getLoginUserId());
		entity.setdModifiedDate(new Date());
		entity.setiVersion(entity.getiVersion()+1);
		entity.setsCreator(null);
		entity.setdCreateDate(null);
		
		int count = 0;
		try {
			count = mapper.updateByPrimaryKeySelective(entity);
		} catch (Exception e) {
			doExceptionMsg(e, "执行更新记录失败");
		}
		
		if(count <= 0){
			throw new RuntimeException("更新记录失败");
		}
		return count;
	}
	

	@Override
	public String save(T entity) {
		
		if(null == entity){
			throw new RuntimeException("实体参数不能为空");
		}
		
		if(checkExist(entity)){
			throw new RuntimeException("添加失败：已存在相同的数据");
		}
		
		if(StringUtils.isEmpty(entity.getsId())){//生成主键
			entity.setsId(StringUtils.generateUUID(8));
		}
		
		if(null == entity.getdCreateDate()){
			entity.setdCreateDate(new Date());
		}
		
		//创建人
		entity.setsCreator(RootUtils.getLoginUserId());
		int count = 0;
		
		try {
			count = mapper.insertSelective(entity);
		} catch (Exception e) {
			doExceptionMsg(e, "执行添加记录失败");
		}
		
		if(count <= 0){
			throw new RuntimeException("添加记录失败");
		}
		return entity.getsId();
	}
	

	@Override
	public int delete(String sId) {
		
		if(StringUtils.isEmpty(sId)){
			throw new RuntimeException("主键不能为空");
		}
		
		T entity = null;
		try {
			entity = mapper.selectByPrimaryKey(sId);
		} catch (Exception e) {
			doExceptionMsg(e, "获取所删除记录失败");
		}
		
		if(null == entity){
			throw new RuntimeException("删除记录失败：记录不存在");
		}
		
		if(RecordStatus.Delete == entity.getiState()){
			throw new RuntimeException("记录已删除");
		}
		
		entity.setsId(sId);
		entity.setiState(RecordStatus.Delete);
		entity.setiVersion(entity.getiVersion()+1);
		entity.setsModifieder(RootUtils.getLoginUserId());
		entity.setdModifiedDate(new Date());
		
		int count = 0;
		try {
			count = mapper.updateByPrimaryKeySelective((T) entity);
		} catch (Exception e) {
			doExceptionMsg(e, "执行删除记录失败");
		}
		
		if(count <= 0){
			throw new RuntimeException("删除记录失败");
		}
		return count;
	}
	

	@Override
	public int findCount(Example example) {
		
		if(null == example){
			throw new RuntimeException("实体参数不能为空");
		}
		
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("iState", RecordStatus.Normal);//查询不为删除状态的数据
		example.and(criteria);
		int count = 0;
		
		try {
			count = mapper.selectCountByExample(example);
		} catch (Exception e) {
			doExceptionMsg(e, "查询记录数量失败");
		}
		
		return count;
	}
	

	@Override
	public int findCount(T entity) {
		
		if(null == entity){
			throw new RuntimeException("实体参数不能为空");
		}
		
		entity.setiState(RecordStatus.Normal);//查询不为删除状态的实体数量
		int count = 0;
		try {
			count = mapper.selectCount(entity);
		} catch (Exception e) {
			doExceptionMsg(e, "查询记录数量失败");
		}
		
		return count;
	}
	
	
	@Override
	public int findAllCount(){
		
		String sql = "SELECT COUNT(1) FROM " + getTableName() + " WHERE iState=1";
		int count = 0;
		try {
			count = mapper.countBySql(sql);
		} catch (Exception e) {
			doExceptionMsg(e, "查询全部记录数量失败");
		}
		return count;
	}
	

	@Override
	public List<LinkedHashMap<String, Object>> selectObjBySql(String sql) {
		
		if(StringUtils.isEmpty(sql)){
			throw new RuntimeException("参数Sql语句不能为空");
		}
		
		List<LinkedHashMap<String, Object>> list = null;
		try {
			list = mapper.selectObjBySql(sql);
		} catch (Exception e) {
			doExceptionMsg(e, "自定义Sql语句查询失败");
		}
		
		return list;
	}
	

	@Override
	public int countBySql(String sql) {
		
		if(StringUtils.isEmpty(sql)){
			throw new RuntimeException("参数Sql语句不能为空");
		}
		
		int count = 0;
		try {
			count = mapper.countBySql(sql);
		} catch (Exception e) {
			doExceptionMsg(e, "自定义Sql语句查询失败");
		}
		
		return count;
	}
	

	@Override
	public List<Object> selectOneColumnBySql(String sql) {
		
		if(StringUtils.isEmpty(sql)){
			throw new RuntimeException("参数Sql语句不能为空");
		}
		
		List<Object> list = null;
		try {
			list = mapper.selectOneColumnBySql(sql);
		} catch (Exception e) {
			doExceptionMsg(e, "自定义Sql语句查询失败");
		}
		
		return list;
	}
	

	@Override
	public T selectBySql(String sql) {
		
		if(StringUtils.isEmpty(sql)){
			throw new RuntimeException("参数Sql语句不能为空");
		}
		
		T entity = null;
		try {
			entity = mapper.selectBySql(sql);
		} catch (Exception e) {
			doExceptionMsg(e, "自定义Sql语句查询失败");
		}
		
		return entity;
	}
	
	
	@Override
	public List<T> selectListBySql(String sql) {
		
		if(StringUtils.isEmpty(sql)){
			throw new RuntimeException("参数Sql语句不能为空");
		}
		
		List<T> list = null;
		try {
			list = mapper.selectListBySql(sql);
		} catch (Exception e) {
			doExceptionMsg(e, "自定义Sql语句查询失败");
		}
		
		return list;
	}
	
	
	@Override
	public int deleteBySql(String sql) {
		
		if(StringUtils.isEmpty(sql)){
			throw new RuntimeException("参数Sql语句不能为空");
		}
		
		int count = 0;
		try {
			count = mapper.deleteBySql(sql);
		} catch (Exception e) {
			doExceptionMsg(e, "自定义Sql语句删除失败");
		}
		
		return count;
	}

	
	@Override
	public int updateBySql(String sql) {
		
		if(StringUtils.isEmpty(sql)){
			throw new RuntimeException("参数Sql语句不能为空");
		}
		
		int count = 0;
		try {
			count = mapper.updateBySql(sql);
		} catch (Exception e) {
			doExceptionMsg(e, "自定义Sql语句更新失败");
		}
		
		return count;
	}
	

	/**
	 * 根据主键从数据库获取数据版本号<br>
	 * 注意：本方法只限本Dao中的方法调用，用于更新数据时的版本号判断
	 * @author Horgn黄小锤
	 * @date 2019年1月25日 上午8:44:26
	 * @return
	 */
	protected final int getVersion(String sId){
		
		if(StringUtils.isEmpty(sId)){
			throw new RuntimeException("主键不能为空");
		}
		
		String tableName = getTableName();
		String sql = " SELECT iVersion FROM " + tableName + " WHERE sId='" + sId + "' AND iState=1  ";
		T entity = null;
		
		try {
			entity = mapper.selectBySql(sql);
		} catch (Exception e) {
			doExceptionMsg(e, "Sql语句获取版本号失败");
		}
		
		if(null == entity || null == entity.getiVersion()){
			throw new RuntimeException("获取版本号失败");
		}
		
		return entity.getiVersion();
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
	
	
	/**
	 * 异常处理方法<br>
	 * 打印异常信息及日志输出，可在配置文件中配置是否打印异常信息
	 * @author Horgn黄小锤
	 * @date 2019年2月21日 上午9:08:06
	 * @param e 异常
	 * @param msg 响应信息
	 */
	protected final void doExceptionMsg(Exception e, String msg){
		
		if(StringUtils.isEmpty(msg)){
			msg = "出现异常咯";
		}
		
		if(null == e){
			throw new RuntimeException(msg);
		}
		
		if(null != openExInfo && true == openExInfo){
			e.printStackTrace();
		}
		
		logger.error(msg + " -- " + e.getMessage());
		throw new RuntimeException(msg);
	}

}
