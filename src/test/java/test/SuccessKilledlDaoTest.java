package test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jp.dao.SuccessKilledDao;
import com.jp.entity.SuccessKilled;
/**
 * 配置spring和junit整合，junit启东时加载springIOC容器
 * spring-test，junit
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledlDaoTest {
	
	@Resource
	private SuccessKilledDao successKilledDao;
	
	/**
	 * 插入购买明细，可过滤重复
	 * @throws Exception
	 */
	@Test
	public void testInsertSuccessKilled() throws Exception{
		int id = 3;
		long phone =13623865089L;
		int insertCount = successKilledDao.insertSuccessKilled(id, phone);
		System.out.println("insertCount:"+insertCount);
	}
	
	@Test
	public void testQueryByIdWithSeckill() throws Exception{
		int id = 3;
		long phone =13623865089L;
		SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(id, phone);
		System.out.println(successKilled);
		System.out.println(successKilled.getSeckill());
	}
	
}

















