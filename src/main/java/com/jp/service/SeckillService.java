package com.jp.service;

import java.util.List;

import com.jp.dto.Exposer;
import com.jp.dto.SeckillExecution;
import com.jp.entity.Seckill;
import com.jp.exception.RepeatKillException;
import com.jp.exception.SeckillCloseException;
import com.jp.exception.SeckillException;

/**
 * 站在使用者角度设计接口
 * 一、方法定义力度
 * @author Zhangning
 */
public interface SeckillService {
	/**
	 * 查询所有秒杀记录
	 * @return
	 */
	List<Seckill> getSeckillList();
	/**
	 * 查询单个秒杀记录
	 * @param seckillId
	 * @return
	 */
	Seckill getById(int seckillId);
	/**
	 * 秒杀开启时候 。输出秒杀接口的地址
	 * 否则输出系统时间和秒杀时间
	 * @param seckillId
	 */
	Exposer exportSeckillUrl(int seckillId);
	/**
	 * 执行秒杀操作
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 */
	SeckillExecution executeSeckill(int seckillId,long userPhone,String md5)
			throws SeckillCloseException,SeckillException,RepeatKillException;
}
