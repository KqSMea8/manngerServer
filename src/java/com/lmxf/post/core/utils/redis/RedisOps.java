package com.lmxf.post.core.utils.redis;

import java.util.ArrayList;
import java.util.List;

import com.lmxf.post.core.utils.CacheUtils;
import com.manage.project.system.index.vo.OneMonthReviewVo;
import com.manage.project.system.index.vo.OperateReviewVo;

import redis.clients.jedis.Jedis;

/**
 * redis持久化数据工具
 * 
 * @author 思杰
 * 
 */
public class RedisOps {
	public static void set(String key, String value) {
		Jedis jedis = RedisConnection.getJedis();
		jedis.set(key, value);
		jedis.close();
	}

	public static String get(String key) {
		Jedis jedis = RedisConnection.getJedis();
		String value = jedis.get(key);
		jedis.close();
		return value;
	}

	public static void setObject(String key, Object object) {
		Jedis jedis = RedisConnection.getJedis();
		jedis.set(key.getBytes(), SerializeUtil.serizlize(object));
		jedis.close();
	}

	public static Object getObject(String key) {
		Jedis jedis = RedisConnection.getJedis();
		byte[] bytes = jedis.get(key.getBytes());
		jedis.close();
		return SerializeUtil.deserialize(bytes);
	}

	public static void main(String[] args) {
		// OperateReviewVo operateReviewVo=new OperateReviewVo();
		// operateReviewVo.setOnlineMachine("10");
		// operateReviewVo.setOutlineMachine("10");
		// operateReviewVo.setTotalProfit("10.20");
		// operateReviewVo.setTotalSale("124.02");
		// operateReviewVo.setTotalSaleNum("100");
		// RedisOps.setObject(CacheUtils.INDEX_SUMMARY_TOTALREVIEW_CACHE_+"8886",operateReviewVo);
		// List<OneMonthReviewVo> oneMonthReviewVoList=new
		// ArrayList<OneMonthReviewVo>();
		// OneMonthReviewVo oneMonthReviewVo=new OneMonthReviewVo();
		// oneMonthReviewVo.setType(1);
		// oneMonthReviewVo.setTotalProfit("10.20");
		// oneMonthReviewVo.setTotalSale("124.02");
		// oneMonthReviewVo.setTotalSaleNum("100");
		// oneMonthReviewVoList.add(oneMonthReviewVo);
		// RedisOps.setObject(CacheUtils.INDEX_SUMMARY_ONEMONTH_CACHE_+"8886",oneMonthReviewVoList);
		OperateReviewVo operateReviewVo = (OperateReviewVo) RedisOps.getObject(CacheUtils.INDEX_SUMMARY_TOTALREVIEW_CACHE_ + "8886");
		System.out.println(" 总利润:" + operateReviewVo.getTotalProfit() + " 总营业额:" + operateReviewVo.getTotalSale() + " 总销量:" + operateReviewVo.getTotalSaleNum() );
		
		List<OneMonthReviewVo> oneMonthReviewVoList = (List<OneMonthReviewVo>) RedisOps.getObject(CacheUtils.INDEX_SUMMARY_ONEMONTH_CACHE_  + "8886");
		OneMonthReviewVo oneMonthReviewVo = null;
		if (oneMonthReviewVoList != null) {
			for (OneMonthReviewVo oneMonthReviewVoT : oneMonthReviewVoList) {
				if (oneMonthReviewVoT.getType() == 1) {
					oneMonthReviewVo = oneMonthReviewVoT;
				}
			}
		}
		System.out.println(" 总利润:" + oneMonthReviewVo.getTotalProfit() + " 总营业额:" + oneMonthReviewVo.getTotalSale() + " 总销量:" + oneMonthReviewVo.getTotalSaleNum() );
	}
}
