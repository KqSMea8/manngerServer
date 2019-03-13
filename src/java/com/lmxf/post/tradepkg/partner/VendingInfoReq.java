package com.lmxf.post.tradepkg.partner;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.JSONUtils;
import com.lmxf.post.entity.vending.Vending;
import com.lmxf.post.tradepkg.common.ReqHead;

public class VendingInfoReq extends ReqHead {
	private final static Log log = LogFactory.getLog(VendingInfoReq.class);

	@SuppressWarnings("unchecked")
	public Map parseXml(String report_json) {
		Map r = new HashMap();
		Vending vending = new Vending();
		try {
			// json解析协议
			JSONObject JSONBody = JSONUtils.getBody(report_json);
			if (JSONBody.containsKey("Latitude")){
				vending.setLatitude("" + JSONBody.getString("Latitude"));
			}
			if (JSONBody.containsKey("Longitude")){
				vending.setLongitude("" + JSONBody.getString("Longitude"));
			}
			if (JSONBody.containsKey("SiteId")){
				vending.setSiteId("" + JSONBody.getString("SiteId"));
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
