package com.lmxf.post.webservice.impl;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lmxf.post.core.utils.*;
import com.lmxf.post.service.core.ErrorCodeService;
import com.lmxf.post.service.core.TradeDefineService;
import com.lmxf.post.service.order.TradeOrderDeliveryService;
import com.lmxf.post.service.order.TradeOrderFetchService;
import com.lmxf.post.service.order.TradeOrderInvalidService;
import com.lmxf.post.service.order.TradeOrderOutService;
import com.lmxf.post.service.order.TradeOrderRecoverService;
import com.lmxf.post.service.order.TradeSCMOrderDeliveryPayService;
import com.lmxf.post.service.order.TradeSCMOrderDeliveryService;

/**
 * 终端订单模块接口
 * @author Administrator
 *
 */
public class OrderProcess {
	private final static Log log = LogFactory.getLog(OrderProcess.class);
	private String clientIp, clientHostname, clientProtocal;
	private XmlValidator xmlValidator;
	private ErrorCodeService errorCodeService;
	private TradeDefineService tradeDefineService;
	private TradeOrderDeliveryService tradeOrderDeliveryService;//终端订单申请
	private TradeOrderInvalidService tradeOrderInvalidService;//终端订单取消
	private TradeOrderOutService tradeOrderOutService;//订单出柜
	private TradeOrderFetchService tradeOrderFetchService;//订单已取走
	private TradeOrderRecoverService tradeOrderRecoverService;//订单正常回收
	private TradeSCMOrderDeliveryService tradeSCMOrderDeliveryService;//单片机系统本地订单-无需支付
	private TradeSCMOrderDeliveryPayService tradeSCMOrderDeliveryPayService;//单片机系统本地订单-需要支付

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
			log.error("orderProcess.checkTrade  is error");
			return errorCodeService.getErrorRespJson(ret);
		}
		String repXml = null;
		try {
			//终端订单申请
			if (trade_code.equals("1241")) {
				repXml = tradeOrderDeliveryService.tradePrepare(apply_xml);
			}
			//终端订单取消
			if (trade_code.equals("1242")) {
				repXml = tradeOrderInvalidService.tradePrepare(apply_xml);
			}
			//订单出柜
			if (trade_code.equals("1243")) {
				repXml = tradeOrderOutService.tradePrepare(apply_xml);
			}
			//订单已取走
			if (trade_code.equals("1244")) {
				repXml = tradeOrderFetchService.tradePrepare(apply_xml);
			}
			//订单正常回收
			if (trade_code.equals("1245")) {
				repXml = tradeOrderRecoverService.tradePrepare(apply_xml);
			}
			//RFID柜型申请订单
			if (trade_code.equals("1246")) {
				repXml = tradeSCMOrderDeliveryService.tradePrepare(apply_xml);
			}
			//单片机本地订单-需支付
			if (trade_code.equals("1247")) {
				repXml = tradeSCMOrderDeliveryPayService.tradePrepare(apply_xml);
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

	public void setTradeOrderDeliveryService(
			TradeOrderDeliveryService tradeOrderDeliveryService) {
		this.tradeOrderDeliveryService = tradeOrderDeliveryService;
	}

	public void setTradeOrderInvalidService(
			TradeOrderInvalidService tradeOrderInvalidService) {
		this.tradeOrderInvalidService = tradeOrderInvalidService;
	}

	public void setTradeOrderOutService(TradeOrderOutService tradeOrderOutService) {
		this.tradeOrderOutService = tradeOrderOutService;
	}

	public void setTradeOrderFetchService(
			TradeOrderFetchService tradeOrderFetchService) {
		this.tradeOrderFetchService = tradeOrderFetchService;
	}

	public void setTradeOrderRecoverService(
			TradeOrderRecoverService tradeOrderRecoverService) {
		this.tradeOrderRecoverService = tradeOrderRecoverService;
	}

	public void setTradeSCMOrderDeliveryService(
			TradeSCMOrderDeliveryService tradeSCMOrderDeliveryService) {
		this.tradeSCMOrderDeliveryService = tradeSCMOrderDeliveryService;
	}

	public void setTradeSCMOrderDeliveryPayService(
			TradeSCMOrderDeliveryPayService tradeSCMOrderDeliveryPayService) {
		this.tradeSCMOrderDeliveryPayService = tradeSCMOrderDeliveryPayService;
	}
}
