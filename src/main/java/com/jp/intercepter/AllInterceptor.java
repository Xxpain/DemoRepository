package com.jp.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class AllInterceptor implements HandlerInterceptor{

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// 进入控制器之间先执行的
		System.out.println("执行了ALL的拦截器  preHandle");
		response.setCharacterEncoding("utf-8");
		return true;
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		//System.out.println("执行了ALL的拦截器  postHandle");
		
	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		//System.out.println("执行了ALL的拦截器  afterCompletion");
		
	}

}
