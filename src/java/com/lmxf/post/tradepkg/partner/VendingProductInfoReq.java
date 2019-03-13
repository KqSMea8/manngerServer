package com.lmxf.post.tradepkg.partner;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.JSONUtils;
import com.lmxf.post.entity.vending.VendingStock;
import com.lmxf.post.tradepkg.common.ReqHead;

public class VendingProductInfoReq extends ReqHead {
	
	@SuppressWarnings("unchecked")
	public Map parseXml(String report_json) {
		Map r = new HashMap();
		VendingStock vendingStock = new VendingStock();
		try {
			// json解析协议
			JSONObject JSONBody = JSONUtils.getHeader(report_json);
			r.put("bcode", JSONBody.getString("BCode"));
			r.put("istart", JSONBody.getString("IStart"));
			JSONBody = JSONUtils.getBody(report_json);
			vendingStock.setSiteId(JSONBody.getString("SiteId"));
			if (vendingStock.getSiteId() == null || "".equals(vendingStock.getSiteId())) {
				r.put("code", GConstent.Request_StationNumber_No_Define);
				return r;
			}
			if (JSONBody.containsKey("ClassifyId")) {
				vendingStock.setTypeId("" + JSONBody.getString("ClassifyId"));
			}
			if (JSONBody.containsKey("ProductName")) {
				vendingStock.setProductName("" + JSONBody.getString("ProductName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			r.put("code", GConstent.Program_App_Execp);
			return r;
		}
		r.put("code", 0);
		r.put("vendingStock", vendingStock);
		return r;
	}

}
