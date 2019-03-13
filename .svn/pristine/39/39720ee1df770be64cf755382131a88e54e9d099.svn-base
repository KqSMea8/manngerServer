package com.lmxf.post.tradepkg.partner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.JSONUtils;
import com.lmxf.post.entity.order.OrderApply;
import com.lmxf.post.entity.order.OrderProduct;
import com.lmxf.post.tradepkg.common.ReqHead;

public class OrderReportReq extends ReqHead {
	@SuppressWarnings("unchecked")
	public Map parseXml(String report_json) {
		OrderApply orderApply = new OrderApply();
		Map r = new HashMap();
		try {
			// json解析协议
			JSONObject JSONBody = JSONUtils.getHeader(report_json);
			JSONBody = JSONUtils.getBody(report_json);
			orderApply.setSiteId(JSONBody.getString("SiteId"));
			orderApply.setSaleChannel("1");
			orderApply.setLoginId(JSONBody.getString("Cid"));
			if (orderApply.getSiteId() == null || "".equals(orderApply.getSiteId())) {
				r.put("code", GConstent.Request_SiteId_No_Define);
				return r;
			}
			if (orderApply.getSaleChannel() == null || "".equals(orderApply.getSaleChannel())) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
			if (orderApply.getLoginId() == null || "".equals(orderApply.getLoginId())) {
				r.put("code", GConstent.Request_LoginId_No_Define);
				return r;
			}
			JSONArray JSONArray = JSONBody.getJSONArray("OrderInfo");
			if (JSONArray == null || JSONArray.size() <= 0) {
				r.put("code", GConstent.Product_Info_No_Found);
				return r;
			}
			List<OrderProduct> orderProductList = new ArrayList();
			OrderProduct orderProduct = null;
			for (int i = 0; i < JSONArray.size(); i++) {
				JSONObject jSONObject = (JSONObject) JSONArray.get(i);
				orderProduct = new OrderProduct();
				orderProduct.setProductCode(jSONObject.getString("BarCode"));
				if (orderProduct.getProductCode() == null || "".equals(orderProduct.getProductCode())) {
					r.put("code", GConstent.Request_parameter_No_Define);
					return r;
				}
				orderProductList.add(orderProduct);
			}
			if (orderProductList.size() <= 0) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
			orderApply.setOrderProductList(orderProductList);
		} catch (Exception e) {
			e.printStackTrace();
			r.put("code", GConstent.Program_App_Execp);
			return r;
		}
		r.put("code", 0);
		r.put("orderApply", orderApply);
		return r;
	}
}
