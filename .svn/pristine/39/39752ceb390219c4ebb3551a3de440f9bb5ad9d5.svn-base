package com.lmxf.post.tradepkg.partner;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.GParameter;
import com.lmxf.post.entity.order.OrderApply;
import com.lmxf.post.entity.order.OrderProduct;
import com.lmxf.post.tradepkg.common.RespHead;

public class CustomerOrderInfoResp extends RespHead {

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
				JSONbox.put("FetchPassWord", "" + typeList.get(i).getPassWord());
				JSONbox.put("EncryptType", "" + typeList.get(i).getEncryptionType());
				JSONbox.put("Salt", "" + typeList.get(i).getSlat());
				JSONbox.put("SiteId", "" + typeList.get(i).getSiteId());
				JSONbox.put("SiteName", ""+ typeList.get(i).getSiteName());
				JSONbox.put("SaleChannel", ""+ typeList.get(i).getSaleChannel());
				JSONbox.put("ApplyTime", ""+ typeList.get(i).getCreateTime());
				JSONbox.put("FetchOverTime", ""+ typeList.get(i).getFetchOverTime());
				JSONbox.put("CurState", ""+ typeList.get(i).getCurState());
				JSONbox.put("StateTime", ""+ typeList.get(i).getStateTime());
				if(typeList.get(i).getPayState().equals(GParameter.payState_wait)){
					JSONbox.put("PayState", "00");
				}else if(typeList.get(i).getPayState().equals(GParameter.payState_success)){
					JSONbox.put("PayState", "01");
				}else if(typeList.get(i).getPayState().equals(GParameter.payState_failed)){
					JSONbox.put("PayState", "02");
				}
				JSONbox.put("AbnomarlState", ""+ typeList.get(i).getAbnomarlState());
				JSONbox.put("PayPrice", ""+ typeList.get(i).getPayPrice());
				JSONbox.put("FavPrice", ""+ typeList.get(i).getFavPrice());
				JSONbox.put("FayWay", ""+ typeList.get(i).getFavWay());
				JSONArray JSONDetailArray = new JSONArray();
				List<OrderProduct> orderProductList = typeList.get(i).getOrderProductList();
				if(null != orderProductList && orderProductList.size() > 0){
					for (OrderProduct orderProduct : orderProductList) {
						JSONObject JSONDetail = new JSONObject();
						JSONDetail.put("ProductId", "" + orderProduct.getProductId());
						JSONDetail.put("ProductName", "" + orderProduct.getProductName());
						JSONDetail.put("Num", "" + orderProduct.getSaleNum());
						JSONDetail.put("Price", "" + orderProduct.getSalePrice()*orderProduct.getSaleNum());
						JSONDetailArray.add(JSONDetail);
					}
				}
				JSONbox.put("ProductInfo", JSONDetailArray);
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
