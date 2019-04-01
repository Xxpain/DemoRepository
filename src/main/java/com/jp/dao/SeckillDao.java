package com.jp.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jp.entity.Seckill;

public interface SeckillDao {
	/**
	 * 减库存
	 * @param seckillId
	 * @param killTime
	 * @return
	 */
	int reduceNumber(@Param("seckillId") long seckillId,@Param("killTime") Date killTime); 
	/**
	 * 根据id查询秒杀对象
	 * @param seckillId
	 * @return
	 */
	Seckill queryById(int seckillId);
	/**
	 * 根据偏移量查询秒杀商品列表
	 * @param offet
	 * @param limit
	 * @return
	 */
	List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);
}
