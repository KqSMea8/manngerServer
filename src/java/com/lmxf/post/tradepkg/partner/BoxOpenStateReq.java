package com.lmxf.post.tradepkg.partner;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.JSONUtils;
import com.lmxf.post.entity.order.OrderApply;
import com.lmxf.post.tradepkg.common.ReqHead;

public class BoxOpenStateReq extends ReqHead {

	@SuppressWarnings("unchecked")
	public Map parseXml(String report_json) {
		OrderApply orderApply = new OrderApply();
		Map r = new HashMap();
		try {
			// json解析协议
			JSONObject JSONBody = JSONUtils.getHeader(report_json);
			JSONBody = JSONUtils.getBody(report_json);
			orderApply.setSiteId(JSONBody.getString("SiteId"));
			orderApply.setLoginId(JSONBody.getString("LoginId"));
			if (orderApply.getSiteId() == null || "".equals(orderApply.getSiteId())) {
				r.put("code", GConstent.Request_SiteId_No_Define);
				return r;
			}
			if (orderApply.getLoginId() == null || "".equals(orderApply.getLoginId())) {
				r.put("code", GConstent.Request_LoginId_No_Define);
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
