package com.jp.service;

import java.util.List;

import com.jp.dto.Exposer;
import com.jp.dto.SeckillExecution;
import com.jp.entity.Seckill;
import com.jp.exception.RepeatKillException;
import com.jp.exception.SeckillCloseException;
import com.jp.exception.SeckillException;

/**
 * վ��ʹ���߽Ƕ���ƽӿ�
 * һ��������������
 * @author Zhangning
 */
public interface SeckillService {
	/**
	 * ��ѯ������ɱ��¼
	 * @return
	 */
	List<Seckill> getSeckillList();
	/**
	 * ��ѯ������ɱ��¼
	 * @param seckillId
	 * @return
	 */
	Seckill getById(int seckillId);
	/**
	 * ��ɱ����ʱ�� �������ɱ�ӿڵĵ�ַ
	 * �������ϵͳʱ�����ɱʱ��
	 * @param seckillId
	 */
	Exposer exportSeckillUrl(int seckillId);
	/**
	 * ִ����ɱ����
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 */
	SeckillExecution executeSeckill(int seckillId,long userPhone,String md5)
			throws SeckillCloseException,SeckillException,RepeatKillException;
}
