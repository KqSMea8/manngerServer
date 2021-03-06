package com.lmxf.post.service.site;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.putils.utils.DateUtil;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.GParameter;
import com.lmxf.post.entity.vending.Vending;
import com.lmxf.post.entity.vending.VendingState;
import com.lmxf.post.repository.vending.VendingDao;
import com.lmxf.post.repository.vending.VendingStateDao;
import com.lmxf.post.service.core.TradeProcess;
import com.lmxf.post.tradepkg.site.VendingStateReq;
import com.lmxf.post.tradepkg.site.VendingStateResp;

public class TradeVendingStateService extends TradeProcess {
	private final static Log log = LogFactory.getLog(TradeVendingStateService.class);
	private VendingStateReq vendingStateReq;
	private VendingStateResp vendingStateResp;
	private VendingStateDao vendingStateDao;
	private VendingDao vendingDao;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String tradeProcess(String apply_xml) {
		Map ret = null;
		VendingState vendingState = null;
		try {
			ret = vendingStateReq.parseXml(apply_xml);
			if (ret.get("code") == null) {
				return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
			} else if (ret.get("code") != null && Integer.parseInt(ret.get("code").toString()) != 0) {
				log.error("VendingStateReq.parseXml is error!");
				return errorCodeDao.getErrorRespJson(Integer.parseInt(ret.get("code").toString()));
			}
		} catch (Exception e) {
			log.error("---parseXml  error-----", e);
			return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
		}
		vendingState = (VendingState) ret.get("vendingState");
		Vending vending = new Vending();
		vending = vendingDao.findBySiteId(vendingState.getSiteId());
		if (null == vending) {
			return errorCodeDao.getErrorRespJson(GConstent.Site_No_Define);
		}
		if (!"0".equals(vendingState.getLongitude())) {
			vending.setLongitude(vendingState.getLongitude());
			vending.setLatitude(vendingState.getLatitude());
			if (vendingState.getvAndroid() != null && vendingState.getvAndroid().contains("android.")) {
				vending.setPayType(GParameter.platType_android);
			} else if (vendingState.getvAndroid() != null && vendingState.getvAndroid().contains("windows.")) {
				vending.setPayType(GParameter.platType_windows);
			}
			this.vendingDao.update(vending);
		}

		VendingState vendingStateR = vendingStateDao.findBySiteId(vendingState.getSiteId());
		if (null != vendingStateR) {
			vendingStateR.setSeqId(vendingState.getSeqId());
			vendingStateR.setvFirmware(vendingState.getvFirmware());
			vendingStateR.setvVCS(vendingState.getvVCS());
			vendingStateR.setSignalValue(vendingState.getSignalValue());
			vendingStateR.setIccid(vendingState.getIccid());
			vendingStateR.setResoution(vendingState.getResoution());
			vendingStateR.setScreenType(vendingState.getScreenType());
			vendingStateR.setvAndroid(vendingState.getvAndroid());
			vendingStateR.setvBaseband(vendingState.getvBaseband());
			vendingStateR.setIpAddress(vendingState.getIpAddress());
			this.vendingStateDao.update(vendingStateR);
		} else {
			vendingState.setLogid(DateUtil.getLogid());
			vendingState.setSiteName(vendingState.getSiteName());
			vendingState.setIpAddress(vendingState.getIpAddress());
			vendingState.setContime(vendingState.getContime());
			vendingState.setLoseContime(vendingState.getLoseContime());
			vendingState.setReportTime(vendingState.getReportTime());
			vendingState.setNetSate(vendingState.getNetSate());
			vendingState.setCorpId(vendingState.getCorpId());
			vendingStateR.setIpAddress(vendingState.getIpAddress());
			this.vendingStateDao.insert(vendingState);
		}
		return vendingStateResp.CreateJson(GConstent.SUCCESS_CODE, GConstent.SUCCESS_MSG, 1, 1);
	}

	public void setVendingStateReq(VendingStateReq vendingStateReq) {
		this.vendingStateReq = vendingStateReq;
	}

	public void setVendingStateResp(VendingStateResp vendingStateResp) {
		this.vendingStateResp = vendingStateResp;
	}

	public void setVendingStateDao(VendingStateDao vendingStateDao) {
		this.vendingStateDao = vendingStateDao;
	}

	public void setVendingDao(VendingDao vendingDao) {
		this.vendingDao = vendingDao;
	}

}
