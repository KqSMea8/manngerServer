package com.lmxf.post.tradepkg.init;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.JSONUtils;
import com.lmxf.post.entity.vending.Vending;
import com.lmxf.post.tradepkg.common.ReqHead;

public class VendingQueryReq extends ReqHead {
	
	@SuppressWarnings("unchecked")
	public Map parseXml(String report_json) {
		Map r = new HashMap();
		Vending vending = new Vending();
		try {
			// json解析协议
			JSONObject JSONBody = JSONUtils.getHeader(report_json);
			JSONBody = JSONUtils.getBody(report_json);
			vending.setSiteId(JSONBody.getString("SiteId"));
			if (vending.getSiteId() == null || "".equals(vending.getSiteId())) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
		} catch (Exception e) {
			e.printStackTrace();
			r.put("code", GConstent.Program_App_Execp);
			return r;
		}
		r.put("code", 0);
		r.put("vending", vending);
		return r;
	}

}
