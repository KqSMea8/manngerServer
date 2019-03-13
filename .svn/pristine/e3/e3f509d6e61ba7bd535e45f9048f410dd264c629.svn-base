package com.lmxf.post.core.utils.redis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.lmxf.post.core.utils.SpringContextUtil;
import com.lmxf.post.entity.parameter.NsParameter;
import com.lmxf.post.repository.parameter.NsParameterDao;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * redis连接
 * 
 * @author 思杰
 * 
 */
public class RedisConnection {
	private static String HOST = "118.178.230.113";
	//private static String HOST = "47.110.248.199";
	//private static String HOST = "127.0.0.1";
	private static int PORT = 6479;
	private static int MAX_ACTIVE = 1024;
	private static int MAX_IDLE = 200;
	private static int MAX_WAIT = 10000;
	private static String PASSWORD = "123456";

	private static JedisPool jedisPool = null;

	/*
	 * 初始化redis连接池
	 */
	private static void initPool() {
		try {
			NsParameter nsParameterP = new NsParameter();
			nsParameterP.setName("redisServer_url");
			NsParameterDao nsParameterDao = (NsParameterDao) SpringContextUtil.getBean("nsParameterDao");
			NsParameter nsParameter = nsParameterDao.findOne(nsParameterP);
			if (nsParameter != null && nsParameter.getValue() != null && !"".equals(nsParameter.getValue())) {
				HOST=nsParameter.getValue();
			}
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(MAX_ACTIVE);// 最大连接数
			config.setMaxIdle(MAX_IDLE);// 最大空闲连接数
			config.setMaxWaitMillis(MAX_WAIT);// 获取可用连接的最大等待时间
			jedisPool = new JedisPool(config, HOST, PORT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 获取jedis实例
	 */
	public synchronized static Jedis getJedis() {
		try {
			if (jedisPool == null) {
				initPool();
			}
			Jedis jedis = jedisPool.getResource();
			jedis.auth(PASSWORD);// 密码
			return jedis;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}