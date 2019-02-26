package com.hframe.basic.util;

import javax.persistence.Table;
import javax.persistence.Transient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
/**
 * 反射工具类
 * @author Horgn黄小锤
 * @date 2019年1月25日 上午8:52:08
 * @version V1.0
 */
public class ReflectUtils {
	
	/**反射工具类日志属性*/
	private static Logger reflectLogger = LoggerFactory.getLogger(ReflectUtils.class);
	
	/**
	 * 反射赋值，将Map集合中的key-value值反射到实体参数中对应的属性中，设置属性值<br>
     * 注意：<br>
     * 1、包含所有属性，无论私有还是公有属性<br>
     * 2、不包含序列化接口的 serialVersionUID 属性<br>
     * 3、不包含带有 JPA 的 @Transient 注解的属性<br>
     * 4、如果父子类中出现重复的属性名，只赋予子类的属性值
	 * @author Horgn黄小锤
	 * @date 2019年1月29日 下午12:48:49
	 * @param object
	 * @param values
	 */
	public static Object reflectEntity(Object object, Map<String, Object> values){
		
		if(null == object || null == values || values.size() <= 0){
			return object;
		}
		
		List<String> fieldNames = getEntityFieldNames(object.getClass());
		
		for(Map.Entry<String, Object> entry : values.entrySet()){
			if(!fieldNames.contains(entry.getKey())){
				continue;
			}
			
			reflectEntity(object.getClass(), object, entry.getKey(), entry.getValue());
		}
		
		return object;
	}
	/**递归反射赋值*/
	private static void reflectEntity(Class<?> clazz, Object object, String name, Object value){
		
		if(null == clazz || null == object || null == value || null == name || name.trim().length() <= 0){
			return;
		}
		
		try {
			Field field = clazz.getDeclaredField(name);
			field.setAccessible(true);
			field.set(object, value);
			
		} catch (Exception e) {
			reflectEntity(clazz.getSuperclass(), object, name, value);
		}
	}
 
    /**
     * 获取类上的 @Table 注解中的表名称
     * @author Horgn黄小锤
     * @date 2019年1月25日 上午8:55:31
     * @param clazz
     * @return
     */
    public static String getTableName(Class<?> clazz){
    	
    	Table annotation = clazz.getAnnotation(Table.class);
    	if(null == annotation){
    		throw new RuntimeException("[REF030] 获取 TableName 失败");
    	}
    	
    	return annotation.name();
    }
    
    /**
     * 获取参数类中的所有属性，包括所有父级Class的所有属性<br>
     * 注意：<br>
     * 1、包含所有属性，无论私有还是公有属性<br>
     * 2、不包含序列化接口的 serialVersionUID 属性<br>
     * 3、不包含带有 JPA 的 @Transient 注解的属性<br>
     * 4、如果父子类中出现重复的属性名，只返回子类的属性
     * @author Horgn黄小锤
     * @date 2019年1月29日 上午8:29:31
     * @param clazz
     * @return
     */
    public static List<Field> getEntityFields(Class<?> clazz){
    	
    	List<Field> fields = new ArrayList<>();
    	if(null == clazz){
    		return fields;
    	}
    	
    	Class<?> entityClass = clazz;
    	while(null != entityClass){
    		Field[] declaredFields = entityClass.getDeclaredFields();
    		
    		for(Field field : declaredFields){
    			if(!"serialVersionUID".equals(field.getName()) && !fields.contains(field) && null == field.getAnnotation(Transient.class)){
    				fields.add(field);
    			}
    		}
    		
    		entityClass = entityClass.getSuperclass();
    	}
    	return fields;
    }
    
    /**
     * 获取参数类中的所有属性名称，包括所有父级Class的所有属性名称<br>
     * 注意：<br>
     * 1、包含所有属性，无论私有还是公有属性<br>
     * 2、不包含序列化接口的 serialVersionUID 属性<br>
     * 3、不包含带有 JPA 的 @Transient 注解的属性<br>
     * 4、如果父子类中出现重复的属性名，只返回子类的属性
     * @author Horgn黄小锤
     * @date 2019年1月29日 上午10:32:54
     * @param clazz
     * @return
     */
    public static List<String> getEntityFieldNames(Class<?> clazz){
    	
    	List<String> names = new ArrayList<>();
    	if(null == clazz){
    		return names;
    	}
    	
    	Class<?> entityClass = clazz;
    	while(null != entityClass){
    		Field[] declaredFields = entityClass.getDeclaredFields();
    		
    		for(Field field : declaredFields){
    			if(!"serialVersionUID".equals(field.getName()) && !names.contains(field) && null == field.getAnnotation(Transient.class)){
    				names.add(field.getName());
    			}
    		}
    		
    		entityClass = entityClass.getSuperclass();
    	}
    	return names;
    }
    
