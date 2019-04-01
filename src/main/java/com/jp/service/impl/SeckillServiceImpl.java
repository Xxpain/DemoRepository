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
	// 日志的加载
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private SeckillDao seckillDao;
	@Autowired
	private SuccessKilledDao successKilledDao;
	@Autowired
	private RedisDao redisDao;

	private final String slat = "lkasjd901@82oi()&^dpuj98aj@12o3uksjd@#(*l";
	// 查询所有秒杀记录
	public List<Seckill> getSeckillList() {
		return seckillDao.queryAll(0, 10);
	}
	// 查询单个秒杀记录
	public Seckill getById(int seckillId) {
		return seckillDao.queryById(seckillId);
	}
	/**
	 * 秒杀开启时候 。输出秒杀接口的地址 否则输出系统时间和秒杀时间
	 */
	public Exposer exportSeckillUrl(int seckillId) {
		// 优化点 缓存优化 超时的基础上维护一致性
		// 访问redis
		//Seckill seckill = redisDao.getSeckill(seckillId);
			// 缓存中找不到 就访问数据库找seckill对象
			Seckill seckill = seckillDao.queryById(seckillId);
			if (seckill == null) {// 查不到这个需要秒杀的产品
				return new Exposer(false, seckillId);
			}
			//用reids存储--前提打开redis
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
		// System.out.println("MD5为：："+md5);
		return new Exposer(true, md5, seckillId);
	}

	/**
	 * 传入秒杀商品id得到加密的md5
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
	 * 执行秒杀操作
	 * 使用注解控制事务方法的有点 
	 * 1.团队一看就知道你开启了事务,看到注解就知道 这个方法是事务方法
	 * 2.保证事务方法的执行时间尽量短，不要穿插其他的网络操作 
	 * 3.不是所有的方法都需要事务，如果只有一条修改操作或者只读操作不需要事务控制
	 */
	@Transactional
	public SeckillExecution executeSeckill(int seckillId, long userPhone,String md5) 
			throws SeckillCloseException, SeckillException,RepeatKillException {
		if (md5 == null || !md5.equals(getMD5(seckillId))) {
			throw new SeckillException("秒杀数据被重写了");
		}
		// 执行秒杀逻辑 减库存和记录购买行为
		Date nowTime = new Date();
		try {
			//记录购买行为,记住购买客户的信息 
			int insertCount = successKilledDao.insertSuccessKilled(seckillId,userPhone);
			// 唯一：seckillId ,userPhone
			if (insertCount <= 0) {
				//重复秒杀
				throw new RepeatKillException("重复秒杀");
			} else {
				// 减库存  热点商品竞争
				int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
				if (updateCount <= 0) {
					// 没有更新记录  秒杀结束  rollback
					throw new SeckillCloseException(SeckillStateEnum.END.getStateInfo());
				} else {
					//秒杀成功 commit
					SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
					return new SeckillExecution(seckillId,SeckillStateEnum.SUCCESS, successKilled);
				}
			}
		} catch (SeckillCloseException e1) {
			//在捕获的地方再次抛出，就可以解决事务不回滚的问题
			throw e1;
		} catch (RepeatKillException e2) {
			throw e2;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			// 所有编译的异常 转化为运行期异常
			throw new SeckillException("seckill inner error:" + e.getMessage());
		}
	}
}
