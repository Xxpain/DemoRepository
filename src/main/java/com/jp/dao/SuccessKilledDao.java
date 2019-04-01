package com.jp.dao;

import org.apache.ibatis.annotations.Param;

import com.jp.entity.SuccessKilled;

public interface SuccessKilledDao {
	/**
	 * 插入购买明细，可过滤重复
	 * @param seckillId
	 * @param userPhone
	 */
	int insertSuccessKilled(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);
	
	/**
	 * 根据id和手机号查询SuccessKilled并携带秒杀产品对象实体
	 * @param seckillId
	 */
	SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);
	
}