    /**
     * 获取参数实体所有属性名称，不包含父级 Class 的属性<br>
     * 注意：<br>
     * 1、包含所有属性，无论私有还是公有属性<br>
     * 2、不包含序列化接口的 serialVersionUID 属性<br>
     * 3、不包含带有 JPA 的 @Transient 注解的属性
     * @author Horgn黄小锤
     * @date 2019年1月29日 上午10:51:04
     * @param clazz
     * @return
     */
    public static List<String> getSubclassFields(Class<?> clazz){
    	
    	List<String> names = new ArrayList<>();
    	if(null == clazz){
    		return names;
    	}
    	
    	Field[] declaredFields = clazz.getDeclaredFields();
		for(Field field : declaredFields){
			
			if(!"serialVersionUID".equals(field.getName()) && !names.contains(field) && null == field.getAnnotation(Transient.class)){
				names.add(field.getName());
			}
		}
    	return names;
    }
    
    /**
     * 获取参数实体的父级 Class 的所有属性名称<br>
     * 注意：<br>
     * 1、包含所有属性，无论私有还是公有属性<br>
     * 2、不包含序列化接口的 serialVersionUID 属性<br>
     * 3、不包含带有 JPA 的 @Transient 注解的属性<br>
     * 4、只能获取上一父级的属性，不能获取上上级及以上的属性
     * @author Horgn黄小锤
     * @date 2019年1月29日 上午11:08:52
     * @param clazz
     * @return
     */
    public static List<String> getSuperclassFields(Class<?> clazz){
    	
    	List<String> names = new ArrayList<>();
    	Class<?> superclass = clazz.getSuperclass();
    	
    	if(null == clazz || null == superclass){
    		return names;
    	}
    	
    	Field[] declaredFields = superclass.getDeclaredFields();
    	
		for(Field field : declaredFields){
			if(!"serialVersionUID".equals(field.getName()) && !names.contains(field) && null == field.getAnnotation(Transient.class)){
				names.add(field.getName());
			}
		}
		
    	return names;
    }
    
    /**
     * 获取参数实体中所有属性的名称和对应的属性值，包括所有父级Class的所有属性<br>
     * 注意：<br>
     * 1、包含所有属性，无论私有还是公有属性<br>
     * 2、不包含序列化接口的 serialVersionUID 属性<br>
     * 3、不包含带有 JPA 的 @Transient 注解的属性<br>
     * 4、长度为 0 或全空格的 String 字符串也被认定为 null，返回 null 值<br>
     * 5、如果属性是 String 字符串，则将会调用 .trim() 方法去除前后空白<br>
     * 6、如果父子类中出现重复的属性名，只返回子类的属性
     * @author Horgn黄小锤
     * @date 2019年1月29日 上午9:27:28
     * @param clazz
     * @param object
     * @return
     */
    public static Map<String, Object> getEntityFieldVaule(Object object){
    	
    	Map<String, Object> map = new HashMap<>();
    	if( null == object){
    		return map;
    	}
    	Class<? extends Object> entityClass = object.getClass();
    	
    	try {
	    	while(null != entityClass){
	    		Field[] declaredFields = entityClass.getDeclaredFields();
	    		
	    		for(Field field : declaredFields){
	    			String name = field.getName();
	    			if("serialVersionUID".equals(name) || null != field.getAnnotation(Transient.class) || map.containsKey(name)){
	    				continue;
	    			}
					
						field.setAccessible(true);
						Object value = field.get(object);
						if(null == value){
							map.put(name, value);
							continue;
						}
						
						Class<? extends Object> valueClass = value.getClass();
						if("java.lang.String".equals(valueClass.getName())){
							String strValue = value.toString().trim();
							
							if(strValue.length() <= 0){
								map.put(name, null);
								continue;
								
							}else{
								map.put(name, strValue);
								continue;
							}
						}
						
						map.put(name, value);
	    		}
	    		entityClass = entityClass.getSuperclass();
	    		
	    	}
    	} catch (Exception e) {
			
			reflectLogger.error("[REF075] 反射实体赋值失败：" + e.getMessage());
			throw new RuntimeException("[REF001] 获取属性值异常");
		}
    	return map;
    }
    
    
}




