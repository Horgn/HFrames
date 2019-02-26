package com.hframe.basic.util;


/**
 * 自定义断言类，抛出异常而不是错误
 * @author Horgn黄小锤
 * @version v1.0
 * @date 2018-06-06 08:44:33
 */
public final class Assert {
	
	/**
	 * 判断对象是否为空，为空则抛出空指针异常
	 * @author Horgn黄小锤
	 * @param object 对象
	 * @param msg 异常提示
	 * @date 2018-06-06 08:46:56
	 */
	public static void isNull(Object object,String msg){
		
		if(object == null){
			throw new NullPointerException(msg);
		}
	}
	
	/**
	 * 判断字符串是否为空，为空则抛出空指针异常
	 * @author Horgn黄小锤
	 * @param object 对象
	 * @param msg 异常提示
	 * @date 2018-06-06 08:46:56
	 */
	public static void isNull(String string,String msg){
		
		if(StringUtils.isEmpty(string)){
			throw new NullPointerException(msg);
		}
	}
	
	/**
	 * 判断对象是否不为空，不为空则抛出非法参数异常
	 * @author Horgn黄小锤
	 * @param object
	 * @param msg
	 * @date 2018-06-13 11:09:17
	 */
	public static void notNull(Object object,String msg){
		
		if(object != null){
			throw new IllegalArgumentException(msg);
		}
	}
	
	/**
	 * 判断字符串是否不为空，不为空则抛出非法参数异常
	 * @author Horgn黄小锤
	 * @param string
	 * @param msg
	 * @date 2018-06-13 11:09:57
	 */
	public static void notNull(String string,String msg){
		
		if(StringUtils.isNotEmpty(string)){
			throw new IllegalArgumentException(msg);
		}
	}
	
	public static void equal(Object str1,Object str2,String msg){
		
	}
	
	public static void equal(String str1,String str2,String msg){
		
	}
	
	/**
	 * 判断参数是否小于指定数量，小于则抛出非法参数异常
	 * @author Horgn黄小锤
	 * @param number
	 * @param count
	 * @param msg
	 * @date 2018-06-13 10:42:28
	 */
	public static void lessThan(Integer number,Integer count,String msg){
		
		isNull(number, "参数为空");
		isNull(count, "参数为空");
		
		lessThan(Double.valueOf(number), Double.valueOf(count), msg);
	}
	
	/**
	 * 判断参数是否小于指定数量，小于则抛出非法参数异常
	 * @author Horgn黄小锤
	 * @param number
	 * @param count
	 * @param msg
	 * @date 2018-06-13 10:42:28
	 */
	public static void lessThan(Integer number,Double count,String msg){
		
		isNull(number, "参数为空");
		isNull(count, "参数为空");
		
		lessThan(Double.valueOf(number), count, msg);
	}
	
	/**
	 * 判断参数是否小于指定数量，小于则抛出非法参数异常
	 * @author Horgn黄小锤
	 * @param number
	 * @param count
	 * @param msg
	 * @date 2018-06-13 10:42:28
	 */
	public static void lessThan(Double number,Double count,String msg){
		
		isNull(number, "参数为空");
		isNull(count, "参数为空");
		
		if(number < count){
			throw new IllegalArgumentException(msg);
		}
	}
	
	/**
	 * 判断参数是否大于指定数量，大于则抛出非法参数异常
	 * @author Horgn黄小锤
	 * @param number
	 * @param count
	 * @param msg
	 * @date 2018-06-13 10:43:12
	 */
	public static void largerThan(Integer number,Integer count,String msg){
		
		isNull(number, "参数为空");
		isNull(count, "参数为空");
		
		largerThan(Double.valueOf(number), Double.valueOf(count), msg);
	}
	
	/**
	 * 判断参数是否大于指定数量，大于则抛出非法参数异常
	 * @author Horgn黄小锤
	 * @param number
	 * @param count
	 * @param msg
	 * @date 2018-06-13 10:43:12
	 */
	public static void largerThan(Integer number,Double count,String msg){
		
		isNull(number, "参数为空");
		isNull(count, "参数为空");
		
		largerThan(Double.valueOf(number), count, msg);
	}
	
	/**
	 * 判断参数是否大于指定数量，大于则抛出非法参数异常
	 * @author Horgn黄小锤
	 * @param number
	 * @param count
	 * @param msg
	 * @date 2018-06-13 10:43:12
	 */
	public static void largerThan(Double number,Double count,String msg){
		
		isNull(number, "参数为空");
		isNull(count, "参数为空");
		
		if(number > count){
			throw new IllegalArgumentException(msg);
		}
	}
	
	public static void main(String[] args) {
		lessThan(34, 50.0, "less");
	}
	
}