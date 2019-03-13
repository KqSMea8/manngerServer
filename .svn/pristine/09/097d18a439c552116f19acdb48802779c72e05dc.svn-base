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
import com.lmxf.post.entity.vending.VendingLsdhandle;
import com.lmxf.post.entity.vending.VendingLsdiffer;
import com.lmxf.post.tradepkg.common.ReqHead;

public class SitelsdhandleReq extends ReqHead {
	@SuppressWarnings("unchecked")
	public Map parseXml(String report_json) {

		List<VendingLsdhandle> vendingLsdifferList = new ArrayList();
		Map r = new HashMap();
		try {
			// json解析协议
			JSONObject JSONBody = JSONUtils.getHeader(report_json);
			String bcode = JSONBody.getString("BCode");
			JSONBody = JSONUtils.getBody(report_json);
			String OrderId = JSONBody.getString("OrderId");
			String OperId = JSONBody.getString("OperId");
			if (OrderId == null || "".equals(OrderId)) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
			if (OperId == null || "".equals(OperId)) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
			r.put("orderId", OrderId);
			r.put("operId", OperId);
			JSONArray JSONArray = JSONBody.getJSONArray("LaneInfo");
			if (JSONArray == null || JSONArray.size() <= 0) {
				r.put("code", GConstent.Product_Info_No_Found);
				return r;
			}
			VendingLsdhandle vendingLsdiffer = null;
			for (int i = 0; i < JSONArray.size(); i++) {
				JSONObject jSONObject = (JSONObject) JSONArray.get(i);
				vendingLsdiffer = new VendingLsdhandle();
				vendingLsdiffer.setProboxId(jSONObject.getString("ProBoxId"));
				vendingLsdiffer.setLsdifferId(jSONObject.getString("LsdifferId"));
				vendingLsdiffer.setProductId(jSONObject.getString("ProductId"));
				vendingLsdiffer.setHandleType(jSONObject.getString("OutState"));
				if (vendingLsdiffer.getProboxId() == null || "".equals(vendingLsdiffer.getProboxId())) {
					r.put("code", GConstent.Request_parameter_No_Define);
					return r;
				}
				if (vendingLsdiffer.getLsdifferId() == null || "".equals(vendingLsdiffer.getLsdifferId())) {
					r.put("code", GConstent.Request_parameter_No_Define);
					return r;
				}
				if (vendingLsdiffer.getHandleType() == null || "".equals(vendingLsdiffer.getHandleType())) {
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
