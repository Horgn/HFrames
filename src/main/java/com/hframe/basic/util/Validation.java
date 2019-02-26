package com.hframe.basic.util;

import java.util.Map;



/**
 * 数据验证工具类
 * @author Horgn黄小锤
 * @version v1.0
 * @date 2018-08-25 15:58:01
 */
public class Validation {
	
	/**
	 * 判断指定实体对象的指定属性是否为空，包括父类属性<br>
	 * 注意：<br>
	 * 1、如果参数object为空，则抛出异常<br>
	 * 2、如果实体object中没有参数中指定的属性，则抛出异常<br>
	 * 3、如果实体object中指定的属性的值为null，则抛出异常<br>
	 * 4、长度为 0 的字符串和只有空格的字符串也会认定为null，抛出异常
	 * @author Horgn黄小锤
	 * @param object 实体对象
	 * @param attrs 需要进行验证的参数数组
	 * @throws Exception
	 * @date 2018-08-25 15:58:14
	 */
	public static void notNull(Object object,String... attrs){
		
		if(object == null){
			throw new RuntimeException("[Validation.java] 校验工具类：验证失败，参数不能为空");
		}
		
		if(attrs == null || attrs.length <= 0){
			return;
		}
		
		Map<String, Object> map = ReflectUtils.getEntityFieldVaule(object);
		if(null == map || map.size() <= 0){
			throw new RuntimeException("[VAL001] 校验失败，获取属性值失败");
		}
		
		for(String field : attrs){
			if(StringUtils.isEmpty(field)){
				continue;
			}
			
			if(!map.containsKey(field)){
				throw new RuntimeException("[VAL002] 校验失败，实体中没有该属性（"+field.toUpperCase()+"）");
			}
			
			if(null == map.get(field)){
				throw new RuntimeException("[VAL003] 校验失败，属性值不能为空（"+field.toUpperCase()+"）");
			}
		}
	}
	
	
	


}




