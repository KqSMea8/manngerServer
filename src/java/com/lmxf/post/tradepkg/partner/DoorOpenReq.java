package com.lmxf.post.tradepkg.partner;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.JSONUtils;
import com.lmxf.post.tradepkg.common.ReqHead;

public class DoorOpenReq extends ReqHead {

	@SuppressWarnings("unchecked")
	public Map parseXml(String report_json) {
		String siteId;
		String loginId;
		String state;
		Map r = new HashMap();
		try {
			// json解析协议
			JSONObject JSONBody = JSONUtils.getHeader(report_json);
			JSONBody = JSONUtils.getBody(report_json);
			siteId = JSONBody.getString("SiteId");
			loginId = JSONBody.getString("LoginId");
			state = JSONBody.getString("State");
			if (siteId == null || "".equals(siteId)) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
			if (loginId == null || "".equals(loginId)) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
			if (state == null || "".equals(state)) {
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
		r.put("loginId", loginId);
		r.put("state", state);
		return r;
	}
}
