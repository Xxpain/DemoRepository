package com.jp.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.sun.net.httpserver.HttpHandler;

public class Test1Intercepter implements HandlerInterceptor {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// ���������֮����ִ�е�
		System.out.println("text1ִ����preHandle");
		request.setCharacterEncoding("utf-8");

		if (request.getSession().getAttribute("user") == null) {
			// û��session ��ֹ����
			// �����͵���¼ҳ��
			System.out.println("û��session  ��ֹ����");
			request.getRequestDispatcher("/seckill/hello").forward(request, response);
			return false;
		}
		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// System.out.println("text1ִ����postHandle");

	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// System.out.println("text1ִ����afterCompletion");

	}

}
