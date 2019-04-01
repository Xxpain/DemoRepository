package com.jp.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.jp.dao.SeckillDao;
import com.jp.dao.SuccessKilledDao;
import com.jp.dao.cache.RedisDao;
import com.jp.dto.Exposer;
import com.jp.dto.SeckillExecution;
import com.jp.entity.Seckill;
import com.jp.entity.SuccessKilled;
import com.jp.enums.SeckillStateEnum;
import com.jp.exception.RepeatKillException;
import com.jp.exception.SeckillCloseException;
import com.jp.exception.SeckillException;
import com.jp.service.SeckillService;

//@Compinent
// @Service @Repository @Controller
@Service
public class SeckillServiceImpl implements SeckillService {
	// ��־�ļ���
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private SeckillDao seckillDao;
	@Autowired
	private SuccessKilledDao successKilledDao;
	@Autowired
	private RedisDao redisDao;

	private final String slat = "lkasjd901@82oi()&^dpuj98aj@12o3uksjd@#(*l";
	// ��ѯ������ɱ��¼
	public List<Seckill> getSeckillList() {
		return seckillDao.queryAll(0, 10);
	}
	// ��ѯ������ɱ��¼
	public Seckill getById(int seckillId) {
		return seckillDao.queryById(seckillId);
	}
	/**
	 * ��ɱ����ʱ�� �������ɱ�ӿڵĵ�ַ �������ϵͳʱ�����ɱʱ��
	 */
	public Exposer exportSeckillUrl(int seckillId) {
		// �Ż��� �����Ż� ��ʱ�Ļ�����ά��һ����
		// ����redis
		//Seckill seckill = redisDao.getSeckill(seckillId);
			// �������Ҳ��� �ͷ������ݿ���seckill����
			Seckill seckill = seckillDao.queryById(seckillId);
			if (seckill == null) {// �鲻�������Ҫ��ɱ�Ĳ�Ʒ
				return new Exposer(false, seckillId);
			}
			//��reids�洢--ǰ���redis
//			else {
//				redisDao.putSeckill(seckill);
//			}
		Date startTime = seckill.getStartTime();
		Date endTime = seckill.getEndTime();
		Date newTime = new Date();
		if (newTime.getTime() < startTime.getTime()
				|| newTime.getTime() > endTime.getTime()) {
			return new Exposer(false, seckillId, newTime.getTime(),
					startTime.getTime(), endTime.getTime());
		}
		String md5 = getMD5(seckillId);
		// System.out.println("MD5Ϊ����"+md5);
		return new Exposer(true, md5, seckillId);
	}

	/**
	 * ������ɱ��Ʒid�õ����ܵ�md5
	 * 
	 * @param seckillId
	 * @return
	 */
	private String getMD5(long seckillId) {
		String base = seckillId + "/" + slat;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}

	/**
	 * ִ����ɱ����
	 * ʹ��ע��������񷽷����е� 
	 * 1.�Ŷ�һ����֪���㿪��������,����ע���֪�� ������������񷽷�
	 * 2.��֤���񷽷���ִ��ʱ�価���̣���Ҫ����������������� 
	 * 3.�������еķ�������Ҫ�������ֻ��һ���޸Ĳ�������ֻ����������Ҫ�������
	 */
	@Transactional
	public SeckillExecution executeSeckill(int seckillId, long userPhone,String md5) 
			throws SeckillCloseException, SeckillException,RepeatKillException {
		if (md5 == null || !md5.equals(getMD5(seckillId))) {
			throw new SeckillException("��ɱ���ݱ���д��");
		}
		// ִ����ɱ�߼� �����ͼ�¼������Ϊ
		Date nowTime = new Date();
		try {
			//��¼������Ϊ,��ס����ͻ�����Ϣ 
			int insertCount = successKilledDao.insertSuccessKilled(seckillId,userPhone);
			// Ψһ��seckillId ,userPhone
			if (insertCount <= 0) {
				//�ظ���ɱ
				throw new RepeatKillException("�ظ���ɱ");
			} else {
				// �����  �ȵ���Ʒ����
				int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
				if (updateCount <= 0) {
					// û�и��¼�¼  ��ɱ����  rollback
					throw new SeckillCloseException(SeckillStateEnum.END.getStateInfo());
				} else {
					//��ɱ�ɹ� commit
					SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
					return new SeckillExecution(seckillId,SeckillStateEnum.SUCCESS, successKilled);
				}
			}
		} catch (SeckillCloseException e1) {
			//�ڲ���ĵط��ٴ��׳����Ϳ��Խ�����񲻻ع�������
			throw e1;
		} catch (RepeatKillException e2) {
			throw e2;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			// ���б�����쳣 ת��Ϊ�������쳣
			throw new SeckillException("seckill inner error:" + e.getMessage());
		}
	}
}
