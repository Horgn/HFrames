package com.hframe.basic.config;

import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hframe.basic.common.JsonResult;
import com.hframe.basic.common.Results;
import com.hframe.basic.exception.PageException;
import com.hframe.basic.util.SessionUtils;
import com.hframe.basic.util.StringUtils;

/**
 * 全局异常处理<br>
 * 此注解用于标识此类为全局的异常处理类
 * @author Horgn黄小光
 * @version v1.0
 * @date 2017-12-25 14:34:31
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	
	@Value("${hframe.exception.print}")
	private Boolean openExInfo;
	
	private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	/**
	 * Exception.class 类的异常处理
	 * @author Horgn黄小光
	 * @param e
	 * @return
	 * @date 2017-12-25 14:36:34
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public JsonResult handleException(Exception e){
		
		logger.error("============ 全局异常处理 [运行异常]： "+e.getMessage());
		if(null != openExInfo && true == openExInfo){
			e.printStackTrace();
		}
		
		String message = e.getMessage();
		if(StringUtils.isNotEmpty(message)){
			return Results.Error(message);
		}
		
		return Results.Error("哎哟，出现异常啦");
	}
	
	/**
	 * 页面异常
	 * @author Horgn黄小锤
	 * @date 2019年1月26日 上午9:13:42
	 * @param e
	 * @return
	 */
	@ExceptionHandler(PageException.class)
	public String handlePageException(PageException e){
		
		logger.error("============ 全局异常处理 [页面异常]： "+e.getMessage());
		if(null != openExInfo && true == openExInfo){
			e.printStackTrace();
		}
		
		if(StringUtils.isNotEmpty(e.getMessage())){
			SessionUtils.setSessionAttribute("error_message", e.getMessage());
		}else{
			SessionUtils.setSessionAttribute("error_message", "获取页面出错咯");
		}
		
		return "pages/error_page";
	}
	
	/**
	 * 权限权限
	 * @author Horgn黄小锤
	 * @date 2019年1月11日 下午4:41:14
	 * @param e
	 * @return
	 */
	@ExceptionHandler(UnauthorizedException.class)
	public String handlePermissionException(UnauthorizedException e){
		
		logger.error("============ 全局异常处理 [权限异常]："+e.getMessage());
		if(null != openExInfo && true == openExInfo){
			e.printStackTrace();
		}
		
		SessionUtils.setSessionAttribute("error_message", "权限不足，无法访问");
			
		return "pages/error_role";
	}
	
}
