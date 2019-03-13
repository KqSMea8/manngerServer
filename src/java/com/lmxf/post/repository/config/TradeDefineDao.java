package com.lmxf.post.repository.config;
import com.lmxf.post.entity.config.TradeDefine;
import org.apache.commons.putils.repository.BaseDao;
public class TradeDefineDao extends BaseDao{
	public TradeDefine  findOne(String trade_code)
	{
		TradeDefine  tradeDefine = this.getSqlSession().selectOne("TradeDefine.findOne", trade_code);
		return tradeDefine;
	}

}
