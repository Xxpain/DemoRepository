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
		System.out.println("进入了 viewAll 控制器！");
		System.out.println("姓名:"+name);
		System.out.println("密码:"+pwd);
		mv.setViewName("/hello.jsp");
		return mv;
	}
}
