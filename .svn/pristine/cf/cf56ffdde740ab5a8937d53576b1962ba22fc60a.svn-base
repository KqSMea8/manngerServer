package com.lmxf.post.tradepkg.partner;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.JSONUtils;
import com.lmxf.post.entity.vending.VendingLanep;
import com.lmxf.post.tradepkg.common.ReqHead;

public class LaneProductInfoReq extends ReqHead {
	
	@SuppressWarnings("unchecked")
	public Map parseXml(String report_json) {
		Map r = new HashMap();
		VendingLanep vendingLanep = new VendingLanep();
		try {
			// json解析协议
			JSONObject JSONBody = JSONUtils.getHeader(report_json);
			r.put("bcode", JSONBody.getString("BCode"));
			r.put("istart", JSONBody.getString("IStart"));
			JSONBody = JSONUtils.getBody(report_json);
			vendingLanep.setSiteId(JSONBody.getString("SiteId"));
			if (vendingLanep.getSiteId() == null || "".equals(vendingLanep.getSiteId())) {
				r.put("code", GConstent.Request_StationNumber_No_Define);
				return r;
			}
		} catch (Exception e) {
			e.printStackTrace();
			r.put("code", GConstent.Program_App_Execp);
			return r;
		}
		r.put("code", 0);
		r.put("vendingLanep", vendingLanep);
		return r;
	}

}
