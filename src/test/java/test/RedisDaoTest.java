package test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jp.dao.SeckillDao;
import com.jp.dao.cache.RedisDao;
import com.jp.entity.Seckill;

@RunWith(SpringJUnit4ClassRunner.class)
//����junit spring�����ļ�
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class RedisDaoTest{

	
	@Autowired
	private RedisDao redisDao;
	@Autowired
	private SeckillDao seckillDao;
	
	@Test
	public void testSckill(){
		//get and put 
		int seckillId = 2;
		Seckill seckill = redisDao.getSeckill(seckillId);
		System.out.println("seckillId="+seckillId+"�����л������Ѿ����뻺�棬Ϊ��"+seckill);
		if (seckill ==null) {
			seckill = seckillDao.queryById(seckillId);
			if (seckill!=null) {
				String result =redisDao.putSeckill(seckill);
				System.out.println("�Ƿ�redis�ɹ���"+result);
				seckill = redisDao.getSeckill(seckillId);
				System.out.println("seckill"+seckill);
			}
		}
		
	}
	
}
