package com.lmxf.post.tradepkg.dataSynch;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.entity.order.OrderApply;
import com.lmxf.post.entity.order.OrderBox;
import com.lmxf.post.entity.order.OrderProduct;
import com.lmxf.post.tradepkg.common.RespHead;

public class OrderIssuedResp extends RespHead {

	public String CreateJson(Object... parm) {
		OrderApply orderApply = (OrderApply) parm[4];
		try {
			JSONObject rootObject = super.getJSONObject((String) parm[0], (String) parm[1], (Integer) parm[2], (Integer) parm[3]);
			JSONObject JSONBody = new JSONObject();
			JSONBody.put("SiteId", "" + orderApply.getSiteId());
			JSONBody.put("OrderId", "" + orderApply.getOrderId());
			JSONBody.put("LoginId", "" + orderApply.getLoginId());
			JSONBody.put("LoginName", "" + orderApply.getLoginName());
			JSONBody.put("FetchPWD", "" + orderApply.getPassWord());
			JSONBody.put("EncryptType", "" + orderApply.getEncryptionType());
			JSONBody.put("Salt", "" + orderApply.getSlat());
			JSONBody.put("ApplyTime", "" + orderApply.getCreateTime());
			JSONBody.put("PayType", "" + orderApply.getPayType());
			JSONBody.put("PayPrice", "" + orderApply.getPayPrice());
			JSONBody.put("FavPrice", "" + orderApply.getFavPrice());
			JSONBody.put("FavWay", "" + orderApply.getFavWay());
			JSONArray JSONDetailArray = new JSONArray();
			for (OrderProduct orderProduct : orderApply.getOrderProductList()) {
				JSONObject JSONDetail = new JSONObject();
				JSONDetail.put("ProductId", orderProduct.getProductId());
				//商品货道信息
				JSONArray JSONBoxOneArray = new JSONArray();
				for(OrderBox orderBox:orderProduct.getOrderBoxList()){
					JSONObject JSONBox = new JSONObject();
					JSONBox.put("Num", "" + orderBox.getNum());
					JSONBox.put("laneSId", "" + orderBox.getLaneSId());
					JSONBox.put("laneEId", "" + orderBox.getLaneEId());
					JSONBoxOneArray.add(JSONBox);
				}
				JSONDetail.put("LaneInfo", JSONBoxOneArray);
				JSONDetailArray.add(JSONDetail);
			}
			JSONBody.put("ProductInfo", JSONDetailArray);
			JSONObject JSONObject = (JSONObject) rootObject.get(GConstent.ZxmlRoot);
			JSONObject.put(GConstent.ZxmlBody, JSONBody);
			return rootObject.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}
	
	public String CreateIssueJson(Object... parm) {
		OrderApply orderApply = (OrderApply) parm[4];
		List<OrderBox> orderBoxList =  (List<OrderBox>) parm[5];
		try {
			JSONObject rootObject = super.getIssueJSONObject((String) parm[0], (String) parm[1], (String) parm[2], (String)  parm[3]);
			JSONObject JSONBody = new JSONObject();
			JSONBody.put("SiteId", "" + orderApply.getSiteId());
			JSONBody.put("OrderId", "" + orderApply.getOrderId());
			JSONBody.put("LoginId", "" + orderApply.getLoginId());
			JSONBody.put("LoginName", "" + orderApply.getLoginName());
			JSONBody.put("FetchPWD", "" + orderApply.getPassWord());
			JSONBody.put("EncryptType", "" + orderApply.getEncryptionType());
			JSONBody.put("Salt", "" + orderApply.getSlat());
			JSONBody.put("ApplyTime", "" + orderApply.getCreateTime());
			JSONBody.put("PayType", "" + orderApply.getPayType());
			JSONBody.put("PayPrice", "" + orderApply.getPayPrice());
			JSONBody.put("FavPrice", "" + orderApply.getFavPrice());
			JSONBody.put("FavWay", "" + orderApply.getFavWay());
			JSONArray JSONLaneArray = new JSONArray();
			//商品货道信息
			for(OrderBox orderBox:orderBoxList){
				JSONObject JSONBox = new JSONObject();
				JSONBox.put("ProBoxId", "" + orderBox.getProboxId());
				JSONBox.put("LaneSId", "" + orderBox.getLaneSId());
				JSONBox.put("LaneEId", "" + orderBox.getLaneEId());
				JSONBox.put("ProductId", "" + orderBox.getProductId());
				JSONLaneArray.add(JSONBox);
			}
			JSONBody.put("LaneInfo", JSONLaneArray);
			JSONObject JSONObject = (JSONObject) rootObject.get(GConstent.ZxmlRoot);
			JSONObject.put(GConstent.ZxmlBody, JSONBody);
			return rootObject.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

}
