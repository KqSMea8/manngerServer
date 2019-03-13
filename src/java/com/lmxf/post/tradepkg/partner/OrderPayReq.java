package com.lmxf.post.tradepkg.partner;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.GParameter;
import com.lmxf.post.core.utils.JSONUtils;
import com.lmxf.post.entity.order.OrderApply;
import com.lmxf.post.tradepkg.common.ReqHead;

public class OrderPayReq extends ReqHead {

	@SuppressWarnings("unchecked")
	public Map parseXml(String report_json) {
		OrderApply orderApply = new OrderApply();
		Map r = new HashMap();
		try {
			// json解析协议
			JSONObject JSONBody = JSONUtils.getHeader(report_json);
			JSONBody = JSONUtils.getBody(report_json);
			orderApply.setOrderId(JSONBody.getString("OrderId"));
			orderApply.setPayType(JSONBody.getString("PayType"));
			if (orderApply.getOrderId() == null || "".equals(orderApply.getOrderId())) {
				r.put("code", GConstent.Request_OrderId_No_Define);
				return r;
			}
			if (orderApply.getPayType() == null || "".equals(orderApply.getPayType())) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
			//微信支付参数
			if (orderApply.getPayType().equals(GParameter.payType_weChatG)){
				JSONArray WechatJSONArray = JSONBody.getJSONArray("WechatPayInfo");
				if (WechatJSONArray == null || WechatJSONArray.size() <= 0) {
					r.put("code", GConstent.WechatPayInfo_No_Found);
					return r;
				}
				for (int i = 0; i < WechatJSONArray.size(); i++) {
					JSONObject jSONObject = (JSONObject) WechatJSONArray.get(i);
					orderApply.setOpenId(jSONObject.getString("OpenId"));
				}
			}
			//支付宝支付参数
			if (orderApply.getPayType().equals(GParameter.orderApplyPayType_Alipay)){
				JSONArray AlipayJSONArray = JSONBody.getJSONArray("AlipayInfo");
				if (AlipayJSONArray == null || AlipayJSONArray.size() <= 0) {
					r.put("code", GConstent.WechatPayInfo_No_Found);
					return r;
				}
				for (int i = 0; i < AlipayJSONArray.size(); i++) {
					JSONObject jSONObject = (JSONObject) AlipayJSONArray.get(i);
//				orderApply.setOpenId(jSONObject.getString("OpenId"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			r.put("code", GConstent.Program_App_Execp);
			return r;
		}
		r.put("code", 0);
		r.put("orderApply", orderApply);
		return r;
	}
}