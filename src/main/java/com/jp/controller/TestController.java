package com.jp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/TestController")
public class TestController {

	@RequestMapping("/viewAll")
	public ModelAndView viewAll(String name,String pwd){
		ModelAndView mv = new ModelAndView();
		System.out.println("������ viewAll ��������");
		System.out.println("����:"+name);
		System.out.println("����:"+pwd);
		mv.setViewName("/hello.jsp");
		return mv;
	}
}
