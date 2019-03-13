package com.lmxf.post.webservice.impl;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.JSONUtils;
import com.lmxf.post.core.utils.XmlValidator;
import com.lmxf.post.service.core.ErrorCodeService;
import com.lmxf.post.service.core.TradeDefineService;
import com.lmxf.post.service.partner.TradeOrderCancelService;

/**
 * 售货机命令推送失败处理
 * 
 * @author Administrator
 * 
 */
public class TransferFailureProcess {
	private final static Log log = LogFactory.getLog(DataSynchProcess.class);
	private ErrorCodeService errorCodeService;
	private TradeOrderCancelService tradeOrderCancelService;// 订单取消

	public String report(String apply_xml) {
		log.error("收到消息服务器命令执行失败协议:" + apply_xml.replaceAll(">\\s+<", "><"));
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
		String trade_code = JSONHeadObject.getString(GConstent.tcode);

		String repXml = null;
		try {
			// 推送订单命令失败
			if (trade_code.equals("1223")) {
				JSONObject JSONbodyObject = JSONUtils.getBody(apply_xml);
				String orderId = JSONbodyObject.getString("OrderId");
				// 封装订单取消json协议
				JSONObject zmsgObject = new JSONObject();

				JSONObject rootObject = new JSONObject();

				JSONObject headObject = new JSONObject();
				headObject.put(GConstent.tcode, "1004");
				headObject.put(GConstent.bcode, "04");
				headObject.put(GConstent.istart, "1");
				headObject.put(GConstent.version, "1.0");
				rootObject.put(GConstent.ZxmlHead, headObject);

				JSONObject bodyObject = new JSONObject();
				bodyObject.put("OrderId", orderId);
				rootObject.put(GConstent.ZxmlBody, bodyObject);

				zmsgObject.put(GConstent.ZxmlRoot, rootObject);
				log.info("订单推送失败后封装订单取消协议:" + zmsgObject.toString());

				repXml = tradeOrderCancelService.tradePrepare(zmsgObject.toString());
			}
		} catch (Exception e) {
			log.error("checkTrade is error :" + e.getClass() + "  " + e.getMessage());
			log.error("excception msg:" + errorCodeService.getErrorRespJson(GConstent.Program_App_Execp));
			return errorCodeService.getErrorRespJson(GConstent.Program_App_Execp);
		}
		log.error("trade_code:" + trade_code + "   repXml:" + repXml.replaceAll(">\\s+<", "><"));
		return repXml;
	}

	public void setErrorCodeService(ErrorCodeService errorCodeService) {
		this.errorCodeService = errorCodeService;
	}

	public void setTradeOrderCancelService(TradeOrderCancelService tradeOrderCancelService) {
		this.tradeOrderCancelService = tradeOrderCancelService;
	}

}
