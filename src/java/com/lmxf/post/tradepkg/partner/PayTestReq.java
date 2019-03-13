package com.lmxf.post.tradepkg.partner;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.JSONUtils;
import com.lmxf.post.entity.order.OrderApply;
import com.lmxf.post.tradepkg.common.ReqHead;

public class PayTestReq extends ReqHead {

	@SuppressWarnings("unchecked")
	public Map parseXml(String report_json) {
		Map r = new HashMap();
		OrderApply orderApply = new OrderApply();
		try {
			// json解析协议
			JSONObject JSONBody = JSONUtils.getHeader(report_json);
			JSONBody = JSONUtils.getBody(report_json);
			if (JSONBody.getString("Price")== null || "".equals(JSONBody.getString("Price"))) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
			orderApply.setPayPrice(Float.parseFloat(JSONBody.getString("Price")));
			orderApply.setPayType(JSONBody.getString("PayType"));
			orderApply.setCorpId(JSONBody.getString("CorpId"));
			orderApply.setOrderId("TestPay" + "_" + orderApply.getCorpId() + "_" + System.nanoTime());
			orderApply.setNotice(JSONBody.getString("Notice"));
			if (orderApply.getCorpId()== null || "".equals(orderApply.getCorpId())) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
			if (orderApply.getPayType()== null || "".equals(orderApply.getPayType())) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
			if (orderApply.getNotice()== null || "".equals(orderApply.getNotice())) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
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
