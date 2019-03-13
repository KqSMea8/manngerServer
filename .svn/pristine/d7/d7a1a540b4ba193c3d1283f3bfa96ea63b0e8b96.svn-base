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
import com.lmxf.post.entity.product.ProductLunder;
import com.lmxf.post.tradepkg.common.ReqHead;

public class UnderResultReq extends ReqHead {

	@SuppressWarnings("unchecked")
	public Map parseXml(String report_json) {
		Map r = new HashMap();
		ProductLunder productLunder = null;
		String vUnderId=null;
		String isFinsh = GParameter.supplyOrderState_wait;// 未补货完成
		List<ProductLunder> productLunderList = new ArrayList<ProductLunder>();
		try {
			// json解析协议
			JSONObject JSONBody = JSONUtils.getHeader(report_json);
			r.put("bcode", JSONBody.getString("BCode"));
			r.put("istart", JSONBody.getString("IStart"));
			JSONBody = JSONUtils.getBody(report_json);
			vUnderId = JSONBody.getString("VUnderId");
			if (JSONBody.containsKey("IsFinsh")) {
				isFinsh = JSONBody.getString("IsFinsh");
			}
			JSONArray jsonNArray = JSONBody.getJSONArray("UnderInfo");
			for (int i = 0; i < jsonNArray.size(); i++) {
				JSONObject JSONObject = (net.sf.json.JSONObject) jsonNArray.get(i);
				productLunder = new ProductLunder();
				productLunder.setLunderId(JSONObject.getString("UnderId"));
				productLunder.setProductId(JSONObject.getString("ProductId"));
				productLunder.setLaneSId(Integer.parseInt(JSONObject.getString("LaneSId")));
				productLunder.setLaneEId(Integer.parseInt(JSONObject.getString("LaneEId")));
				productLunder.setUnderNum(Integer.parseInt(JSONObject.getString("UnderNum")));
				if (productLunder.getLunderId() == null || "".equals(productLunder.getLunderId())) {
					r.put("code", GConstent.Request_parameter_No_Define);
					return r;
				}
				if (productLunder.getProductId() == null || "".equals(productLunder.getProductId())) {
					r.put("code", GConstent.Request_parameter_No_Define);
					return r;
				}
				if (productLunder.getLaneSId() == 0 || "".equals(productLunder.getLaneSId())) {
					r.put("code", GConstent.Request_parameter_No_Define);
					return r;
				}
				if (productLunder.getLaneEId() == 0 || "".equals(productLunder.getLaneEId())) {
					r.put("code", GConstent.Request_parameter_No_Define);
					return r;
				}
				if ("".equals(productLunder.getUnderNum())) {
					r.put("code", GConstent.Request_parameter_No_Define);
					return r;
				}
				productLunderList.add(productLunder);
			}

		} catch (Exception e) {
			e.printStackTrace();
			r.put("code", GConstent.Program_App_Execp);
			return r;
		}
		r.put("code", 0);
		r.put("productLunderList", productLunderList);
		r.put("vUnderId", vUnderId);
		r.put("isFinsh", isFinsh);
		return r;
	}

}
