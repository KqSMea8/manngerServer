package com.lmxf.post.tradepkg.partner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.GParameter;
import com.lmxf.post.core.utils.JSONUtils;
import com.lmxf.post.entity.supply.SupplyVproduct;
import com.lmxf.post.tradepkg.common.ReqHead;

public class SupplyResultReq extends ReqHead {

	@SuppressWarnings("unchecked")
	public Map parseXml(String report_json) {
		Map r = new HashMap();
		List<SupplyVproduct> supplyVproductList = new ArrayList<SupplyVproduct>();
		SupplyVproduct supplyVproduct = null;
		String vOrderId = null;
		String sOrderId = null;
		String isFinsh = GParameter.supplyOrderState_wait;// 未补货完成
		try {
			// json解析协议
			JSONObject JSONBody = JSONUtils.getHeader(report_json);
			r.put("bcode", JSONBody.getString("BCode"));
			r.put("istart", JSONBody.getString("IStart"));
			JSONBody = JSONUtils.getBody(report_json);
			vOrderId = JSONBody.getString("VOrderId");
			sOrderId = JSONBody.getString("SOrderId");
			if (JSONBody.containsKey("IsFinsh")) {
				isFinsh = JSONBody.getString("IsFinsh");
			}
			JSONArray JSONArraySupply = JSONBody.getJSONArray("SupplyInfo");
			for (int i = 0; i < JSONArraySupply.size(); i++) {
				JSONObject jsonbod = (JSONObject) JSONArraySupply.get(i);
				supplyVproduct = new SupplyVproduct();
				supplyVproduct.setsVendingId(jsonbod.getString("SLProductId"));
				supplyVproduct.setProductId(jsonbod.getString("ProductId"));
				supplyVproduct.setLaneSId(Integer.parseInt(jsonbod.getString("LaneSId")));
				supplyVproduct.setLaneEId(Integer.parseInt(jsonbod.getString("LaneEId")));
				supplyVproduct.setCurNum(Integer.parseInt(jsonbod.getString("CurNum")));
				supplyVproduct.setrSupplyNum(Integer.parseInt(jsonbod.getString("RSupplyNum")));
				if (supplyVproduct.getsVendingId() == null || "".equals(supplyVproduct.getsVendingId())) {
					r.put("code", GConstent.Request_parameter_No_Define);
					return r;
				}
				if (supplyVproduct.getProductId() == null || "".equals(supplyVproduct.getProductId())) {
					r.put("code", GConstent.Request_parameter_No_Define);
					return r;
				}
				if ("".equals(supplyVproduct.getLaneSId())) {
					r.put("code", GConstent.Request_parameter_No_Define);
					return r;
				}
				if ("".equals(supplyVproduct.getLaneEId())) {
					r.put("code", GConstent.Request_parameter_No_Define);
					return r;
				}
				if ("".equals(supplyVproduct.getCurNum())) {
					r.put("code", GConstent.Request_parameter_No_Define);
					return r;
				}
				if ("".equals(supplyVproduct.getrSupplyNum())) {
					r.put("code", GConstent.Request_parameter_No_Define);
					return r;
				}
				supplyVproductList.add(supplyVproduct);
			}
		} catch (Exception e) {
			e.printStackTrace();
			r.put("code", GConstent.Program_App_Execp);
			return r;
		}
		r.put("code", 0);
		r.put("supplyVproductList", supplyVproductList);
		r.put("vOrderId", vOrderId);
		r.put("sOrderId", sOrderId);
		r.put("isFinsh", isFinsh);
		
		return r;
	}

}
