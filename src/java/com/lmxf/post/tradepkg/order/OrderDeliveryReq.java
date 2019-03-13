package com.lmxf.post.tradepkg.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.putils.utils.StringUtils;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.GParameter;
import com.lmxf.post.core.utils.JSONUtils;
import com.lmxf.post.entity.order.OrderApply;
import com.lmxf.post.entity.order.OrderBox;
import com.lmxf.post.entity.order.OrderProduct;
import com.lmxf.post.tradepkg.common.ReqHead;

public class OrderDeliveryReq extends ReqHead {
	@SuppressWarnings("unchecked")
	public Map parseXml(String report_json) {
		OrderApply orderApply = new OrderApply();
		Map r = new HashMap();
		try {
			// json解析协议
			JSONObject JSONBody = JSONUtils.getHeader(report_json);
			String bcode = JSONBody.getString("BCode");
			JSONBody = JSONUtils.getBody(report_json);
			orderApply.setSiteId(JSONBody.getString("SiteId"));
			orderApply.setLoginId(JSONBody.getString("LoginId"));
			orderApply.setLoginName(JSONBody.getString("LoginName"));
			orderApply.setCreateTime(JSONBody.getString("ApplyTime"));
			orderApply.setPayType(JSONBody.getString("PayType"));
			if (JSONBody.containsKey("SaleChannel")) {
				orderApply.setSaleChannel(JSONBody.getString("SaleChannel"));
			} else {
				orderApply.setSaleChannel(GParameter.saleChannel_bigScreenAndroidTerminal);
			}
			if (orderApply.getSiteId() == null || "".equals(orderApply.getSiteId())) {
				r.put("code", GConstent.Request_SiteId_No_Define);
				return r;
			}
			if (orderApply.getLoginId() == null || "".equals(orderApply.getLoginId())) {
				r.put("code", GConstent.Request_LoginId_No_Define);
				return r;
			}
			if (orderApply.getLoginName() == null || "".equals(orderApply.getLoginName())) {
				r.put("code", GConstent.Request_LoginName_No_Define);
				return r;
			}
			if (orderApply.getCreateTime() == null || "".equals(orderApply.getCreateTime())) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
			if (orderApply.getPayType() == null || "".equals(orderApply.getPayType())) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
			// 获取货道出货信息
			JSONArray JSONArray = JSONBody.getJSONArray("LaneInfo");
			if (JSONArray == null || JSONArray.size() <= 0) {
				r.put("code", GConstent.Product_Info_No_Found);
				return r;
			}
			List<OrderBox> orderBoxList = new ArrayList();
			List<OrderBox> orderLaneList = new ArrayList();
			Map<String, Integer> productInfoMap = new HashMap();
			OrderBox orderBox = null;
			OrderBox orderBoxLane=null;
			for (int i = 0; i < JSONArray.size(); i++) {
				JSONObject jSONObject = (JSONObject) JSONArray.get(i);
				int lNum = jSONObject.getInt("LNum");
				int LaneSId = jSONObject.getInt("LaneSId");
				int LaneEId = jSONObject.getInt("LaneEId");
				String ProductId = jSONObject.getString("ProductId");
				orderBoxLane=new OrderBox();
				if (lNum <= 0) {
					r.put("code", GConstent.Request_parameter_No_Define);
					return r;
				}
				if (productInfoMap.get(ProductId) != null) {
					productInfoMap.put(ProductId, productInfoMap.get(ProductId)+ lNum);
				}else{
					productInfoMap.put(ProductId, lNum);
				}
				orderBoxLane.setLaneSId(LaneSId);
				orderBoxLane.setLaneEId(LaneEId);
				orderBoxLane.setSaleNum(lNum);
				orderBoxLane.setProductId(ProductId);
				orderLaneList.add(orderBoxLane);
				for (int k = 0; k < lNum; k++) {
					orderBox = new OrderBox();
					orderBox.setProductId(ProductId);
					orderBox.setLaneSId(LaneSId);
					orderBox.setLaneEId(LaneEId);
					if (orderBox.getLaneSId() == 0 || "".equals(orderBox.getLaneSId())) {
						r.put("code", GConstent.Request_parameter_No_Define);
						return r;
					}
					if (orderBox.getLaneEId() == 0 || "".equals(orderBox.getLaneEId())) {
						r.put("code", GConstent.Request_parameter_No_Define);
						return r;
					}
					orderBoxList.add(orderBox);
				}
			}
			if (orderBoxList.size() <= 0) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
			orderApply.setOrderLaneList(orderLaneList);
			// 获取出货商品信息
			List<OrderProduct> orderProductList = new ArrayList();
			OrderProduct orderProduct = null;
			for(String productId:productInfoMap.keySet()){
				orderProduct=new OrderProduct();
				orderProduct.setProductId(productId);
				orderProduct.setSaleNum(productInfoMap.get(productId));
				orderProductList.add(orderProduct);
			}
			orderApply.setpNum(orderBoxList.size());
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
