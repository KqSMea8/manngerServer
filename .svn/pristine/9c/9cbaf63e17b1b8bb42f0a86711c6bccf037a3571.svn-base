package com.lmxf.post.tradepkg.partner;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.GParameter;
import com.lmxf.post.core.utils.JSONUtils;
import com.lmxf.post.entity.vending.Vending;
import com.lmxf.post.tradepkg.common.ReqHead;

public class SiteNetStateReq extends ReqHead {
	
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
			String netState = JSONBody.getString("NetWork");
			if (netState == null || "".equals(netState)) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
			if(netState.equals("01")){
				vending.setNetSate(GParameter.siteNetState_online);
			}else if(netState.equals("02")){
				vending.setNetSate(GParameter.siteNetState_offline);
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
