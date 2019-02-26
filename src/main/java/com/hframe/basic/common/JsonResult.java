package com.hframe.basic.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 页面请求响应类
 * @author Horgn黄小锤
 * @version v2.0
 * @date 2018-09-07 14:52:11
 */
public class JsonResult implements Serializable{
	
	private static final long serialVersionUID = -46385579409238785L;

	/**请求状态*/
	public static final class ResultCode {
		/**正常*/
		public static final Integer SUCCESS = 1;
		/**错误、异常*/
		public static final Integer ERROR = 0;
	}
	
	
	/**响应状态：1正常，0异常、错误*/
	private Integer code;
	/**响应信息，提示信息*/
	private String message;
	/**响应数据*/
	private Map<String, Object> data;
	
	
	/**
	 * 页面请求返回类型
	 * @author Horgn黄小锤
	 * @date 2018-09-07 14:55:17
	 * @param code 请求状态
	 */
	public JsonResult(Integer code){
		this(code,null);
	}
	
	
	/**
	 * 页面请求返回类型
	 * @author Horgn黄小锤
	 * @date 2018-09-07 14:56:04
	 * @param code 请求状态
	 * @param message 提示消息
	 */
	public JsonResult(Integer code,String message){
		this(code,message,null);
	}
	
	
	/**
	 * 页面请求返回类型
	 * @author Horgn黄小锤
	 * @date 2018-09-07 14:56:04
	 * @param code 请求状态
	 * @param message 提示消息
	 * @param data 返回数据
	 */
	public JsonResult(Integer code,String message,Map<String, Object> data){
		
		this.code = code;
		this.data = data;
		
		if(message == null){
			if(ResultCode.SUCCESS == code){ 
				this.message = "操作成功";
				}
			
			if(ResultCode.ERROR == code){
				this.message = "操作失败";
			}
		}else{
			this.message = message;
		}
	}
	
	
	/**
	 * 添加返回数据
	 * @author Horgn黄小锤
	 * @param key
	 * @param value
	 * @return
	 * @date 2018-10-15 19:25:50
	 */
	public JsonResult addData(String key,Object value){
		
		if(data == null) {
			data = new HashMap<>();
		}
		
		data.put(key, value);
		return this;
	}
	
	
	/**
	 * 获取 请求状态
	 * @author Horgn黄小锤
	 * @date 2019年2月17日 下午2:58:45
	 * @return
	 */
	public Integer getCode() {
		return code;
	}

	
	/**
	 * 设置请求状态
	 * @author Horgn黄小锤
	 * @param code
	 * @return
	 * @date 2018-09-08 14:15:34
	 */
	public JsonResult setCode(Integer code) {
		this.code = code;
		return this;
	}

	
	/**
	 * 获取 响应信息
	 * @author Horgn黄小锤
	 * @date 2019年2月17日 下午2:58:50
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	
	/**
	 * 设置响应信息
	 * @author Horgn黄小锤
	 * @param message
	 * @return
	 * @date 2018-09-08 14:15:22
	 */
	public JsonResult setMessage(String message) {
		this.message = message;
		return this;
	}

	
	/**
	 * 获取 响应数据
	 * @author Horgn黄小锤
	 * @date 2019年2月17日 下午2:59:44
	 * @return
	 */
	public Map<String, Object> getData() {
		return data;
	}

	
	/**
	 * 设置响应数据
	 * @author Horgn黄小锤
	 * @param data
	 * @return
	 * @date 2018-09-08 14:15:08
	 */
	public JsonResult setData(Map<String, Object> data) {
		this.data = data;
		return this;
	}

	
	/**
	 * 添加响应数据<br>
	 * 可同时添加多个数据，以key-value的形式传入<br>
	 * --- 例：Results.OK.data("name", "horgn","age",null)<br>
	 * --- 注：当参数长度为单数，最后一个元素将被舍弃，如果value为null,该key也会被舍弃<br>
	 * @author Horgn黄小锤
	 * @param value 数据key-value数组
	 * @return
	 * @date 2018-09-07 14:57:15
	 */
	public JsonResult data(Object... value){
		
		if(null == value || value.length <= 0 ){
			return this;
		}
			
		if(this.data == null){
			this.data = new HashMap<String, Object>();
		}
		
		List<Object> list = new ArrayList<Object>(Arrays.asList(value));
		if(list.size() % 2 == 1){
			list.remove(list.size() - 1);
		}
		
		for(int i = 0; i < list.size(); i = i + 2){
			if(list.get(i+1) != null){
				this.data.put((String) list.get(i), list.get(i+1));
			}
		}
			
		return this;
	}

	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("JsonResult [code=");
		builder.append(code);
		builder.append(", message=");
		builder.append(message);
		builder.append(", data=");
		builder.append(data);
		builder.append("]");
		return builder.toString();
	}
	
}