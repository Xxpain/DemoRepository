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
	// 日志的加载
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
		// redis 操作逻辑
		try {
			Jedis jedis = jedisPool.getResource();
			try {
				String key = "seckill:" + seckillId;
				// 并没有实现内部序列化
				// get -> byte[] -> 反序列化Object(Seckill)
				// 使用protostuff :pojo
				byte[] bytes = jedis.get(key.getBytes());
				if (bytes != null) {
					// 空对象
					Seckill seckill = schema.newMessage();
					ProtostuffIOUtil.mergeFrom(bytes, seckill, schema);
					// seckill 被反序列
					return seckill;
				}
			} finally {
				jedis.close();// 关闭缓存
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	
	
	

	public String putSeckill(Seckill seckill) {
		// set Object(Seckill) -> 序列化 ->byte[]
			Jedis jedis = jedisPool.getResource();
			try {
				String key = "seckill:" + seckill.getSeckillId();
				byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema, 
						LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
				//超时缓存
				int timeout= 60*60;//1小时时间  缓存时效
				String result = jedis.setex(key.getBytes(), timeout,bytes);
				return result;
			} finally {
				jedis.close();
			}
	}
}
