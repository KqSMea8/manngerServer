package com.lmxf.post.webservice.impl;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lmxf.post.core.exception.SystemException;
import com.lmxf.post.core.utils.*;
import com.lmxf.post.service.core.ErrorCodeService;
import com.lmxf.post.service.core.TradeDefineService;
import com.lmxf.post.service.partner.TradeOrderApplyLaneService;
import com.lmxf.post.service.partner.TradeOrderOpenService;
import com.lmxf.post.service.partner.TradeOrderPayService;
import com.lmxf.post.service.partner.TradeOrderRefundService;
import com.lmxf.post.service.partner.TradeOrderReportService;
import com.lmxf.post.service.partner.TradePayTestService;
import com.lmxf.post.service.partner.TradeSiteLanelsdifferService;
import com.lmxf.post.service.partner.TradeSitelsdhandleService;
import com.lmxf.post.service.partner.TradeUserInfoService;
import com.lmxf.post.service.partner.TradeVendingCmdService;
import com.lmxf.post.service.partner.TradeBoxOpenService;
import com.lmxf.post.service.partner.TradeBoxOpenStateService;
import com.lmxf.post.service.partner.TradeCustomerOrderInfoService;
import com.lmxf.post.service.partner.TradeDoorOpenService;
import com.lmxf.post.service.partner.TradeEmpAddService;
import com.lmxf.post.service.partner.TradeEmpDeleteService;
import com.lmxf.post.service.partner.TradeEmpEditService;
import com.lmxf.post.service.partner.TradeEmpInfoService;
import com.lmxf.post.service.partner.TradeLaneProductInfoService;
import com.lmxf.post.service.partner.TradeOrderApplyService;
import com.lmxf.post.service.partner.TradeOrderCancelService;
import com.lmxf.post.service.partner.TradeOrderChangeService;
import com.lmxf.post.service.partner.TradeProductClassifyService;
import com.lmxf.post.service.partner.TradeProductInfoService;
import com.lmxf.post.service.partner.TradeSiteNetStateService;
import com.lmxf.post.service.partner.TradeSupplyEventService;
import com.lmxf.post.service.partner.TradeSupplyInfoService;
import com.lmxf.post.service.partner.TradeSupplyResultService;
import com.lmxf.post.service.partner.TradeUnderInfoService;
import com.lmxf.post.service.partner.TradeUnderResultService;
import com.lmxf.post.service.partner.TradeVendingInfoService;
import com.lmxf.post.service.partner.TradeVendingPersonService;
import com.lmxf.post.service.partner.TradeVendingProductInfoService;

/**
 * 开放给第三方购买系统的接口
 * 
 * @author Administrator
 * 
 */
