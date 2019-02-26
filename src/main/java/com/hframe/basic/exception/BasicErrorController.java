package com.hframe.basic.exception;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hframe.basic.common.JsonResult;
import com.hframe.basic.common.Results;

/**
 * 非 Controller 异常捕获
 * @author Horgn黄小锤
 * @date 2019年2月19日 下午2:59:07
 * @version V1.0
 */
@Controller
public class BasicErrorController implements ErrorController{ 
	
	private Logger logger = LoggerFactory.getLogger(BasicErrorController.class);

	@Override
	public String getErrorPath() {
		return "pages/error_page";
	}
	
	/**
	 * 请求出错响应
	 * @author Horgn黄小锤
	 * @date 2019年2月19日 下午3:49:12
	 * @return
	 */
	@RequestMapping("/error")
	@ResponseBody
	public JsonResult getErrorPage(HttpServletRequest request){
		
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
//		String requestURL = request.getRequestURI();
		
		logger.error("获取页面出错咯 [ 请求状态："+statusCode+" ]");
		return Results.Error("获取页面出错咯");
		
	}

}