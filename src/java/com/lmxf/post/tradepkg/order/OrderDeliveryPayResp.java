package com.lmxf.post.tradepkg.order;

import net.sf.json.JSONObject;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.entity.order.OrderApply;
import com.lmxf.post.tradepkg.common.RespHead;

public class OrderDeliveryPayResp extends RespHead {

	public String CreateJson(Object... parm) {
		OrderApply orderApply = (OrderApply) parm[4];
		try {
			JSONObject rootObject = super.getJSONObject((String) parm[0], (String) parm[1], (Integer) parm[2], (Integer) parm[3]);
			JSONObject JSONBody = new JSONObject();
			JSONBody.put("OrderId", "" + orderApply.getOrderId());
			JSONBody.put("QRCode", "" + orderApply.getImgUrl());
			JSONBody.put("QRUrl", "" + orderApply.getCodeUrl());
			JSONObject JSONObject = (JSONObject) rootObject.get(GConstent.ZxmlRoot);
			JSONObject.put(GConstent.ZxmlBody, JSONBody);
			return rootObject.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

}