package com.lmxf.post.tradepkg.partner;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.JSONUtils;
import com.lmxf.post.entity.supply.SupplyOrder;
import com.lmxf.post.tradepkg.common.ReqHead;

public class SupplyInfoReq extends ReqHead {
	
	@SuppressWarnings("unchecked")
	public Map parseXml(String report_json) {
		Map r = new HashMap();
		SupplyOrder supplyOrder = new SupplyOrder();
		try {
			// json解析协议
			JSONObject JSONBody = JSONUtils.getHeader(report_json);
			r.put("bcode", JSONBody.getString("BCode"));
			r.put("istart", JSONBody.getString("IStart"));
			JSONBody = JSONUtils.getBody(report_json);
			supplyOrder.setSupplierId(JSONBody.getString("LoginId"));
			supplyOrder.setCurState(JSONBody.getString("State"));
			if (supplyOrder.getSupplierId() == null || "".equals(supplyOrder.getSupplierId())) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
//			if (supplyOrder.getCurState() == null || "".equals(supplyOrder.getCurState())) {
//				r.put("code", GConstent.Request_parameter_No_Define);
//				return r;
//			}
		} catch (Exception e) {
			e.printStackTrace();
			r.put("code", GConstent.Program_App_Execp);
			return r;
		}
		r.put("code", 0);
		r.put("supplyOrder", supplyOrder);
		return r;
	}

}
