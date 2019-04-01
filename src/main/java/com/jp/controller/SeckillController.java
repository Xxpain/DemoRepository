package com.jp.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jp.dto.Exposer;
import com.jp.dto.SeckillExecution;
import com.jp.dto.SeckillResult;
import com.jp.entity.Seckill;
import com.jp.enums.SeckillStateEnum;
import com.jp.exception.RepeatKillException;
import com.jp.exception.SeckillCloseException;
import com.jp.exception.SeckillException;
import com.jp.service.SeckillService;

@Controller
@RequestMapping("/seckill") //url:/ģ��/��Դ/{id}/ϸ��  /seckill/list
public class SeckillController {
	//��־�ļ���
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private SeckillService seckillService;
	/**
	 * ������
	 * �����¼ҳ
	 * @param name
	 * @param pwd
	 * @return
	 */
	@RequestMapping("/hello")
	public String viewAll(Model model){
		System.out.println(1);
		return "/hello";
	}
	@RequestMapping("/viewAll")
	public ModelAndView viewAll(String name,String pwd){
		ModelAndView mv = new ModelAndView();
		System.out.println("������ viewAll ��������");
		System.out.println("����:"+name);
		System.out.println("����:"+pwd);
		mv.setViewName("/success");
		mv.addObject("data","��viewAll��������������data");
		return mv;
	}
	/**
	 * ��ȡ�б�ҳ /seckill/list
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/list",method= RequestMethod.GET)
	public String list(Model model){
		//list.jsp  +model  = ModelAndView
		List<Seckill> list =seckillService.getSeckillList();
		model.addAttribute("seckillList", list);
		return "/list";//list.jsp
	}
	/**
	 * ����
	 * seckill/5/detail
	 * @param seckillId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/{seckillId}/detail",method= RequestMethod.GET)
	public String detail(@PathVariable("seckillId") Integer seckillId,Model model){
		if(seckillId == null){
			return "redirect:/seckill/list";//�ض���
		}
		Seckill seckill = seckillService.getById(seckillId);
		if(seckill==null){
			return "forward:/seckill/list";//ת��
		}
		model.addAttribute("seckill", seckill);
		return "/detail";
	}
	
	//ajax�ӿ�  ������json  
	//��ɱ����ʱ�� �������ɱ�ӿڵĵ�ַ �������ϵͳʱ�����ɱʱ��
	@RequestMapping(value="{seckillId}/exposer",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	@ResponseBody//�������ע�� �Ὣʵ���װ��json��ʽ
	public SeckillResult<Exposer> exposer(@PathVariable("seckillId")Integer seckillId){
		SeckillResult<Exposer> result ;
		try {
			Exposer exposer = seckillService.exportSeckillUrl(seckillId);
			result = new SeckillResult<Exposer>(true,exposer);

		} catch (Exception e) {
			logger.error(e.getMessage());
			result = new SeckillResult<Exposer>(false,e.getMessage());
		}
		System.out.println("~~~~~~~~~~"+result);
		return result; 
	}
	
	/**
	 * ִ����ɱ
	 * @param seckillId
	 * @param md5
	 * @param phone
	 * @return
	 */
	@RequestMapping(value="/{seckillId}/{md5}/execution",
method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public SeckillResult<SeckillExecution> execute(@PathVariable Integer seckillId,
			@PathVariable("md5") String md5,
			@CookieValue(value="killPhone",required=false) Long phone){
		SeckillResult<SeckillExecution> result ;
		if(phone==null){
			return new SeckillResult<SeckillExecution>(false,"δע��");
		}
		try {
			SeckillExecution execution = seckillService.executeSeckill(seckillId, phone, md5);
			return new SeckillResult<SeckillExecution>(true,execution);

		}catch (RepeatKillException e) {//�ظ���ɱ�쳣
			SeckillExecution execution = new SeckillExecution(seckillId, SeckillStateEnum.REPEAT_KILL);
			return new SeckillResult<SeckillExecution>(true,execution);
		}catch (SeckillCloseException e) {//��ɱ�ر� �������쳣
			SeckillExecution execution = new SeckillExecution(seckillId, SeckillStateEnum.END);
			return new SeckillResult<SeckillExecution>(true,execution);
		}catch (SeckillException e) {//��ɱͨ���쳣
			logger.error(e.getMessage());
			SeckillExecution execution = new SeckillExecution(seckillId, SeckillStateEnum.INNER_ERROR);
			return new SeckillResult<SeckillExecution>(true,execution);
		}
	}
	/**
	 * ��ȡϵͳʱ��
	 */
	@RequestMapping(value="/time/now",method=RequestMethod.GET)
	@ResponseBody
	public  SeckillResult<Long> time (){
		Date now = new Date();
		return new SeckillResult(true,now.getTime());
	} 
}
