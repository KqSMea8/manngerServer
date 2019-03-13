package com.lmxf.post.repository.stock;

import java.util.List;

import com.lmxf.post.entity.stock.StockInfo;
import org.apache.commons.putils.repository.BaseDao;

public class StockInfoDao extends BaseDao {

	public List<StockInfo> findAll(StockInfo stockInfo) {
		return this.getSqlSession().selectList("StockInfo.findAll",stockInfo);
	}
	public StockInfo findOne(StockInfo stockInfo) {
		return this.getSqlSession().selectOne("StockInfo.findOne",stockInfo);
	}
	
}
