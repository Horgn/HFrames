package com.hframe.basic.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Session工具类
 * 
 * @author horgn黄小锤
 * @date 2018年12月19日
 * @version 1.0
 */
public class SessionUtils {
	
	/**
	 * 获取当前Request请求对象
	 * @author Horgn黄小锤
	 * @date 2019年1月21日 下午5:34:58
	 * @return
	 */
	public static HttpServletRequest getRequest(){ 
		
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes(); 
		return requestAttributes == null ? null : requestAttributes.getRequest(); 
	} 
	
	/**
	 * 获取当前Response响应对象
	 * @author Horgn黄小锤
	 * @date 2019年1月21日 下午5:35:03
	 * @return
	 */
	public static HttpServletResponse getResponse(){
		
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes(); 
		return requestAttributes == null ? null : requestAttributes.getResponse();
	}
	
	/**
	 * 获取当前Session对象
	 * @author Horgn黄小锤
	 * @date 2019年1月21日 下午5:35:13
	 * @return
	 */
	public static HttpSession getSession(){
		
		HttpServletRequest request = getRequest();
		return request == null ? null : request.getSession();
	}
	
	/**
	 * 获取sessionID
	 * @author Horgn黄小锤
	 * @date 2019年1月21日 下午5:35:18
	 * @return
	 */
	public static String getSessionId(){
		
		HttpSession session = getSession();
		return session == null ? null : session.getId();
	}
	
	/**
	 * 获取Ip地址
	 * @author Horgn黄小锤
	 * @date 2019年1月21日 下午5:35:22
	 * @return
	 */
	public static String getIp() { 
		
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder .getRequestAttributes(); 
		
		if(requestAttributes != null){ 
			HttpServletRequest request = requestAttributes.getRequest(); 
			return request.getRemoteAddr(); 
		} 
		
		return null; 
	}
	
	/**
	 * 获取真实路径
	 * @author Horgn黄小锤
	 * @date 2019年1月21日 下午5:35:27
	 * @return
	 */
	public static String getRealRootPath(){  
		
		HttpServletRequest request = getRequest();
		return request == null ? null : request.getServletContext().getRealPath("/"); 
    }
	
	 /**
	  * 获取session中的Attribute  
	  * @author Horgn黄小锤
	  * @date 2019年1月21日 下午5:35:45
	  * @param name
	  * @return
	  */
	public static Object getSessionAttribute(String name){ 
		
    	 HttpServletRequest request = getRequest(); 
    	 return request == null ? null : request.getSession().getAttribute(name); 
    } 
	
	/** 
	 * 设置session的Attribute 
	 * @author Horgn黄小锤
	 * @date 2019年1月21日 下午5:35:51
	 * @param name
	 * @param value
	 */
	public static void setSessionAttribute(String name,Object value){ 
		
		HttpServletRequest request = getRequest(); 
		if(request!=null){ 
			request.getSession().setAttribute(name, value); 
		} 
	} 
	
	/**
	 *  获取request中的Attribute
	 * @author Horgn黄小锤
	 * @date 2019年1月21日 下午5:41:14
	 * @param name
	 * @return
	 */
	public static Object getRequestAttribute(String name){ 
		
		HttpServletRequest request = getRequest(); 
		return request == null ? null : request.getAttribute(name); 
	} 
	
	/**
	 * 设置request的Attribute
	 * @author Horgn黄小锤
	 * @date 2019年1月21日 下午5:41:18
	 * @param name
	 * @param value
	 */
	public static void setRequestAttribute(String name,Object value){ 
		
		HttpServletRequest request = getRequest(); 
		if(request != null){ 
			request.setAttribute(name, value); 
		} 
	} 
	
	/**
	 * 获取上下文path
	 * @author Horgn黄小锤
	 * @date 2019年1月21日 下午5:41:24
	 * @return
	 */
	public static String getContextPath() { 
		
		HttpServletRequest request = getRequest(); 
		return request == null ? null : request.getContextPath();
	} 
	
	/**
	 * *获取RequestURL
	 * @author Horgn黄小锤
	 * @date 2019年1月21日 下午5:41:39
	 * @return
	 */
	public static String getRequestURL(){
		
		HttpServletRequest request = getRequest(); 
		return request == null ? null : request.getRequestURL().toString();
	}
	
	/**
	 * 获取RequestURI
	 * @author Horgn黄小锤
	 * @date 2019年1月21日 下午5:41:52
	 * @return
	 */
	public static String getRequestURI(){
		
		HttpServletRequest request = getRequest(); 
		return request == null ? null : request.getRequestURI();
	}
	
	/**
	 * 删除Session中的参数
	 * @author Horgn黄小锤
	 * @date 2019年1月21日 下午5:41:58
	 * @param name
	 */
	public static void removeSessionAttribute(String name) { 
		
		HttpSession session = getSession();
		if(session != null){
			session.removeAttribute(name);
		}
	} 
	
	/*
	 * 删除Request中的参数
	 */
	public static void removeRequestAttribute(String name){
		
		HttpServletRequest request = getRequest(); 
		if(request != null){
			request.removeAttribute(name);
		}
	}

}
