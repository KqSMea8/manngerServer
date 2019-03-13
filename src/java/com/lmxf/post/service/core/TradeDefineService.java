package com.lmxf.post.service.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.lmxf.post.repository.config.TradeDefineDao;
import com.lmxf.post.entity.config.*;
import com.lmxf.post.core.utils.*;

public class TradeDefineService {
	private final static Log log = LogFactory.getLog(TradeDefineService.class);
	private TradeDefineDao tradeDefineDao;

	public int checkTrade(String plat_code, String trade_code) {
		TradeDefine tradeDefine;
		try {
			tradeDefine = tradeDefineDao.findOne(trade_code);
		} catch (Exception e) {
			log.error("--- tradeDefineDao findOne error---:" + e.getMessage());
			return GConstent.Program_App_Execp;
		}
		if (tradeDefine == null) {
			log.error("--- trade_code not define! ---trade_code:" + trade_code);
			return GConstent.Trade_Code_No_Define;
		}
//		if (!tradeDefine.getPlatCode().equals(plat_code)) {
//			log.error("--- getPlat_code()   is error! ---trade_code:" + trade_code);
//			return GConstent.Plat_Code_No_Define;
//		}
		return 0;

	}
	
	public TradeDefineDao getTradeDefineDao() {
		return tradeDefineDao;
	}

	public void setTradeDefineDao(TradeDefineDao tradeDefineDao) {
		this.tradeDefineDao = tradeDefineDao;
	}

}
