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
import com.lmxf.post.entity.order.OrderBox;
import com.lmxf.post.entity.order.OrderProduct;
import com.lmxf.post.tradepkg.common.ReqHead;

public class OrderApplyLaneReq extends ReqHead {
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
				orderApply.setSaleChannel(GParameter.saleChannel_miniWeChatAppTerminal);
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
			List<OrderBox> orderBoxList = new ArrayList();
			List<OrderBox> orderLaneList = new ArrayList();
			OrderBox orderBox = null;
			OrderBox orderBoxLane=null;
			Map<String,Integer> productInfoMap=new HashMap();
			//初始化OrderBoxList集合
			for (int i = 0; i < JSONArray.size(); i++) {
				JSONObject jSONObject = (JSONObject) JSONArray.get(i);
				int num=Integer.parseInt(jSONObject.getString("Num"));
				String productId=jSONObject.getString("ProductId");
				int laneSId=jSONObject.getInt("LaneSId");
				int laneEId=jSONObject.getInt("LaneEId");
				orderBoxLane=new OrderBox();
				if(num<=0){
					r.put("code", GConstent.Product_Info_No_Found);
					return r;
				}else{
					if(productInfoMap.get(productId)!=null){
						productInfoMap.put(productId, productInfoMap.get(productId)+num);
					}else{
						productInfoMap.put(productId, num);
					}
				}
				orderBoxLane.setLaneSId(laneSId);
				orderBoxLane.setLaneEId(laneEId);
				orderBoxLane.setSaleNum(num);
				orderBoxLane.setProductId(productId);
				orderLaneList.add(orderBoxLane);
				for(int saleNum=0;saleNum<num;saleNum++){
					orderBox=new OrderBox();
					orderBox.setProductId(productId);
					orderBox.setLaneSId(jSONObject.getInt("LaneSId"));
					orderBox.setLaneEId(jSONObject.getInt("LaneEId"));
					if (StringUtils.isEmpty(orderBox.getProductId())) {            
	 					r.put("code", GConstent.Request_parameter_No_Define);
						return r;
					}
					if (orderBox.getLaneSId() <= 0) {
						r.put("code", GConstent.Request_parameter_No_Define);
						return r;
					}
					if (orderBox.getLaneEId() <= 0) {
						r.put("code", GConstent.Request_parameter_No_Define);
						return r;
					}
				}
				orderBoxList.add(orderBox);
			}
			if (orderBoxList.size() <= 0) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
			orderApply.setOrderBoxList(orderBoxList);
			orderApply.setOrderLaneList(orderLaneList);
			//初始化OrderProduct集合
			List<OrderProduct> orderProductList = new ArrayList();
			OrderProduct orderProduct=null;
			for(String productId:productInfoMap.keySet()){
				orderProduct=new OrderProduct();
				orderProduct.setProductId(productId);
				orderProduct.setSaleNum(productInfoMap.get(productId));
				orderProductList.add(orderProduct);
			}
			orderApply.setOrderProductList(orderProductList);
			//销售数量
			orderApply.setpNum(orderBoxList.size());
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
