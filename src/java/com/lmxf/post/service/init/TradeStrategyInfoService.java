package com.lmxf.post.service.init;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.putils.utils.DateUtil;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.GParameter;
import com.lmxf.post.entity.add.FavourableObject;
import com.lmxf.post.entity.increment.AdvertDevice;
import com.lmxf.post.entity.increment.AdvertMStrategy;
import com.lmxf.post.entity.increment.AdvertStrategy;
import com.lmxf.post.entity.vending.Vending;
import com.lmxf.post.entity.vending.VendingState;
import com.lmxf.post.repository.add.FavourableObjectDao;
import com.lmxf.post.repository.increment.AdvertDeviceDao;
import com.lmxf.post.repository.increment.AdvertMStrategyDao;
import com.lmxf.post.repository.increment.AdvertStrategyDao;
import com.lmxf.post.repository.vending.VendingDao;
import com.lmxf.post.repository.vending.VendingStateDao;
import com.lmxf.post.service.core.TradeProcess;
import com.lmxf.post.tradepkg.init.StrategyInfoReq;
import com.lmxf.post.tradepkg.init.StrategyInfoResp;

/**
 * 广告广播信息获取
 * 
 * @author 思杰
 * 
 */
public class TradeStrategyInfoService extends TradeProcess {
	private final static Log log = LogFactory.getLog(TradeStrategyInfoService.class);
	private StrategyInfoReq strategyInfoReq;
	private StrategyInfoResp strategyInfoResp;
	private AdvertDeviceDao advertDeviceDao;
	private AdvertStrategyDao advertStrategyDao;
	private AdvertMStrategyDao advertMStrategyDao;

	@SuppressWarnings("rawtypes")
	public String tradeProcess(String apply_xml) {
		Map ret = strategyInfoReq.parseXml(apply_xml);
		if (ret.get("code") == null) {
			return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
		} else if (ret.get("code") != null && Integer.parseInt(ret.get("code").toString()) != 0) {
			log.error("strategyInfoReq.parseXml is error!");
			return errorCodeDao.getErrorRespJson(Integer.parseInt(ret.get("code").toString()));
		}
		Vending vendingP = (Vending) ret.get("vending");
		//保存响应结果信息
		List<AdvertStrategy> advertStrategyListAll=new ArrayList<AdvertStrategy>();
		//获取广告信息
		AdvertDevice advertDeviceP=new AdvertDevice();
		advertDeviceP.setDeviceId(vendingP.getSiteId());
		advertDeviceP.setCurState(GParameter.advertDeviceCurState_executing);
        List<AdvertDevice> advertDeviceList=this.advertDeviceDao.findAll(advertDeviceP);
        for(AdvertDevice advertDevice:advertDeviceList){
        	AdvertStrategy advertStrategyP=new AdvertStrategy();
        	advertStrategyP.setAdvertId(advertDevice.getAdvertId());
        	List<AdvertStrategy> advertStrategyList=this.advertStrategyDao.findAll(advertStrategyP);
        	for(AdvertStrategy advertStrategy:advertStrategyList){
        		AdvertMStrategy advertMStrategyP=new AdvertMStrategy();
        		advertMStrategyP.setStrategyId(advertStrategy.getStrategyId());
        		List<AdvertMStrategy> advertMStrategyList=this.advertMStrategyDao.findAll(advertMStrategyP);
        		advertStrategy.setAdvertMStrategyList(advertMStrategyList);
        	}
        	advertStrategyListAll.addAll(advertStrategyList);
        }
		return strategyInfoResp.CreateJson(GConstent.SUCCESS_CODE, GConstent.SUCCESS_MSG, 1, 1, advertStrategyListAll);
	}

	public void setStrategyInfoReq(StrategyInfoReq strategyInfoReq) {
		this.strategyInfoReq = strategyInfoReq;
	}

	public void setStrategyInfoResp(StrategyInfoResp strategyInfoResp) {
		this.strategyInfoResp = strategyInfoResp;
	}

	public void setAdvertDeviceDao(AdvertDeviceDao advertDeviceDao) {
		this.advertDeviceDao = advertDeviceDao;
	}

	public void setAdvertStrategyDao(AdvertStrategyDao advertStrategyDao) {
		this.advertStrategyDao = advertStrategyDao;
	}

	public void setAdvertMStrategyDao(AdvertMStrategyDao advertMStrategyDao) {
		this.advertMStrategyDao = advertMStrategyDao;
	}

}
