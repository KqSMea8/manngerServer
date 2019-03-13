package com.lmxf.post.tradepkg.partner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.putils.utils.StringUtils;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.JSONUtils;
import com.lmxf.post.entity.supply.SupplyOrder;
import com.lmxf.post.entity.supply.SupplyVproduct;
import com.lmxf.post.tradepkg.common.ReqHead;

public class SupplyEventReq extends ReqHead {
	@SuppressWarnings("unchecked")
	public Map parseXml(String report_json) {
		SupplyOrder supplyOrder = new SupplyOrder();
		String siteId;
		Map r = new HashMap();
		try {
			// json解析协议
			JSONObject JSONBody = JSONUtils.getHeader(report_json);
			JSONBody = JSONUtils.getBody(report_json);
			siteId = JSONBody.getString("SiteId");
			supplyOrder.setSupplierId(JSONBody.getString("replenerId"));
			supplyOrder.setStateTime(JSONBody.getString("OperTime"));
			if (siteId == null || "".equals(siteId)) {
				r.put("code", GConstent.Request_SiteId_No_Define);
				return r;
			}
			if (supplyOrder.getSupplierId() == null || "".equals(supplyOrder.getSupplierId())) {
				r.put("code", GConstent.Request_LoginId_No_Define);
				return r;
			}
			if (supplyOrder.getStateTime() == null || "".equals(supplyOrder.getStateTime())) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
			JSONArray JSONArray = JSONBody.getJSONArray("ReplenInfo");
			if (JSONArray == null || JSONArray.size() <= 0) {
				r.put("code", GConstent.Replen_Info_No_Found);
				return r;
			}
			List<SupplyVproduct> supplyVproductList = new ArrayList();
			int curPNum = 0;
			int supplyNum = 0;
			SupplyVproduct supplyVproduct = null;
			for (int i = 0; i < JSONArray.size(); i++) {
				JSONObject jSONObject = (JSONObject) JSONArray.get(i);
				supplyVproduct = new SupplyVproduct();
				supplyVproduct.setSiteId(siteId);
				supplyVproduct.setLaneSId(Integer.parseInt(jSONObject.getString("LaneId")));
				supplyVproduct.setProductId(jSONObject.getString("ProductId"));
				supplyVproduct.setrSupplyNum(Integer.parseInt(jSONObject.getString("ReplenNum")));
				supplyVproduct.setBuyPrice(Float.parseFloat(jSONObject.getString("BuyPrice")));
				curPNum = curPNum + Integer.parseInt(jSONObject.getString("SurplusNum"));
				supplyNum = supplyNum +  supplyVproduct.getrSupplyNum();
				if (supplyVproduct.getLaneSId() == 0 || "".equals(supplyVproduct.getLaneSId())) {
					r.put("code", GConstent.Request_parameter_No_Define);
					return r;
				}
				if (StringUtils.isEmpty(supplyVproduct.getProductId())) {            
 					r.put("code", GConstent.Request_parameter_No_Define);
					return r;
				}
				if (Integer.parseInt(jSONObject.getString("SurplusNum")) == 0 || "".equals(Integer.parseInt(jSONObject.getString("SurplusNum")))) {
					r.put("code", GConstent.Request_parameter_No_Define);
					return r;
				}
				if (supplyVproduct.getrSupplyNum()== 0 || "".equals(supplyVproduct.getrSupplyNum())) {
					r.put("code", GConstent.Request_parameter_No_Define);
					return r;
				}
				if (supplyVproduct.getBuyPrice()== 0 || "".equals(supplyVproduct.getBuyPrice())) {
					r.put("code", GConstent.Request_parameter_No_Define);
					return r;
				}
				supplyVproductList.add(supplyVproduct);
			}
			if (supplyVproductList.size() <= 0) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
			supplyOrder.setCurPNum(curPNum);
			supplyOrder.setSupplyNum(supplyNum);
			supplyOrder.setSupplyVproductList(supplyVproductList);
		} catch (Exception e) {
			e.printStackTrace();
			r.put("code", GConstent.Program_App_Execp);
			return r;
		}
		r.put("code", 0);
		r.put("supplyOrder", supplyOrder);
		r.put("siteId", siteId);
		return r;
	}

}
