package com.lmxf.post.tradepkg.partner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.putils.utils.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.JSONUtils;
import com.lmxf.post.entity.order.OrderApply;
import com.lmxf.post.entity.order.OrderBox;
import com.lmxf.post.tradepkg.common.ReqHead;

public class OrderOpenReq extends ReqHead {

	@SuppressWarnings("unchecked")
	public Map parseXml(String report_json) {
		OrderApply orderApply = new OrderApply();
		Map r = new HashMap();
		try {
			// json解析协议
			JSONObject JSONBody = JSONUtils.getHeader(report_json);

			JSONBody = JSONUtils.getBody(report_json);
			orderApply.setOrderId(JSONBody.getString("OrderId"));
			if (orderApply.getOrderId() == null || "".equals(orderApply.getOrderId())) {
				r.put("code", GConstent.Request_OrderId_No_Define);
				return r;
			}
			JSONArray JSONArray = JSONBody.getJSONArray("BoxCmd");
			if (JSONArray == null || JSONArray.size() <= 0) {
				r.put("code", GConstent.Product_Info_No_Found);
				return r;
			}
			List<OrderBox> orderBoxList = new ArrayList();
			OrderBox orderBox = null;
			for (int i = 0; i < JSONArray.size(); i++) {
				JSONObject jSONObject = (JSONObject) JSONArray.get(i);
				orderBox = new OrderBox();
				orderBox.setLaneSId(Integer.parseInt(jSONObject.getString("BoxId")));
				orderBox.setLaneEId(Integer.parseInt(jSONObject.getString("BoxId")));
				orderBox.setOutState(jSONObject.getString("State"));
				if (orderBox.getLaneSId() == 0 || "".equals(orderBox.getLaneSId())) {
					r.put("code", GConstent.Request_parameter_No_Define);
					return r;
				}
				if (orderBox.getLaneEId() == 0 || "".equals(orderBox.getLaneEId())) {
					r.put("code", GConstent.Request_parameter_No_Define);
					return r;
				}
				if (StringUtils.isEmpty(orderBox.getOutState())) {            
 					r.put("code", GConstent.Request_parameter_No_Define);
					return r;
				}
				orderBoxList.add(orderBox);
			}
			if (orderBoxList.size() <= 0) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
			orderApply.setOrderBoxList(orderBoxList);
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
