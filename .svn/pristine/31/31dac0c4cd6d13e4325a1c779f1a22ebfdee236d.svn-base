package com.lmxf.post.service.partner;

import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.GParameter;
import com.lmxf.post.core.utils.JSONUtils;
import com.lmxf.post.core.utils.pay.PayUtils;
import com.lmxf.post.entity.order.OrderApply;
import com.lmxf.post.service.core.TradeProcess;
import com.lmxf.post.tradepkg.partner.PayTestReq;
import com.lmxf.post.tradepkg.partner.PayTestResp;

public class TradePayTestService extends TradeProcess {
	private final static Log log = LogFactory.getLog(TradeOrderCancelService.class);
	private PayTestReq payTestReq;
	private PayTestResp payTestResp;

	@SuppressWarnings("rawtypes")
	public String tradeProcess(String apply_xml) {
		Map ret = null;
		OrderApply orderApply = null;
		try {
			ret = payTestReq.parseXml(apply_xml);
			if (ret.get("code") == null) {
				return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
			} else if (ret.get("code") != null && Integer.parseInt(ret.get("code").toString()) != 0) {
				log.error("payTestReq.parseXml is error!");
				return errorCodeDao.getErrorRespJson(Integer.parseInt(ret.get("code").toString()));
			}
		} catch (Exception e) {
			log.error("---parseXml  error-----", e);
			return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
		}
		orderApply = (OrderApply) ret.get("orderApply");
		// 申请支付
		String xml = "";
		// 支持微信和支付宝扫码支付测试
		if (!GParameter.payType_weChat.equals(orderApply.getPayType()) && !GParameter.payType_Alipay.equals(orderApply.getPayType())) {
			return errorCodeDao.getErrorRespJson(GConstent.OrderApply_PayInfo_Error);
		}
		try {
			orderApply.setPayMethod(orderApply.getPayType());
			log.debug("开始申请支付");
			xml = PayUtils.tradeHand(orderApply);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(" 申请支付失败:" + e.getMessage());
			GParameter.payOrderNoticeMap.remove(orderApply.getOrderId());
			return errorCodeDao.getErrorRespJson(GConstent.OrderApply_PayInfo_Error);
		}
		// 微信扫码支付或支付宝扫码
		JSONObject JSONBody = JSONUtils.getHeader(xml);
		String retcode = JSONBody.getString("RetCode");
		String retmsg = JSONBody.getString("RetMsg");
		JSONBody = JSONUtils.getBody(xml);
		String out_trade_no = JSONBody.getString("out_trade_no");
		String img_url = JSONBody.getString("img_url");
		String code_url = JSONBody.getString("qr_code");
		if ("0000".equals(retcode)) {
			orderApply.setTitle("0000");
			orderApply.setpTradeNo(out_trade_no);
			orderApply.setImgUrl(img_url);
			orderApply.setCodeUrl(code_url);
			GParameter.payOrderNoticeMap.put(out_trade_no, orderApply);
		} else {
			orderApply.setTitle(retmsg);
		}
		return payTestResp.CreateJson(GConstent.SUCCESS_CODE, GConstent.SUCCESS_MSG, 1, 1, orderApply);
	}

	public void setPayTestReq(PayTestReq payTestReq) {
		this.payTestReq = payTestReq;
	}

	public void setPayTestResp(PayTestResp payTestResp) {
		this.payTestResp = payTestResp;
	}

}
