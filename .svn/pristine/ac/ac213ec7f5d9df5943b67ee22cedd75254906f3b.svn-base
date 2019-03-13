package com.lmxf.post.service.site;

import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.entity.vending.VendingLane;
import com.lmxf.post.entity.vending.VendingLanep;
import com.lmxf.post.repository.vending.VendingLaneDao;
import com.lmxf.post.repository.vending.VendingLanepDao;
import com.lmxf.post.service.core.TradeProcess;
import com.lmxf.post.tradepkg.site.VendingLaneReq;
import com.lmxf.post.tradepkg.site.VendingLaneResp;

public class TradeVendingLaneService extends TradeProcess {

	private final static Log log = LogFactory.getLog(TradeVendingLaneService.class);
	private VendingLaneReq vendingLaneReq;
	private VendingLaneResp vendingLaneResp;
	private VendingLaneDao vendingLaneDao;
	private VendingLanepDao vendingLanepDao;

	public String tradeProcess(String apply_xml) {
		Map ret = null;
		String state, laneState;
		try {
			ret = vendingLaneReq.parseXml(apply_xml);
			if (ret.get("code") == null) {
				return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
			} else if (ret.get("code") != null && Integer.parseInt(ret.get("code").toString()) != 0) {
				log.error("applyReservePersonFewReq.parseXml is error!");
				return errorCodeDao.getErrorRespJson(Integer.parseInt(ret.get("code").toString()));
			}
		} catch (Exception e) {
			log.error("---parseXml  error---:" + e.getMessage());
			return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
		}
		state = (String) ret.get("state");
		String operTime = (String) ret.get("operTime");
		if (state == null) {
			log.error("---state is null---:");
			return errorCodeDao.getErrorRespJson(GConstent.Program_App_Execp);
		}
		VendingLane vendingLane = new VendingLane();
		vendingLane.setSiteId((String) ret.get("siteId"));
		List<VendingLane> vendingLaneList = vendingLaneDao.findAll(vendingLane);
		if ((null == vendingLaneList) || (vendingLaneList.size() < 1)) {
			return errorCodeDao.getErrorRespJson(GConstent.Supply_Vending_No_Found);
		}
		if (state.length() != vendingLaneList.size()) {
			log.error("state length !=" + vendingLaneList.size() + "length=" + state.length());
			return errorCodeDao.getErrorRespJson(GConstent.SiteState_Length_Error);
		}
		VendingLanep vendingLanep = new VendingLanep();
		vendingLanep.setSiteId((String) ret.get("siteId"));
		List<VendingLanep> vendingLanepList = vendingLanepDao.findAll(vendingLanep);
		if ((null == vendingLanepList) || (vendingLanepList.size() < 1)) {
			return errorCodeDao.getErrorRespJson(GConstent.Supply_Vending_No_Found);
		}
		// 修改货道信息
		VendingLane vendingLaneP = null;
		for (int i = 0; i < vendingLaneList.size(); i++) {
			laneState = state.substring(i, i + 1);
			vendingLaneP = vendingLaneList.get(i);
			log.info("i=" + i + " boxtsate:" + laneState);
			if (!vendingLaneP.getCurState().equals(laneState)) {
				vendingLaneP.setCurState(laneState);
				vendingLaneP.setStateTime(operTime);
				this.vendingLaneDao.update(vendingLaneP);
			}
		}
		// 修改货道商品信息
		VendingLanep vendingLaneR = null;
		for (int i = 0; i < vendingLanepList.size(); i++) {
			laneState = state.substring(i, i + 1);
			log.info("i=" + i + " boxtsate:" + laneState);
			vendingLaneR = vendingLanepList.get(i);
			if (!vendingLaneR.getLaneSate().equals(laneState)) {
				vendingLaneR.setLaneSate(laneState);
				this.vendingLanepDao.update(vendingLaneR);
			}
		}
		return vendingLaneResp.CreateJson(GConstent.SUCCESS_CODE, GConstent.SUCCESS_MSG, 1, 1);
	}

	public void setVendingLaneReq(VendingLaneReq vendingLaneReq) {
		this.vendingLaneReq = vendingLaneReq;
	}

	public void setVendingLaneResp(VendingLaneResp vendingLaneResp) {
		this.vendingLaneResp = vendingLaneResp;
	}

	public void setVendingLaneDao(VendingLaneDao vendingLaneDao) {
		this.vendingLaneDao = vendingLaneDao;
	}

	public void setVendingLanepDao(VendingLanepDao vendingLanepDao) {
		this.vendingLanepDao = vendingLanepDao;
	}

}
