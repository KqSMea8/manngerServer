package com.lmxf.post.tradepkg.partner;

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
import com.lmxf.post.entity.order.OrderProduct;
import com.lmxf.post.tradepkg.common.ReqHead;

public class OrderApplyReq extends ReqHead {
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
			if(JSONBody.containsKey("SaleChannel")){
				orderApply.setSaleChannel(JSONBody.getString("SaleChannel"));
			}else{
				orderApply.setSaleChannel(GParameter.saleChannel_weChatGTerminal);
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
			JSONArray JSONArray = JSONBody.getJSONArray("ProductInfo");
			if (JSONArray == null || JSONArray.size() <= 0) {
				r.put("code", GConstent.Product_Info_No_Found);
				return r;
			}
			List<OrderProduct> orderProductList = new ArrayList();
			OrderProduct orderProduct = null;
			int total_num = 0;
			float total_price = 0;
			for (int i = 0; i < JSONArray.size(); i++) {
				JSONObject jSONObject = (JSONObject) JSONArray.get(i);
				orderProduct = new OrderProduct();
				orderProduct.setProductId(jSONObject.getString("ProductId"));
				orderProduct.setSaleNum(Integer.parseInt(jSONObject.getString("Num")));
				orderProduct.setNormalPrice(Float.parseFloat(jSONObject.getString("Price")));
				total_num = total_num + orderProduct.getSaleNum();
				total_price = total_price +  orderProduct.getNormalPrice();
				if (StringUtils.isEmpty(orderProduct.getProductId())) {            
 					r.put("code", GConstent.Request_parameter_No_Define);
					return r;
				}
				if (orderProduct.getSaleNum() == 0 || "".equals(orderProduct.getSaleNum())) {
					r.put("code", GConstent.Request_parameter_No_Define);
					return r;
				}
				if (orderProduct.getNormalPrice()== 0 || "".equals(orderProduct.getNormalPrice())) {
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
			orderApply.setpNum(total_num);
			orderApply.setSalePrice(total_price);
			//微信公众号
			if (orderApply.getPayType().equals(GParameter.payType_weChatG) || orderApply.getPayType().equals(GParameter.payType_weChatApp)){
				JSONArray WechatJSONArray = JSONBody.getJSONArray("WechatPayInfo");
				if (WechatJSONArray == null || WechatJSONArray.size() <= 0) {
					r.put("code", GConstent.WechatPayInfo_No_Found);
					return r;
				}
				for (int i = 0; i < WechatJSONArray.size(); i++) {
					JSONObject jSONObject = (JSONObject) WechatJSONArray.get(i);
					orderApply.setOpenId(jSONObject.getString("OpenId"));
				}
			}
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
