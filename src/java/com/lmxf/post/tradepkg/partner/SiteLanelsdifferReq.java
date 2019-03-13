package com.lmxf.post.tradepkg.partner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.putils.utils.StringUtils;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.GParameter;
import com.lmxf.post.core.utils.JSONUtils;
import com.lmxf.post.entity.order.OrderApply;
import com.lmxf.post.entity.order.OrderProduct;
import com.lmxf.post.entity.vending.VendingLsdiffer;
import com.lmxf.post.tradepkg.common.ReqHead;

public class SiteLanelsdifferReq extends ReqHead {
	@SuppressWarnings("unchecked")
	public Map parseXml(String report_json) {

		List<VendingLsdiffer> vendingLsdifferList = new ArrayList();
		Map r = new HashMap();
		try {
			// json解析协议
			JSONObject JSONBody = JSONUtils.getHeader(report_json);
			String bcode = JSONBody.getString("BCode");
			JSONBody = JSONUtils.getBody(report_json);
			String siteId = JSONBody.getString("SiteId");
			String OperId = JSONBody.getString("OperId");
			if (siteId == null || "".equals(siteId)) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
			if (OperId == null || "".equals(OperId)) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
			r.put("siteId", siteId);
			r.put("operId", OperId);
			JSONArray JSONArray = JSONBody.getJSONArray("LaneInfo");
			if (JSONArray == null || JSONArray.size() <= 0) {
				r.put("code", GConstent.Product_Info_No_Found);
				return r;
			}
			VendingLsdiffer vendingLsdiffer = null;
			for (int i = 0; i < JSONArray.size(); i++) {
				JSONObject jSONObject = (JSONObject) JSONArray.get(i);
				vendingLsdiffer = new VendingLsdiffer();
				vendingLsdiffer.setLaneSId(Integer.parseInt(jSONObject.getString("LaneSId")));
				vendingLsdiffer.setLaneEId(Integer.parseInt(jSONObject.getString("LaneEId")));
				vendingLsdiffer.setCurCap(Integer.parseInt(jSONObject.getString("CurCap")));
				vendingLsdiffer.setResetCap(Integer.parseInt(jSONObject.getString("ResetCap")));
				vendingLsdiffer.setDifferNum(vendingLsdiffer.getCurCap()-vendingLsdiffer.getResetCap());
				if(vendingLsdiffer.getDifferNum()<0){
					vendingLsdiffer.setHandleType(GParameter.VendingLsdifferHandleType_inbound);
				}else if(vendingLsdiffer.getDifferNum()>0){
					vendingLsdiffer.setHandleType(GParameter.VendingLsdifferHandleType_outbound);
				}
				vendingLsdiffer.setDifferNum(Math.abs(vendingLsdiffer.getDifferNum()));
				if (vendingLsdiffer.getLaneSId() <= 0) {
					r.put("code", GConstent.Request_parameter_No_Define);
					return r;
				}
				if (vendingLsdiffer.getLaneEId() <= 0) {
					r.put("code", GConstent.Request_parameter_No_Define);
					return r;
				}
				vendingLsdifferList.add(vendingLsdiffer);
			}
			if (vendingLsdifferList.size() <= 0) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
		} catch (Exception e) {
			e.printStackTrace();
			r.put("code", GConstent.Program_App_Execp);
			return r;
		}
		r.put("code", 0);
		r.put("vendingLsdifferList", vendingLsdifferList);
		return r;
	}

}
