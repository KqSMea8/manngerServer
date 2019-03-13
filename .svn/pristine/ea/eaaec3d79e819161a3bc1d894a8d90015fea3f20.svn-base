package com.lmxf.post.tradepkg.partner;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.GParameter;
import com.lmxf.post.entity.order.OrderApply;
import com.lmxf.post.tradepkg.common.RespHead;

public class OrderApplyLaneResp extends RespHead {

	public String CreateJson(Object... parm) {
		OrderApply orderApply = (OrderApply) parm[4];
		try {
			JSONObject rootObject = super.getJSONObject((String) parm[0], (String) parm[1], (Integer) parm[2], (Integer) parm[3]);
			JSONObject JSONBody = new JSONObject();
			JSONObject JSONObject = (JSONObject) rootObject.get(GConstent.ZxmlRoot);
			JSONBody.put("OrderId", "" + orderApply.getOrderId());
			JSONBody.put("PayPrice", "" + orderApply.getPayPrice());
			JSONBody.put("FavPrice", "" + orderApply.getFavPrice());
			JSONBody.put("FetchOverTime", "" + orderApply.getFetchOverTime());
			JSONBody.put("FetchPassWord", "" + orderApply.getPassWord());
			JSONBody.put("EncryptType", "" + orderApply.getEncryptionType());
			JSONBody.put("Salt", "" + orderApply.getSlat());
			if(orderApply.getPayType().equals(GParameter.payType_weChatG) || orderApply.getPayType().equals(GParameter.payType_weChatApp)){
				JSONArray JSONBoxArray = new JSONArray();
				JSONObject JSONbox = new JSONObject();
				JSONbox.put("AppId", "" + orderApply.getAppId());
				JSONbox.put("TimeStamp", "" + orderApply.getTimeStamp());
				JSONbox.put("NonceStr", "" + orderApply.getNonceStr());
				JSONbox.put("Package", "" + orderApply.getPckage());
				JSONbox.put("SignType", "" + orderApply.getSignType());
				JSONbox.put("PaySign", "" + orderApply.getPaySign());
				JSONBoxArray.add(JSONbox);
				JSONBody.put("WechatPayInfo", JSONBoxArray);
			}
			JSONObject.put(GConstent.ZxmlBody, JSONBody);
			return rootObject.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

}