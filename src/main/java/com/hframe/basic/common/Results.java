package com.hframe.basic.common;

import java.io.Serializable;

import com.hframe.basic.common.JsonResult.ResultCode;

/**
 * 页面请求返回类型<br>
 * @author Horgn黄小锤
 * @version v2.0
 * @date 2018-09-08 09:59:12
 */
public class Results implements Serializable{
	
	private static final long serialVersionUID = -8290152157845806441L;

	/**
	 * 页面请求返回类型<br>
	 * 请求正常，返回正常状态（1）
	 * @author Horgn黄小锤
	 * @return
	 * @date 2018-10-23 15:15:55
	 */
	public static JsonResult OK(){
		return new JsonResult(ResultCode.SUCCESS,null);
	}
	
	/**
	 * 页面请求返回类型<br>
	 * 请求正常，返回正常状态（1）及提示信息
	 * @author Horgn黄小锤
	 * @param message
	 * @return
	 * @date 2018-09-08 10:00:28
	 */
	public static JsonResult OK(String message){
		return new JsonResult(ResultCode.SUCCESS,message);
	}
	
	/**
	 * 页面请求返回类型<br>
	 * 请求正常，返回正常状态（1）及一对键值对数据
	 * @author Horgn黄小锤
	 * @param key 数据名称
	 * @param value 数据
	 * @return
	 * @date 2018-10-15 19:27:00
	 */
	public static JsonResult OK(String key,Object value){
		JsonResult result = new JsonResult(ResultCode.SUCCESS,null,null);
		result.addData(key, value);
		return result;
	}
	
	/**
	 * 页面请求返回类型<br>
	 * 请求正常，返回正常状态（1）及一组键值对数据
	 * @author Horgn黄小锤
	 * @param values 格式(key,value)："id",34,"name","horgn","age",25 等
	 * @return
	 * @date 2018-10-23 15:18:19
	 */
	public static JsonResult OK(Object...values){
		JsonResult result = new JsonResult(ResultCode.SUCCESS,null,null);
		result.data(values);
		return result;
	}
	
	/**
	 * 页面请求返回类型<br>
	 * 请求异常、错误，返回错误状态（0）
	 * @author Horgn黄小锤
	 * @return
	 * @date 2018-10-23 15:21:53
	 */
	public static JsonResult Error(){
		return new JsonResult(ResultCode.ERROR,null);
	}
	
	/**
	 * 页面请求返回类型<br>
	 * 请求异常、错误，返回错误状态（0）及提示信息
	 * @author Horgn黄小锤
	 * @param message 提示信息
	 * @return
	 * @date 2018-09-08 10:00:28
	 */
	public static JsonResult Error(String message){
		return new JsonResult(ResultCode.ERROR,message);
	}
	
	/**
	 * 页面请求返回类型<br>
	 * 请求异常、错误，返回错误状态（0）及一对键值对数据
	 * @author Horgn黄小锤
	 * @param key 数据名称
	 * @param value 数据值
	 * @return
	 * @date 2018-10-23 14:46:07
	 */
	public static JsonResult Error(String key,Object value){
		JsonResult result = new JsonResult(ResultCode.ERROR,null,null);
		result.addData(key, value);
		return result;
	}
	
	/**
	 * 页面请求返回类型<br>
	 * 请求异常、错误，返回错误状态（0）及一组键值对数据
	 * @author Horgn黄小锤
	 * @param values 格式(key,value)："id",34,"name","horgn","age",25 等
	 * @return
	 * @date 2018-10-23 15:22:51
	 */
	public static JsonResult Error(Object... values){
		JsonResult result = new JsonResult(ResultCode.ERROR,null,null);
		result.data(values);
		return result;
	}
	
}