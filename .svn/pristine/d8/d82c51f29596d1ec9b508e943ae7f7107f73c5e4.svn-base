package com.lmxf.post.tradepkg.partner;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.GParameter;
import com.lmxf.post.core.utils.JSONUtils;
import com.lmxf.post.entity.order.OrderApply;
import com.lmxf.post.tradepkg.common.ReqHead;

public class CustomerOrderInfoReq extends ReqHead {

	@SuppressWarnings("unchecked")
	public Map parseXml(String report_json) {
		OrderApply orderApply = new OrderApply();
		Map r = new HashMap();
		String chanelType = null;
		try {
			// json解析协议
			JSONObject JSONBody = JSONUtils.getHeader(report_json);
			r.put("istart", JSONBody.getString("IStart"));

			JSONBody = JSONUtils.getBody(report_json);
			orderApply.setLoginId("" + JSONBody.getString("LoginId"));
			orderApply.setLoginName("" + JSONBody.getString("LoginName"));
			if (JSONBody.containsKey("OrderId")){
				orderApply.setOrderId("" + JSONBody.getString("OrderId"));
			}
			if (JSONBody.containsKey("CurState")){
				orderApply.setCurState("" + JSONBody.getString("CurState"));
			}
			if (JSONBody.containsKey("PayState")){
				if(JSONBody.getString("PayState").equals("00")){
					orderApply.setPayState(GParameter.payState_wait);
				}else if(JSONBody.getString("PayState").equals("01")){
					orderApply.setPayState(GParameter.payState_success);
				}else if(JSONBody.getString("PayState").equals("02")){
					orderApply.setPayState(GParameter.payState_failed);
				}
			}
			if (JSONBody.containsKey("AbnomarlState")){
				orderApply.setAbnomarlState("" + JSONBody.getString("AbnomarlState"));
			}
			if (JSONBody.containsKey("BeginTime")){
				orderApply.setBeginTime("" + JSONBody.getString("BeginTime"));
			}
			if (JSONBody.containsKey("EndTime")){
				orderApply.setEndTime("" + JSONBody.getString("EndTime"));
			}
			if (orderApply.getLoginId() == null || "".equals(orderApply.getLoginId())) {
				r.put("code", GConstent.Request_LoginId_No_Define);
				return r;
			}
			if (orderApply.getLoginName() == null || "".equals(orderApply.getLoginName())) {
				r.put("code", GConstent.Request_LoginName_No_Define);
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
