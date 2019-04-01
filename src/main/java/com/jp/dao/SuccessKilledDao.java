package com.jp.dao;

import org.apache.ibatis.annotations.Param;

import com.jp.entity.SuccessKilled;

public interface SuccessKilledDao {
	/**
	 * ���빺����ϸ���ɹ����ظ�
	 * @param seckillId
	 * @param userPhone
	 */
	int insertSuccessKilled(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);
	
	/**
	 * ����id���ֻ��Ų�ѯSuccessKilled��Я����ɱ��Ʒ����ʵ��
	 * @param seckillId
	 */
	SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);
	
}
