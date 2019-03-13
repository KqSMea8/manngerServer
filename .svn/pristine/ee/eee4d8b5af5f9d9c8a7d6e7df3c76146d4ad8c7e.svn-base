package com.lmxf.post.tradepkg.partner;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.JSONUtils;
import com.lmxf.post.tradepkg.common.ReqHead;

public class BoxOpenReq extends ReqHead {

	@SuppressWarnings("unchecked")
	public Map parseXml(String report_json) {
		String siteId;
		String laneSId;
		String orderId;
		Map r = new HashMap();
		try {
			// json解析协议
			JSONObject JSONBody = JSONUtils.getHeader(report_json);
			JSONBody = JSONUtils.getBody(report_json);
			siteId = JSONBody.getString("SiteId");
			laneSId = JSONBody.getString("LaneSId");
			orderId = JSONBody.getString("OrderId");
			if (siteId == null || "".equals(siteId)) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
			if (laneSId == null || "".equals(laneSId)) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
			if (orderId == null || "".equals(orderId)) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
		} catch (Exception e) {
			e.printStackTrace();
			r.put("code", GConstent.Program_App_Execp);
			return r;
		}
		r.put("code", 0);
		r.put("siteId", siteId);
		r.put("laneSId", laneSId);
		r.put("orderId", orderId);
		return r;
	}
}
