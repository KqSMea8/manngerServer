package com.lmxf.post.tradepkg.init;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.JSONUtils;
import com.lmxf.post.tradepkg.common.ReqHead;

public class OrderAllIssuedReq extends ReqHead {

	@SuppressWarnings("unchecked")
	public Map parseXml(String report_json) {
		String siteId = null;
		Map r = new HashMap();
		try {
			// json解析协议
			JSONObject JSONBody = JSONUtils.getHeader(report_json);
			r.put("tcode", JSONBody.getString("TCode"));
			r.put("bcode", JSONBody.getString("BCode"));
			r.put("istart", JSONBody.getString("IStart"));

			JSONBody = JSONUtils.getBody(report_json);
			r.put("siteId", JSONBody.getString("SiteId"));
			
			if (r.get("siteId") == null || "".equals(r.get("siteId"))) {
				r.put("code", GConstent.Request_StationNumber_No_Define);
				return r;
			}
		} catch (Exception e) {
			e.printStackTrace();
			r.put("code", GConstent.Program_App_Execp);
			return r;
		}
		r.put("code", 0);
		return r;
	}
}
