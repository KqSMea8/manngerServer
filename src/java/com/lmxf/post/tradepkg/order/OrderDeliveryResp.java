package com.lmxf.post.tradepkg.order;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.entity.order.OrderApply;
import com.lmxf.post.entity.order.OrderBox;
import com.lmxf.post.tradepkg.common.RespHead;

public class OrderDeliveryResp extends RespHead {

	public String CreateJson(Object... parm) {
		OrderApply orderApply = (OrderApply) parm[4];
		List<OrderBox> orderBoxList= (List<OrderBox>) parm[5];
		try {
			JSONObject rootObject = super.getJSONObject((String) parm[0], (String) parm[1], (Integer) parm[2], (Integer) parm[3]);
			JSONObject JSONBody = new JSONObject();
			JSONBody.put("OrderId", "" + orderApply.getOrderId());
			JSONBody.put("PayPrice", "" + orderApply.getPayPrice());
			JSONBody.put("FavPrice", "" + orderApply.getFavPrice());
			JSONBody.put("QRCode", "" + orderApply.getImgUrl());
			JSONBody.put("QRUrl", "" + orderApply.getCodeUrl());
			JSONBody.put("FetchPassWord", "" + orderApply.getPassWord());
			JSONBody.put("EncryptType", "" + orderApply.getEncryptionType());
			JSONBody.put("Salt", "" + orderApply.getSlat());
			JSONArray JSONArray = new JSONArray();
			for(OrderBox orderBox:orderBoxList){
				JSONObject JSONDetails = new JSONObject();
				JSONDetails.put("ProboxId", orderBox.getProboxId());
				JSONDetails.put("LaneSId", orderBox.getLaneSId());
				JSONDetails.put("LaneEId", orderBox.getLaneEId());
				JSONDetails.put("ProductId", orderBox.getProductId());
				JSONArray.add(JSONDetails);
			}
			JSONBody.put("LaneInfo", JSONArray);
			JSONObject JSONObject = (JSONObject) rootObject.get(GConstent.ZxmlRoot);
			JSONObject.put(GConstent.ZxmlBody, JSONBody);
			return rootObject.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

}