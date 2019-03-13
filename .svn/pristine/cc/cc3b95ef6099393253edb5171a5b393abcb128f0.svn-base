package com.lmxf.post.tradepkg.partner;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.entity.order.OrderChange;
import com.lmxf.post.tradepkg.common.RespHead;

public class OrderChangeResp extends RespHead {

	public String CreateJson(Object... parm) {
		List<OrderChange> typeList = (List<OrderChange>) parm[4];
		JSONObject rootObject = super.getJSONObject((String) parm[0], (String) parm[1], (Integer) parm[2], (Integer) parm[3]);
		JSONObject JSONBody = new JSONObject();
		JSONObject JSONObject = (JSONObject) rootObject.get(GConstent.ZxmlRoot);
		JSONArray JSONBoxArray = new JSONArray();
		JSONObject JSONbox = null;
		for (int i = 0; i < typeList.size(); i++) {
			JSONbox = new JSONObject();
			JSONbox.put("OrderId", "" + typeList.get(i).getOrderId());
			JSONbox.put("SiteId", "" + typeList.get(i).getSiteId());
			JSONbox.put("OperAction", "" + typeList.get(i).getOperAction());
			JSONbox.put("OperId", "" + typeList.get(i).getOperId());
			JSONbox.put("OperCont", "" + typeList.get(i).getOperCont());
			JSONbox.put("OperTime", "" + typeList.get(i).getOperTime());
			JSONBoxArray.add(JSONbox);
		}
		JSONBody.put("OrderChange", JSONBoxArray);
		JSONObject.put(GConstent.ZxmlBody, JSONBody);
		return rootObject.toString();
	}
}