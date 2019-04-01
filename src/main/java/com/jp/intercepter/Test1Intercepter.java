package com.jp.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.sun.net.httpserver.HttpHandler;

public class Test1Intercepter implements HandlerInterceptor {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 进入控制器之间先执行的
		System.out.println("text1执行了preHandle");
		request.setCharacterEncoding("utf-8");

		if (request.getSession().getAttribute("user") == null) {
			// 没有session 终止请求
			// 并发送到登录页面
			System.out.println("没有session  终止请求");
			request.getRequestDispatcher("/seckill/hello").forward(request, response);
			return false;
		}
		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// System.out.println("text1执行了postHandle");

	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// System.out.println("text1执行了afterCompletion");

	}

}
