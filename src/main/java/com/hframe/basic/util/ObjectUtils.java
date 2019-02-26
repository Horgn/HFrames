package com.hframe.basic.util;

import java.util.Collection;
import java.util.Map;


/**
 * 对象工具类
 * @author Horgn黄小锤
 * @date 2019年1月25日 下午12:50:07
 * @version V1.0
 */
public class ObjectUtils {
	
	/**
	 * 判断参数对象数组是否为空<br>
	 * 只要有一个为空，则返回true
	 * @author Horgn黄小锤
	 * @date 2019年2月1日 上午11:02:34
	 * @param objects
	 * @return
	 */
	public static boolean isNull(Object... objects){
		
		if(null == objects || objects.length <= 0){
			return true;
		}
		
		for(Object object : objects){
			if(null == object){
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 判断参数数组是否不为空<br>
	 * 全部不为空，则返回true<br>
	 * 只要有一个为空，则返回false;
	 * @author Horgn黄小锤
	 * @date 2019年2月1日 上午11:03:24
	 * @param objects
	 * @return
	 */
	public static boolean isNotNull(Object... objects){
		
		if(null == objects || objects.length <= 0){
			return false;
		}
		
		for(Object object : objects){
			if(null == object){
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * 在控制台遍历集合
	 * @author Horgn黄小锤
	 * @date 2019年2月1日 上午11:12:34
	 * @param collection
	 */
	public static void showList(Collection<?> collection){
		
		if(null == collection || collection.size() <= 0){
			System.out.println("Collection[ ];\n");
			return;
		}
		
		System.out.println("List Show Start [ ");
		System.out.println("\t Length : " + collection.size());
		collection.forEach((obj) -> System.out.println("\t { " + obj.toString() + " }"));
		System.out.println("] Show End; ====;\n");
	}
	
	/**
	 * 在控制台遍历集合
	 * @author Horgn黄小锤
	 * @date 2019年2月1日 上午11:19:30
	 * @param map
	 */
	public static void showMap(Map<?, ?> map){
		
		if(null == map || map.size() <= 0){
			System.out.println("Map[ ];\n");
			return;
		}
		
		System.out.println("Map Show Start [");
		System.out.println("\t Length : " + map.size());
		for(Map.Entry<?, ?> entry : map.entrySet()){
			System.out.println("\t { Key : " + entry.getKey().toString() + ", Value : " + entry.getValue().toString() + " }");
		}
		
		System.out.println("] Show End; ====;\n");
	}
	
	/**
	 * 在控制台遍历数组
	 * @author Horgn黄小锤
	 * @date 2019年2月1日 上午11:26:05
	 * @param objects
	 */
	public static void showArray(Object[] objects){
		
		if(null == objects || objects.length <= 0){
			System.out.println("Arrays[ ];\n");
			return;
		}
		
		System.out.println("Arrays Show Start [ ");
		System.out.println("\t Length : " + objects.length);
		
		for(Object object : objects){
			System.out.println("\t { " + object.toString() + " }");
		}
		
		System.out.println("] Show End; ====;\n");
	}
	
	/**
	 * 判断list集合是否为空，或长度为0，是则返回true
	 * @author Horgn黄小锤
	 * @date 2019年2月7日 上午10:55:57
	 * @param list
	 * @return
	 */
	public static Boolean isNullList(Collection<?> list){
		
		if(null == list || list.size() <= 0){
			return true;
		}
		
		return false;
	}
	
	/**
	 * 判断list集合是否不为空，或长度不为0，是则返回true
	 * @author Horgn黄小锤
	 * @date 2019年2月7日 上午10:57:17
	 * @param list
	 * @return
	 */
	public static Boolean isNotNullList(Collection<?> list){
		
		if(null != list && list.size() > 0){
			return true;
		}
		
		return false;
	}
	
}
