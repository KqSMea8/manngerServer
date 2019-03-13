package com.lmxf.post.webservice.impl;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lmxf.post.core.utils.*;
import com.lmxf.post.service.core.ErrorCodeService;
import com.lmxf.post.service.core.TradeDefineService;
import com.lmxf.post.service.init.TradeEmpAllIssuedService;
import com.lmxf.post.service.init.TradeOrderAllIssuedService;
import com.lmxf.post.service.init.TradeStrategyInfoService;
import com.lmxf.post.service.init.TradeVendingQueryService;
import com.lmxf.post.service.partner.TradeLaneProductInfoService;
import com.lmxf.post.service.partner.TradeProductClassifyService;

/**
 * 终端初始化接口
 * 
 * @author Administrator
 * 
 */
public class InitProcess {
	private final static Log log = LogFactory.getLog(InitProcess.class);
	private String clientIp, clientHostname, clientProtocal;
	private XmlValidator xmlValidator;
	private ErrorCodeService errorCodeService;
	private TradeDefineService tradeDefineService;
	private TradeVendingQueryService tradeVendingQueryService;// 售货机基本信息查询
	private TradeLaneProductInfoService tradeLaneProductInfoService;// 售货机货道商品信息查询
	private TradeProductClassifyService tradeProductClassifyService;// 商品分类信息查询
	private TradeEmpAllIssuedService tradeEmpAllIssuedService;// 人员全量查询
	private TradeOrderAllIssuedService tradeOrderAllIssuedService;// 订单全量查询
	private TradeStrategyInfoService tradeStrategyInfoService;// 广告全量查询

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
		// 检查交易码是否存在
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
			// 售货机基本信息查询
			if (trade_code.equals("1201")) {
				repXml = tradeVendingQueryService.tradePrepare(apply_xml);
			}
			// 商品分类查询
			if (trade_code.equals("1202")) {
				repXml = tradeProductClassifyService.tradePrepare(apply_xml);
			}
			// 售货机货道商品查询
			if (trade_code.equals("1203")) {
				repXml = tradeLaneProductInfoService.tradePrepare(apply_xml);
			}
			// 人员全量查询
			if (trade_code.equals("1204")) {
				repXml = tradeEmpAllIssuedService.tradePrepare(apply_xml);
			}
			// 订单全量查询
			if (trade_code.equals("1205")) {
				repXml = tradeOrderAllIssuedService.tradePrepare(apply_xml);
			}
			// 广告全量查询
			if (trade_code.equals("1206")) {
				repXml = tradeStrategyInfoService.tradePrepare(apply_xml);
			}

		} catch (Exception e) {
			log.error("checkTrade is error :" + e.getClass() + "  " + e.getMessage());
			log.error("excception msg:" + errorCodeService.getErrorRespJson(GConstent.Program_App_Execp));
			return errorCodeService.getErrorRespJson(GConstent.Program_App_Execp);
		}
		log.error("trade_code:" + trade_code + "   repXml:" + repXml.replaceAll(">\\s+<", "><"));
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

	public void setTradeVendingQueryService(TradeVendingQueryService tradeVendingQueryService) {
		this.tradeVendingQueryService = tradeVendingQueryService;
	}

	public void setTradeLaneProductInfoService(TradeLaneProductInfoService tradeLaneProductInfoService) {
		this.tradeLaneProductInfoService = tradeLaneProductInfoService;
	}

	public void setTradeProductClassifyService(TradeProductClassifyService tradeProductClassifyService) {
		this.tradeProductClassifyService = tradeProductClassifyService;
	}

	public void setTradeEmpAllIssuedService(TradeEmpAllIssuedService tradeEmpAllIssuedService) {
		this.tradeEmpAllIssuedService = tradeEmpAllIssuedService;
	}

	public void setTradeOrderAllIssuedService(TradeOrderAllIssuedService tradeOrderAllIssuedService) {
		this.tradeOrderAllIssuedService = tradeOrderAllIssuedService;
	}

	public void setTradeStrategyInfoService(TradeStrategyInfoService tradeStrategyInfoService) {
		this.tradeStrategyInfoService = tradeStrategyInfoService;
	}

}
