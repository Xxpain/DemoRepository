package test;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jp.dao.SeckillDao;
import com.jp.entity.Seckill;
/**
 * 配置spring和junit整合，junit启东时加载springIOC容器
 * spring-test，junit
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

	@Resource
	private SeckillDao seckillDao;
	@Test
	public void testQueryById() throws Exception{
		int id = 1;
		Seckill seckill = seckillDao.queryById(id);
		System.out.println(seckill.getName());
		System.out.println(seckill);
	}
	@Test
	public void testQueryAll() throws Exception{
		List<Seckill> seckills = seckillDao.queryAll(0, 100);
		for(Seckill seckill : seckills){
			System.out.println(seckill);
		}
	}
	@Test
	public void testReduceNumber() throws Exception{
		Date killTime = new Date();
		int updateCount = seckillDao.reduceNumber(4, killTime);
		System.out.println("updateCount:"+updateCount);
		if(updateCount>0){
			System.out.println("减库存成功");
		}else{
			System.out.println("减库存失败!!!");
			
		}
	}
}

















