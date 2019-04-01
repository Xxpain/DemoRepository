package com.jp.dao.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.jp.entity.Seckill;

public class RedisDao {
	// ��־�ļ���
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private JedisPool jedisPool;

	private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);

	public RedisDao(String ip, int port) {
		jedisPool = new JedisPool(ip, port);
	}

	/**
	 * 
	 * @param seckillId
	 * @return
	 */
	public Seckill getSeckill(int seckillId) {
		// redis �����߼�
		try {
			Jedis jedis = jedisPool.getResource();
			try {
				String key = "seckill:" + seckillId;
				// ��û��ʵ���ڲ����л�
				// get -> byte[] -> �����л�Object(Seckill)
				// ʹ��protostuff :pojo
				byte[] bytes = jedis.get(key.getBytes());
				if (bytes != null) {
					// �ն���
					Seckill seckill = schema.newMessage();
					ProtostuffIOUtil.mergeFrom(bytes, seckill, schema);
					// seckill ��������
					return seckill;
				}
			} finally {
				jedis.close();// �رջ���
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	
	
	

	public String putSeckill(Seckill seckill) {
		// set Object(Seckill) -> ���л� ->byte[]
			Jedis jedis = jedisPool.getResource();
			try {
				String key = "seckill:" + seckill.getSeckillId();
				byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema, 
						LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
				//��ʱ����
				int timeout= 60*60;//1Сʱʱ��  ����ʱЧ
				String result = jedis.setex(key.getBytes(), timeout,bytes);
				return result;
			} finally {
				jedis.close();
			}
	}
}
