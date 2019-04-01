package test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jp.dto.Exposer;
import com.jp.dto.SeckillExecution;
import com.jp.entity.Seckill;
import com.jp.exception.RepeatKillException;
import com.jp.exception.SeckillCloseException;
import com.jp.exception.SeckillException;
import com.jp.service.SeckillService;


@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
public class SeckillServiceTest {
	//日志的加载
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SeckillService seckillService;

	@Test
	public void testGetSeckillList() throws Exception{
		List<Seckill> list = seckillService.getSeckillList();
		logger.info("list={}",list);
		for (Seckill seckill : list) {
			System.out.println(seckill);
		}
	}
	

	/**
	 * 根据id查seckill商品信息
	 * @throws Exception
	 */
	@Test
	public void testGetById() throws Exception{
		int id = 1;
		Seckill seckill = seckillService.getById(id);
		logger.info("seckill={}",seckill);
		System.out.println(seckill);
	}
	
	//接口地址和执行秒杀的2个测试放一起.可重复执行
	@Test
	public void testSeckillLogic() throws Exception{
		int id =4;//秒杀的商品的id 注意是否在秒杀时间内
		Exposer exposer = seckillService.exportSeckillUrl(id);
		if(exposer.isExposed()){
			long phone=8888888888L;//秒杀时候的账号信息
			String md5= exposer.getMd5();
			try {
				SeckillExecution seckillExecution=seckillService.executeSeckill(id, phone, md5);
				logger.info("seckillExecution={}",seckillExecution);
				
			}catch (RepeatKillException e1) {
				logger.error(e1.getMessage());
			}catch (SeckillException e) {
				logger.error(e.getMessage());
			}catch (SeckillCloseException e) {
				logger.error(e.getMessage());
			}
		}else{
		//秒杀未开启
			System.out.println("秒杀未开启！");
			logger.warn("exposer={}",exposer);
		}
	}
	
	
	@Test
	public void testExportSeckillUrl() throws Exception{
		int id =3;
		Exposer exposer = seckillService.exportSeckillUrl(id);
		logger.info("exposer={} ",exposer);
	}
	//执行秒杀
	@Test
	public void testExecuteSeckill() throws Exception{
		int id=3;
		long phone=8888888888L;
		String md5 = "26a00ff44271e437bfe00848d8cf3a16";
		try {
			SeckillExecution seckillExecution=seckillService.executeSeckill(id, phone, md5);
			logger.info("seckillExecution={}",seckillExecution);
			
		}catch (RepeatKillException e1) {
			logger.error(e1.getMessage());
		}catch (SeckillException e) {
			logger.error(e.getMessage());
		}catch (SeckillCloseException e) {
			logger.error(e.getMessage());
		}
	}
	
	
	
}
