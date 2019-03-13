package com.lmxf.post.tradepkg.init;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.entity.order.OrderApply;
import com.lmxf.post.entity.order.OrderBox;
import com.lmxf.post.entity.order.OrderProduct;
import com.lmxf.post.tradepkg.common.RespHead;

public class OrderAllIssuedResp extends RespHead {

	public String CreateJson(Object... parm) {
		List<OrderApply> typeList = (List<OrderApply>) parm[4];
		try {
			JSONObject rootObject = super.getJSONObject((String) parm[0], (String) parm[1], (Integer) parm[2], (Integer) parm[3]);
			JSONObject JSONBody = new JSONObject();
			JSONArray JSONBoxArray = new JSONArray();
			JSONObject JSONbox = null;
			for (int i = 0; i < typeList.size(); i++) {
				JSONbox = new JSONObject();
				JSONbox.put("OrderId", "" + typeList.get(i).getOrderId());
				JSONbox.put("LoginId", "" + typeList.get(i).getLoginId());
				JSONbox.put("LoginName", "" + typeList.get(i).getLoginName());
				JSONbox.put("FetchPWD", "" + typeList.get(i).getPassWord());
				JSONbox.put("EncryptType", "" + typeList.get(i).getEncryptionType());
				JSONbox.put("Salt", "" + typeList.get(i).getSlat());
				JSONbox.put("ApplyTime", "" + typeList.get(i).getCreateTime());
				JSONbox.put("CurState", "" + typeList.get(i).getCurState());
				JSONbox.put("PayType", "" + typeList.get(i).getPayType());
				JSONbox.put("PayPrice", "" + typeList.get(i).getPayPrice());
				JSONbox.put("FavPrice", "" + typeList.get(i).getFavPrice());
				JSONbox.put("FavWay", "" + typeList.get(i).getFavWay());
				JSONbox.put("OutId", "" + typeList.get(i).getOutId());
				JSONArray JSONDetailArray = new JSONArray();
				for (OrderBox orderBox : typeList.get(i).getOrderBoxList()) {
					JSONObject JSONDetail = new JSONObject();
					// 商品货道信息
					JSONDetail.put("LaneSId", "" + orderBox.getLaneSId());
					JSONDetail.put("LaneEId", "" + orderBox.getLaneEId());
					JSONDetail.put("ProductId", orderBox.getProductId());
					JSONDetail.put("ProBoxId", orderBox.getProboxId());
					JSONDetailArray.add(JSONDetail);
				}
				JSONbox.put("LaneInfo", JSONDetailArray);
				JSONBoxArray.add(JSONbox);
			}
			JSONBody.put("OrderInfo", JSONBoxArray);
			JSONObject JSONObject = (JSONObject) rootObject.get(GConstent.ZxmlRoot);
			JSONObject.put(GConstent.ZxmlBody, JSONBody);
			return rootObject.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

}
