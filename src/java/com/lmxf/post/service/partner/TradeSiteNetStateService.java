package com.lmxf.post.service.partner;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.putils.utils.DateUtil;

import com.lmxf.post.core.utils.CacheUtils;
import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.GParameter;
import com.lmxf.post.core.utils.redis.RedisOps;
import com.lmxf.post.entity.vending.Vending;
import com.lmxf.post.repository.vending.VendingDao;
import com.lmxf.post.service.core.TradeProcess;
import com.lmxf.post.tradepkg.partner.SiteNetStateReq;
import com.lmxf.post.tradepkg.partner.SiteNetStateResp;
import com.manage.project.system.index.vo.OperateReviewVo;
public class TradeSiteNetStateService extends TradeProcess {
	private final static Log log = LogFactory.getLog(TradeSiteNetStateService.class);
	private SiteNetStateReq siteNetStateReq;
	private SiteNetStateResp siteNetStateResp;
	private VendingDao vendingDao;

	@SuppressWarnings("rawtypes")
	public String tradeProcess(String apply_xml) {
		Map ret = siteNetStateReq.parseXml(apply_xml);
		if (ret.get("code") == null) {
			return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
		} else if (ret.get("code") != null && Integer.parseInt(ret.get("code").toString()) != 0) {
			log.error("siteNetStateReq.parseXml is error!");
			return errorCodeDao.getErrorRespJson(Integer.parseInt(ret.get("code").toString()));
		}
		Vending vendingP = (Vending) ret.get("vending");
		Vending vending = vendingDao.findBySiteId(vendingP.getSiteId());
		if (vending == null) {
			return errorCodeDao.getErrorRespJson(GConstent.Site_No_Define);
		}
		vending.setNetSate(vendingP.getNetSate());
		vending.setNetTime(DateUtil.getNow());
		// 如果未认证的机器则默认设置为已认证
		if (GParameter.vendingCurState_noValidate.equals(vending.getCurState())) {
			vending.setCurState(GParameter.vendingCurState_yesValidate);
		}
		vendingDao.update(vending);
		try {
			OperateReviewVo operateReviewVo =(OperateReviewVo) RedisOps.getObject(CacheUtils.INDEX_SUMMARY_TOTALREVIEW_CACHE_ + vending.getCorpId());
			log.debug("获取公司:" + vending.getCorpId() + " 仪表盘总览缓存对象:" + operateReviewVo+"  在线数:"+operateReviewVo.getOnlineMachine()+" 离线数:"+operateReviewVo.getOutlineMachine()+" 当前站点数:"+(GParameter.siteNetState_offline.equals(vending.getNetSate())?"在线":"离线")+" 总利润:"+operateReviewVo.getTotalProfit()+" 总营业额:"+operateReviewVo.getTotalSale()+" 总销量:"+operateReviewVo.getTotalSaleNum());
			if (operateReviewVo != null) {
				Vending vendingPP=new Vending();
				vendingPP.setNetSate(GParameter.siteNetState_online);
				vendingPP.setCorpId(vending.getCorpId());
				Vending vendingPR=this.vendingDao.findNetState(vendingPP);
				if (vendingPR!=null) {
					int onlineNum=vendingPR.getLaneNum();
					operateReviewVo.setOnlineMachine(String.valueOf(onlineNum));
				}
				vendingPP.setNetSate(GParameter.siteNetState_offline);
				vendingPR=this.vendingDao.findNetState(vendingPP);
				if (vendingPR!=null) {
					int onlineNum=vendingPR.getLaneNum();
					operateReviewVo.setOutlineMachine(String.valueOf(onlineNum));
				}
				log.debug("获取公司:" + vending.getCorpId() + " 仪表盘总览缓存对象:" + operateReviewVo+"  在线数:"+operateReviewVo.getOnlineMachine()+" 离线数:"+operateReviewVo.getOutlineMachine()+" 当前站点数:"+(GParameter.siteNetState_offline.equals(vending.getNetSate())?"在线":"离线")+" 总利润:"+operateReviewVo.getTotalProfit()+" 总营业额:"+operateReviewVo.getTotalSale()+" 总销量:"+operateReviewVo.getTotalSaleNum());
				RedisOps.setObject(CacheUtils.INDEX_SUMMARY_TOTALREVIEW_CACHE_ + vending.getCorpId(), operateReviewVo);
			} else {
				log.error("获取公司的销售总览缓存对象出错:" + CacheUtils.INDEX_SUMMARY_TOTALREVIEW_CACHE_ + vending.getCorpId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("设置缓存总览统计出错:" + e.getMessage());
		}
		return siteNetStateResp.CreateJson(GConstent.SUCCESS_CODE, GConstent.SUCCESS_MSG, 1, 1);
	}

	public void setSiteNetStateReq(SiteNetStateReq siteNetStateReq) {
		this.siteNetStateReq = siteNetStateReq;
	}

	public void setSiteNetStateResp(SiteNetStateResp siteNetStateResp) {
		this.siteNetStateResp = siteNetStateResp;
	}

	public void setVendingDao(VendingDao vendingDao) {
		this.vendingDao = vendingDao;
	}


}
