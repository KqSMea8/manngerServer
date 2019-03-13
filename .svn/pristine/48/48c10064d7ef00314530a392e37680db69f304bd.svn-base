package com.lmxf.post.service.site;

import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.putils.utils.DateUtil;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.entity.vending.Vending;
import com.lmxf.post.entity.vending.VendingEvent;
import com.lmxf.post.repository.parameter.SequenceIdDao;
import com.lmxf.post.repository.vending.VendingDao;
import com.lmxf.post.repository.vending.VendingEventDao;
import com.lmxf.post.service.core.TradeProcess;
import com.lmxf.post.tradepkg.site.VendingEventReq;
import com.lmxf.post.tradepkg.site.VendingEventResp;

public class TradeVendingEventService extends TradeProcess {
	private final static Log log = LogFactory.getLog(TradeVendingEventService.class);
	private VendingEventReq vendingEventReq;
	private VendingEventResp vendingEventResp;
	private VendingEventDao vendingEventDao;
	private SequenceIdDao sequenceIdDao;
	private VendingDao vendingDao;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String tradeProcess(String apply_xml) {
		Map ret = null;
		VendingEvent vendingEvent = null;
		try {
			ret = vendingEventReq.parseXml(apply_xml);
			if (ret.get("code") == null) {
				return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
			} else if (ret.get("code") != null && Integer.parseInt(ret.get("code").toString()) != 0) {
				log.error("applyReservePersonFewReq.parseXml is error!");
				return errorCodeDao.getErrorRespJson(Integer.parseInt(ret.get("code").toString()));
			}
		} catch (Exception e) {
			log.error("---parseXml  error-----", e);
			return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
		}
		vendingEvent = (VendingEvent) ret.get("vendingEvent");
		VendingEvent vendingEventR=new VendingEvent();
		vendingEventR.setSiteId(vendingEvent.getSiteId());
		vendingEventR.setEventTId(vendingEvent.getEventTId());
		VendingEvent vendingEventP = vendingEventDao.findOne(vendingEventR);
		if(null != vendingEventP){
			return errorCodeDao.getErrorRespJson(GConstent.Vending_Event_Exsit);
		}
		Vending vending=new Vending();
		vending=vendingDao.findBySiteId(vendingEvent.getSiteId());
		if(null == vending){
			return errorCodeDao.getErrorRespJson(GConstent.Site_No_Define);
		}
		vendingEvent.setLogid(DateUtil.getLogid());
		vendingEvent.setEventId(vending.getCorpId()+"-"+this.sequenceIdDao.findNextVal(vending.getCorpId(), "as_vending_event",7));
		vendingEvent.setDistrictId(vending.getDistrictId());
		vendingEvent.setLineId(vending.getLineId());
		vendingEvent.setPointId(vending.getPointId());
		vendingEvent.setSiteName(vending.getSiteName());
		vendingEvent.setCorpId(vending.getCorpId());
		this.vendingEventDao.insert(vendingEvent);
		return vendingEventResp.CreateJson(GConstent.SUCCESS_CODE, GConstent.SUCCESS_MSG, 1, 1);
	}

	public void setVendingEventReq(VendingEventReq vendingEventReq) {
		this.vendingEventReq = vendingEventReq;
	}

	public void setVendingEventResp(VendingEventResp vendingEventResp) {
		this.vendingEventResp = vendingEventResp;
	}

	public void setVendingEventDao(VendingEventDao vendingEventDao) {
		this.vendingEventDao = vendingEventDao;
	}

	public void setSequenceIdDao(SequenceIdDao sequenceIdDao) {
		this.sequenceIdDao = sequenceIdDao;
	}

	public void setVendingDao(VendingDao vendingDao) {
		this.vendingDao = vendingDao;
	}
	
}
