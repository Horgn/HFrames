package com.hframe.basic.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.hframe.basic.root.RootUtils;


/**
 * 用户登录验证拦截器
 * @author Horgn黄小锤
 * @version v1.0
 * @date 2018-03-27 10:56:58
 */
public class LoginInterceptor implements HandlerInterceptor {
	

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		
		//从Session中获取用户登录状态
		if(!RootUtils.getLoginState()){
			
			request.getRequestDispatcher("/login").forward(request, response);
			return false;
		}
		
		return true;
		
	}

}