public class PartnerProcess {
	private final static Log log = LogFactory.getLog(PartnerProcess.class);
	private String clientIp, clientHostname, clientProtocal;
	private XmlValidator xmlValidator;
	private ErrorCodeService errorCodeService;
	private TradeDefineService tradeDefineService;
	private TradeVendingInfoService tradeVendingInfoService;// 售货机信息查询
	private TradeVendingProductInfoService tradeVendingProductInfoService;// 售货机商品信息查询
	private TradeOrderApplyService tradeOrderApplyService;// 售货机订单申请
	private TradeOrderCancelService tradeOrderCancelService;// 售货机订单取消
	private TradeOrderChangeService tradeOrderChangeService;// 售货机订单状态查询
	private TradeCustomerOrderInfoService tradeCustomerOrderInfoService;// 客户订单查询
	private TradeEmpInfoService tradeEmpInfoService;// 人员查询
	private TradeEmpAddService tradeEmpAddService;// 人员新增
	private TradeEmpEditService tradeEmpEditService;// 人员修改
	private TradeEmpDeleteService tradeEmpDeleteService;// 人员注销
	private TradeLaneProductInfoService tradeLaneProductInfoService;// 售货机货道商品信息查询
	private TradeSupplyInfoService tradeSupplyInfoService;// 人员补货信息查询
	private TradeSupplyResultService tradeSupplyResultService;// 人员补货信息上报
	private TradeUnderInfoService tradeUnderInfoService;// 人员下架信息查询
	private TradeUnderResultService tradeUnderResultService;// 人员下架信息上报
	private TradeDoorOpenService tradeDoorOpenService;// RFID柜型开柜门事件
	private TradeBoxOpenService tradeBoxOpenService;// Mini柜型格口开门事件（补货）
	private TradeSupplyEventService tradeSupplyEventService;// 补货事件
	private TradeBoxOpenStateService tradeBoxOpenStateService;// 售货机格口状态查询
	private TradeSiteNetStateService tradeSiteNetStateService;// 站点网络状态事件上报
	private TradeProductInfoService tradeProductInfoService;// 商品信息查询
	private TradeProductClassifyService tradeProductClassifyService;// 商品分类信息查询
	private TradeVendingPersonService tradeVendingPersonService;// 人员绑定
	private TradeVendingCmdService tradeVendingCmdService;// 售货机命令推送
	private TradeOrderOpenService tradeOrderOpenService;
	private TradeOrderReportService tradeOrderReportService;
	private TradeOrderPayService tradeOrderPayService;// 订单支付
	private TradeUserInfoService tradeUserInfoService;// 获取账户信息
	private TradeOrderRefundService tradeOrderRefundService;// 订单退款
	private TradePayTestService tradePayTestService;// 支付申请测试
	private TradeSiteLanelsdifferService tradeSiteLanelsdifferService;// 货道库存差异矫正
	private TradeSitelsdhandleService tradeSitelsdhandleService;//商品出库状态矫正接口
	private TradeOrderApplyLaneService tradeOrderApplyLaneService;//可指定货道申请订单接口

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
			log.error("partnerProcess.checkTrade  is error");
			return errorCodeService.getErrorRespJson(ret);
		}
		String repXml = null;
		try {
			// 售货机信息查询
			if (trade_code.equals("1001")) {
				repXml = tradeVendingInfoService.tradePrepare(apply_xml);
			}
			// 售货机商品信息查询
			if (trade_code.equals("1002")) {
				repXml = tradeVendingProductInfoService.tradePrepare(apply_xml);
			}
			// 售货机订单申请
			if (trade_code.equals("1003")) {
				repXml = tradeOrderApplyService.tradePrepare(apply_xml);
			}
			// 售货机订单取消
			if (trade_code.equals("1004")) {
				repXml = tradeOrderCancelService.tradePrepare(apply_xml);
			}
			// 售货机订单状态查询
			if (trade_code.equals("1005")) {
				repXml = tradeOrderChangeService.tradePrepare(apply_xml);
			}
			// 客户订单查询
			if (trade_code.equals("1006")) {
				repXml = tradeCustomerOrderInfoService.tradePrepare(apply_xml);
			}
			// 客户订单支付
			if (trade_code.equals("1007")) {
				repXml = tradeOrderPayService.tradePrepare(apply_xml);
			}// 退款
			if (trade_code.equals("1008")) {
				repXml = tradeOrderRefundService.tradePrepare(apply_xml);
			}// 支付测试
			if (trade_code.equals("1009")) {
				repXml = tradePayTestService.tradePrepare(apply_xml);
			}
			if (trade_code.equals("1010")) {
				repXml = tradeSitelsdhandleService.tradePrepare(apply_xml);
			}
			if (trade_code.equals("1011")) {
				repXml = tradeOrderApplyLaneService.tradePrepare(apply_xml);
			}
			// 人员新增
			if (trade_code.equals("1021")) {
				repXml = tradeEmpAddService.tradePrepare(apply_xml);
			}
			// 人员修改
			if (trade_code.equals("1022")) {
				repXml = tradeEmpEditService.tradePrepare(apply_xml);
			}
			// 人员删除
			if (trade_code.equals("1023")) {
				repXml = tradeEmpDeleteService.tradePrepare(apply_xml);
			}
			// 人员查询
			if (trade_code.equals("1024")) {
				repXml = tradeEmpInfoService.tradePrepare(apply_xml);
			}
			// 售货机货道商品查询
			if (trade_code.equals("1025")) {
				repXml = tradeLaneProductInfoService.tradePrepare(apply_xml);
			}
			// 人员补货信息查询
			if (trade_code.equals("1026")) {
				repXml = tradeSupplyInfoService.tradePrepare(apply_xml);
			}
			// 人员补货信息上报
			if (trade_code.equals("1027")) {
				repXml = tradeSupplyResultService.tradePrepare(apply_xml);
			}
			// 人员下架信息查询
			if (trade_code.equals("1028")) {
				repXml = tradeUnderInfoService.tradePrepare(apply_xml);
			}
			// 人员下架信息上报
			if (trade_code.equals("1029")) {
				repXml = tradeUnderResultService.tradePrepare(apply_xml);
			}
			// 人员绑定
			if (trade_code.equals("1030")) {
				repXml = tradeVendingPersonService.tradePrepare(apply_xml);
			}
			// 商品信息查询
			if (trade_code.equals("1041")) {
				repXml = tradeProductInfoService.tradePrepare(apply_xml);
			}
			// 商品分类查询
			if (trade_code.equals("1042")) {
				repXml = tradeProductClassifyService.tradePrepare(apply_xml);
			}

			// RFID柜型开柜门事件
			if (trade_code.equals("1050")) {
				repXml = tradeDoorOpenService.tradePrepare(apply_xml);
			}
			// Mini柜型格口开门事件（补货）
			if (trade_code.equals("1051")) {
				repXml = tradeBoxOpenService.tradePrepare(apply_xml);
			}
			// 补货事件
			if (trade_code.equals("1052")) {
				repXml = tradeSupplyEventService.tradePrepare(apply_xml);
			}
			// 售货机格口状态查询
			if (trade_code.equals("1053")) {
				repXml = tradeBoxOpenStateService.tradePrepare(apply_xml);
			}
			// 上报订单信息
			if (trade_code.equals("1302")) {
				repXml = tradeOrderReportService.tradePrepare(apply_xml);
			}
			// 上报订单开箱成功
			if (trade_code.equals("1304")) {
				repXml = tradeOrderOpenService.tradePrepare(apply_xml);
			}
			// 站点网络状态事件上报
			if (trade_code.equals("1060")) {
				repXml = tradeSiteNetStateService.tradePrepare(apply_xml);
			}
			// 站点网络状态事件上报
			if (trade_code.equals("1061")) {
				repXml = tradeSiteLanelsdifferService.tradePrepare(apply_xml);
			}
			// 售货机命令推送
			if (trade_code.equals("1227")) {
				repXml = tradeVendingCmdService.tradePrepare(apply_xml);
			}
			// 补货员查询
			if (trade_code.equals("1031")) {
				repXml = tradeUserInfoService.tradePrepare(apply_xml);
			}
		} catch (SystemException e) {
			e.printStackTrace();
			log.error("checkTrade is error :" + e.getClass() + "  " + e.getMessage());
			log.error("excception msg:" + errorCodeService.getErrorRespJson(GConstent.Program_App_Execp));
			return errorCodeService.getErrorRespJson(e.getCode());
		} catch (Exception e) {
			e.printStackTrace();
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

	public void setTradeVendingInfoService(TradeVendingInfoService tradeVendingInfoService) {
		this.tradeVendingInfoService = tradeVendingInfoService;
	}

	public void setTradeVendingProductInfoService(TradeVendingProductInfoService tradeVendingProductInfoService) {
		this.tradeVendingProductInfoService = tradeVendingProductInfoService;
	}

	public void setTradeOrderApplyService(TradeOrderApplyService tradeOrderApplyService) {
		this.tradeOrderApplyService = tradeOrderApplyService;
	}

	public void setTradeOrderCancelService(TradeOrderCancelService tradeOrderCancelService) {
		this.tradeOrderCancelService = tradeOrderCancelService;
	}

	public void setTradeOrderChangeService(TradeOrderChangeService tradeOrderChangeService) {
		this.tradeOrderChangeService = tradeOrderChangeService;
	}

	public void setTradeCustomerOrderInfoService(TradeCustomerOrderInfoService tradeCustomerOrderInfoService) {
		this.tradeCustomerOrderInfoService = tradeCustomerOrderInfoService;
	}

	public void setTradeLaneProductInfoService(TradeLaneProductInfoService tradeLaneProductInfoService) {
		this.tradeLaneProductInfoService = tradeLaneProductInfoService;
	}

	public void setTradeSupplyInfoService(TradeSupplyInfoService tradeSupplyInfoService) {
		this.tradeSupplyInfoService = tradeSupplyInfoService;
	}

	public void setTradeEmpInfoService(TradeEmpInfoService tradeEmpInfoService) {
		this.tradeEmpInfoService = tradeEmpInfoService;
	}

	public void setTradeEmpAddService(TradeEmpAddService tradeEmpAddService) {
		this.tradeEmpAddService = tradeEmpAddService;
	}

	public void setTradeSupplyResultService(TradeSupplyResultService tradeSupplyResultService) {
		this.tradeSupplyResultService = tradeSupplyResultService;
	}

	public void setTradeEmpEditService(TradeEmpEditService tradeEmpEditService) {
		this.tradeEmpEditService = tradeEmpEditService;
	}

	public void setTradeEmpDeleteService(TradeEmpDeleteService tradeEmpDeleteService) {
		this.tradeEmpDeleteService = tradeEmpDeleteService;
	}

	public void setTradeUnderInfoService(TradeUnderInfoService tradeUnderInfoService) {
		this.tradeUnderInfoService = tradeUnderInfoService;
	}

	public void setTradeUnderResultService(TradeUnderResultService tradeUnderResultService) {
		this.tradeUnderResultService = tradeUnderResultService;
	}

	public void setTradeDoorOpenService(TradeDoorOpenService tradeDoorOpenService) {
		this.tradeDoorOpenService = tradeDoorOpenService;
	}

	public void setTradeBoxOpenService(TradeBoxOpenService tradeBoxOpenService) {
		this.tradeBoxOpenService = tradeBoxOpenService;
	}

	public void setTradeSiteNetStateService(TradeSiteNetStateService tradeSiteNetStateService) {
		this.tradeSiteNetStateService = tradeSiteNetStateService;
	}

	public void setTradeBoxOpenStateService(TradeBoxOpenStateService tradeBoxOpenStateService) {
		this.tradeBoxOpenStateService = tradeBoxOpenStateService;
	}

	public void setTradeSupplyEventService(TradeSupplyEventService tradeSupplyEventService) {
		this.tradeSupplyEventService = tradeSupplyEventService;
	}

	public void setTradeProductInfoService(TradeProductInfoService tradeProductInfoService) {
		this.tradeProductInfoService = tradeProductInfoService;
	}

	public void setTradeProductClassifyService(TradeProductClassifyService tradeProductClassifyService) {
		this.tradeProductClassifyService = tradeProductClassifyService;
	}

	public void setTradeVendingPersonService(TradeVendingPersonService tradeVendingPersonService) {
		this.tradeVendingPersonService = tradeVendingPersonService;
	}

	public void setTradeVendingCmdService(TradeVendingCmdService tradeVendingCmdService) {
		this.tradeVendingCmdService = tradeVendingCmdService;
	}

	public void setTradeOrderOpenService(TradeOrderOpenService tradeOrderOpenService) {
		this.tradeOrderOpenService = tradeOrderOpenService;
	}

	public void setTradeOrderReportService(TradeOrderReportService tradeOrderReportService) {
		this.tradeOrderReportService = tradeOrderReportService;
	}

	public void setTradeOrderPayService(TradeOrderPayService tradeOrderPayService) {
		this.tradeOrderPayService = tradeOrderPayService;
	}

	public void setTradeUserInfoService(TradeUserInfoService tradeUserInfoService) {
		this.tradeUserInfoService = tradeUserInfoService;
	}

	public void setTradeOrderRefundService(TradeOrderRefundService tradeOrderRefundService) {
		this.tradeOrderRefundService = tradeOrderRefundService;
	}

	public void setTradePayTestService(TradePayTestService tradePayTestService) {
		this.tradePayTestService = tradePayTestService;
	}

	public void setTradeSiteLanelsdifferService(TradeSiteLanelsdifferService tradeSiteLanelsdifferService) {
		this.tradeSiteLanelsdifferService = tradeSiteLanelsdifferService;
	}

	public void setTradeSitelsdhandleService(TradeSitelsdhandleService tradeSitelsdhandleService) {
		this.tradeSitelsdhandleService = tradeSitelsdhandleService;
	}

	public void setTradeOrderApplyLaneService(TradeOrderApplyLaneService tradeOrderApplyLaneService) {
		this.tradeOrderApplyLaneService = tradeOrderApplyLaneService;
	}

}
