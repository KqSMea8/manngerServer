package com.lmxf.post.tradepkg.init;

import net.sf.json.JSONObject;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.entity.vending.Vending;
import com.lmxf.post.entity.vending.VendingState;
import com.lmxf.post.tradepkg.common.RespHead;

public class VendingQueryResp extends RespHead {


	public String CreateJson(Object... parm) {
		Vending vending = (Vending) parm[4];
		VendingState vendingState= (VendingState) parm[5];
		try {
			JSONObject rootObject = super.getJSONObject((String) parm[0], (String) parm[1], (Integer) parm[2], (Integer) parm[3]);
			JSONObject JSONBody = new JSONObject();
			JSONBody.put("SiteId", "" + vending.getSiteId());
			JSONBody.put("SiteName", ""+ vending.getSiteName());
			JSONBody.put("Fullname", "");
			JSONBody.put("Addr", ""+ vending.getAddress());
			JSONBody.put("FayWay", "" + vending.getFayWay());
			JSONBody.put("Discount", "" + vending.getDiscount());
			JSONBody.put("CabinetConfig", "" + vending.getConfigFile());
			JSONBody.put("SeqId", "" + (vendingState!=null?vendingState.getSeqId():""));
			JSONBody.put("Longitude", "" +vending.getLongitude());
			JSONBody.put("Latitude", "" + vending.getLatitude());
			JSONObject JSONObject = (JSONObject) rootObject.get(GConstent.ZxmlRoot);
			JSONObject.put(GConstent.ZxmlBody, JSONBody);
			return rootObject.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

}
