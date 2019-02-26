package com.hframe.basic.exception;

/**
 * 获取页面异常
 * @author Horgn黄小锤
 * @date 2019年1月26日 上午9:12:57
 * @version V1.0
 */
public class PageException extends RuntimeException{

	private static final long serialVersionUID = -5380732581051707240L;
	
	/**
	 * 获取页面异常
	 * 构造器 PageException
	 */
	public PageException(){
		super("获取页面出错咯" );
	}
	
	/**
	 * 获取页面异常
	 * 构造器 PageException
	 * @param message 异常信息
	 */
	public PageException(String message){
		super(message);
	}
	

}
