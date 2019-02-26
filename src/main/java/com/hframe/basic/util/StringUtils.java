package com.hframe.basic.util;

import java.util.UUID;

/**
 * 字符串工具类
 * @author Horgn黄小锤
 * @version v1.0
 * @date 2018-04-13 16:46:46
 */
public class StringUtils {
	
	/**
	 * 判断字符串是否为空
	 * @author Horgn黄小锤
	 * @param string
	 * @return 如果为空则返回true
	 * @date 2018-04-13 16:50:12
	 */
	public static boolean isEmpty(String string){
		
		if(string == null || string.trim().equals("") || string.trim().length() <= 0){
			return true;
		}
		
		return false;
	}
	
	/**
	 * 判断多个字符串变量是否为空<br>
	 * 只要有其中一个为空，则返回true
	 * @author Horgn黄小锤
	 * @param strs
	 * @return 
	 * @date 2018-10-23 11:05:56
	 */
	public static boolean isEmpty(String... strs){
		
		if(strs == null || strs.length <= 0){
			return true;
		}
		
		for(String string : strs){
			if(isEmpty(string)){
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 判断字符串是否不为空
	 * @author Horgn黄小锤
	 * @param string
	 * @return 如果不为空则返回true
	 * @date 2018-04-13 16:50:23
	 */
	public static boolean isNotEmpty(String string){
		return !isEmpty(string);
	}
	
	/**
	 * 判断多个字符串是否为空<br>
	 * 1、如果都不为空，则返回true<br>
	 * 2、只要有一个为空，则返回false
	 * @author Horgn黄小锤
	 * @date 2019年2月1日 上午10:48:39
	 * @param strings
	 * @return
	 */
	public static boolean isNotEmpty(String... strings){
		
		if(null == strings || strings.length <= 0){
			return false;
		}
		
		for(String string : strings){
			if(isEmpty(string)){
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * 首字母大写
	 * @author Horgn黄小锤
	 * @param string
	 * @return
	 * @date 2018-04-13 16:51:31
	 */
	public static String upperPreString(String string){
		
		Assert.isNull(string, "字符串为空");
		return string.substring(0,1).toUpperCase() + string.substring(1);
	}
	
	/**
	 * 首字母小写
	 * @author Horgn黄小锤
	 * @param string
	 * @return
	 * @date 2018-04-13 16:53:30
	 */
	public static String lowerPreString(String string){
		
		Assert.isNull(string, "字符串为空");
		return string.substring(0,1).toLowerCase() + string.substring(1);
	}
	
	/**
	 * 根据数据库表/字段名称获取java属性名称（驼峰命名法）
	 * @author Horgn黄小锤
	 * @param tableName 如：T_USER_INFO，返回：tUserInfo
	 * @return 
	 * @date 2018-05-30 12:48:25
	 */
	public static String getJavaName(String tableName){
		
		Assert.isNull(tableName, "表名或字段名称为空");
		String name = "";
		String[] ns = tableName.toLowerCase().split("_");
		
		for(String s:ns){
			if(isNotEmpty(s)){
				name += upperPreString(s);
			}
		}
		
		return lowerPreString(name);
	}
	
	/**
	 * 将java中的驼峰字符串转换成数据库字段/表名，以下划线隔开<br>
	 * -----注：数字也会被下划线隔开
	 * @author Horgn黄小锤
	 * @param javaName 如：userNameInfo2，返回:USER_NAME_INFO_2
	 * @return
	 * @date 2018-10-25 18:01:29
	 */
	public static String getTableName(String javaName){
		
		Assert.isNull(javaName, "实体名或属性名为空");
		StringBuilder name = new StringBuilder();
		char[] chs = javaName.toCharArray();
		
		for(int i=0;i<chs.length;i++){
			if(isUpper(String.valueOf(chs[i]))){
				
				if(i == 0){
					name.append(String.valueOf(chs[i]));
				}else{
					name.append("_"+chs[i]);
				}
				
			}else{
				name.append(String.valueOf(chs[i]).toUpperCase());
			}
		}
		return name.toString();
	}
	
	/**
	 * 判断字符串中的每个字符是否全部是大写，如果是，返回true（可包含数字、下划线）
	 * @author Horgn黄小锤
	 * @param str
	 * @return
	 * @date 2018-10-25 17:53:37
	 */
	public static boolean isUpper(String str){
		
	    for(int i=0; i<str.length(); i++){
	        char c = str.charAt(i);
	        
	        if(c >= 97 && c <= 122) {
	            return false;
	        }
	    }
	    
	    return true;
	}
	
	/**
	 * 移除字符串中所有非单词字符（包括下划线_）
	 * @author Horgn黄小锤
	 * @param resString
	 * @return
	 * @date 2018-06-13 10:10:53
	 */
	public static String format(String resString){
		return format(resString,null);
	}
	
	/**
	 * 字符串格式转换，将非单词字符（包括下划线_）转换成指定字符
	 * @author Horgn黄小锤
	 * @param resString 原字符串
	 * @param symbol 需要转换成的字符
	 * @return
	 * @date 2018-06-13 09:46:44
	 */
	public static String format(String resString,String symbol){
		return format(resString, null,symbol);
	}
	
	/**
	 * 字符串格式转换，将字符串中指定字符转换成指定字符</br>
	 * 如果arr参数为空，则将所有非单词字符（包括下划线_）转换面指定字符
	 * @author Horgn黄小锤
	 * @param resString 原字符串
	 * @param arr 需要配置的字符
	 * @param symbol 需要转换成的字符
	 * @return
	 * @date 2018-06-13 09:48:38
	 */
	public static String format(String resString,String[] arr,String symbol){
		
		Assert.isNull(resString, "字符串为空");
		String string = null;
		symbol = isNotEmpty(symbol)?symbol:"";
		
		if(arr == null || arr.length <= 0) {
			String[] str = resString.split("[\\W|_]");
			
			for(String s:str)
				if(isEmpty(string)){
					string = s;
				}else{
					string += symbol + s;
				}
			
		}else{
			for(String sym:arr){
				int index = 1;
				while (index > 0) {
					index = resString.indexOf(sym);
					
					if (index > 0) {
						String s1 = resString.substring(0, index);
						String s2 = resString.substring(index + 1);
						resString = s1 + symbol + s2;
					} 
				}
			}
			string = resString;
		}
		return string;
	}
	
	/**
	 * 在控制打印数组数据
	 * @author Horgn黄小锤
	 * @param objects
	 * @date 2018-10-23 14:13:56
	 */
	public static void show(Object[] objects){
		
		System.out.print("Object[] : [");
		for(int i=0;i<objects.length;i++){
			if(i != objects.length-1){
				System.out.print(objects[i].toString()+",");
			}else{
				System.out.print(objects[i].toString());
			}
		}
		
		System.out.println("]");
	}
	
	/**
	 * 随机生成32位UUID（已去除“-”）
	 * @author Horgn黄小锤
	 * @return
	 * @date 2018-06-11 15:20:31
	 */
	public static String generateUUID(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	/**
	 * 随机生成32位UUID并获取前len个长度的字符串
	 * @author Horgn黄小锤
	 * @param len 长度（最大值为32，如果超过32则只返回32个长度的字符串）
	 * @return
	 * @date 2018-09-03 14:31:29
	 */
	public static String generateUUID(int len){
		return generateUUID(0,len);
	}
	
	/**
	 * 随机生成32位UUID并获取指定起始位置和指定长度的字符串
	 * @author Horgn黄小锤
	 * @param start 起始位置（0-31）
	 * @param len 长度(1-32)
	 * @return 起始位置到起始位置+len长度的字符串
	 * @date 2018-09-03 14:32:13
	 */
	public static String generateUUID(int start,int len){
		
		if(start < 0 || start >= 32){
			start = 0;
		}
		
		if(len <= 0 || (start + len) >= 32){
			len = 32 - start;
		}
		
		return generateUUID().substring(start, start + len);
	}
	
	/**
	 * 随机生成一个指定长度的UUID字符串
	 * @author Horgn黄小锤
	 * @param len 长度（大于0）
	 * @return
	 * @date 2018-09-03 14:57:49
	 */
	public static String generateStr(int len){
		
		int l = 0, r = 0;
		if(len <= 0 || len == 32){
			l = 1;
			r = 0;
		}else if(len < 32){
			return generateUUID(len);
		}else{
			l = len / 32;
			r = len % 32;
		}
		
		String str = "";
		for(int i=0;i<l;i++){
			str = str + generateUUID();
		}
		if(r > 0){
			str = str + generateUUID(r);
		}
		
		return str;
	}
	
}