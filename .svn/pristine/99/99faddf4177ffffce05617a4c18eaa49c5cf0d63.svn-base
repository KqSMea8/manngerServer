package com.lmxf.post.webservice.impl;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.JSONUtils;
import com.lmxf.post.core.utils.XmlValidator;
import com.lmxf.post.service.core.ErrorCodeService;
import com.lmxf.post.service.core.TradeDefineService;
import com.lmxf.post.service.site.TradeVendingEventService;
import com.lmxf.post.service.site.TradeVendingLaneService;
import com.lmxf.post.service.site.TradeVendingStateService;

/**
 * 上报接口
 * @author Administrator
 *
 */
public class SiteProcess {
	private final static Log log = LogFactory.getLog(SiteProcess.class);
	private String clientIp, clientHostname, clientProtocal;
	private XmlValidator xmlValidator;
	private ErrorCodeService errorCodeService;
	private TradeDefineService tradeDefineService;
	private TradeVendingEventService tradeVendingEventService;
	private TradeVendingStateService tradeVendingStateService;
	private TradeVendingLaneService tradeVendingLaneService;
	
	public void setRemoteAddress(String hostname, String ip, int port, String protocal) {
		this.clientIp = ip;
		this.clientHostname = hostname;
		this.clientProtocal = protocal;
	}

	public String report(String apply_xml) {
		log.error("apply_xml:" + apply_xml.replaceAll(">\\s+<", "><"));
		// 判断请求报文中是否包含SQL关键字
		String[] sensitiveKeys = { "select", "delete", "insert", "update", "distinct", "order by", "count(*)", "group by" };
		for (int i = 0; i < sensitiveKeys.length; i++) {
			boolean flag = apply_xml.toLowerCase().contains(sensitiveKeys[i]);
			if (flag) {
				return errorCodeService.getErrorRespJson(GConstent.Apply_Xml_Illegal);
			}
		}
		//检查交易码是否存在
		JSONObject JSONHeadObject = JSONUtils.getHeader(apply_xml);
		String plat_code = JSONHeadObject.getString(GConstent.bcode);
		String trade_code = JSONHeadObject.getString(GConstent.tcode);
		int ret = tradeDefineService.checkTrade(plat_code, trade_code);
		if (ret != 0) {
			log.error("initProcess.checkTrade  is error");
			return errorCodeService.getErrorRespJson(ret);
		}
		String repXml = null;
		try {
			//售货机事件上报
			if (trade_code.equals("1283")) {
				repXml = tradeVendingEventService.tradePrepare(apply_xml);
			}
			//售货机设备信息上报
			if (trade_code.equals("1284")) {
				repXml = tradeVendingStateService.tradePrepare(apply_xml);
			}
			//货道状态上报
			if (trade_code.equals("1282")) {
				repXml = tradeVendingLaneService.tradePrepare(apply_xml);
			}
		} catch (Exception e) {
			log.error("checkTrade is error :" + e.getClass() + "  " + e.getMessage());
			log.error("excception msg:" + errorCodeService.getErrorRespJson(GConstent.Program_App_Execp));
			return errorCodeService.getErrorRespJson(GConstent.Program_App_Execp);
		}
		log.error("trade_code:"+ trade_code + "   repXml:" + repXml.replaceAll(">\\s+<", "><"));
		return repXml;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public void setClientHostname(String clientHostname) {
		this.clientHostname = clientHostname;
	}

	public void setClientProtocal(String clientProtocal) {
		this.clientProtocal = clientProtocal;
	}


	public void setXmlValidator(XmlValidator xmlValidator) {
		this.xmlValidator = xmlValidator;
	}

	public void setErrorCodeService(ErrorCodeService errorCodeService) {
		this.errorCodeService = errorCodeService;
	}

	public void setTradeDefineService(TradeDefineService tradeDefineService) {
		this.tradeDefineService = tradeDefineService;
	}

	public void setTradeVendingEventService(
			TradeVendingEventService tradeVendingEventService) {
		this.tradeVendingEventService = tradeVendingEventService;
	}

	public void setTradeVendingStateService(
			TradeVendingStateService tradeVendingStateService) {
		this.tradeVendingStateService = tradeVendingStateService;
	}

	public void setTradeVendingLaneService(
			TradeVendingLaneService tradeVendingLaneService) {
		this.tradeVendingLaneService = tradeVendingLaneService;
	}
		
}
