package com.lmxf.post.tradepkg.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.putils.utils.DateUtil;
import org.apache.commons.putils.utils.StringUtils;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.GParameter;
import com.lmxf.post.core.utils.JSONUtils;
import com.lmxf.post.entity.order.OrderApply;
import com.lmxf.post.entity.order.OrderBox;
import com.lmxf.post.entity.order.OrderProduct;
import com.lmxf.post.entity.vending.VendingLanep;
import com.lmxf.post.repository.vending.VendingLanepDao;
import com.lmxf.post.tradepkg.common.ReqHead;

public class SCMOrderDeliveryReq extends ReqHead {
	private VendingLanepDao vendingLanepDao;
	@SuppressWarnings("unchecked")
	public Map parseXml(String report_json) {
		OrderApply orderApply = new OrderApply();
		Map r = new HashMap();
		try {
			// json解析协议
			JSONObject JSONBody = JSONUtils.getHeader(report_json);
			String bcode = JSONBody.getString("BCode");

			JSONBody = JSONUtils.getBody(report_json);
			if(JSONBody.containsKey("SaleChannel")){
				orderApply.setSaleChannel(JSONBody.getString("SaleChannel"));
			}else{
				orderApply.setSaleChannel(GParameter.saleChannel_bigScreenAndroidTerminal);
			}
			orderApply.setSiteId(JSONBody.getString("SiteId"));
			orderApply.setLoginId(JSONBody.getString("LoginId"));
			orderApply.setProductId(JSONBody.getString("ProductId"));
			if (orderApply.getSiteId() == null || "".equals(orderApply.getSiteId())) {
				r.put("code", GConstent.Request_SiteId_No_Define);
				return r;
			}
			if (orderApply.getLoginId() == null || "".equals(orderApply.getLoginId())) {
				r.put("code", GConstent.Request_LoginId_No_Define);
				return r;
			}
			if (orderApply.getProductId() == null || "".equals(orderApply.getProductId())) {
				r.put("code", GConstent.Request_LoginName_No_Define);
				return r;
			}
			//统计商品数量
			String[] productIds=orderApply.getProductId().split(",");
			Map<String,Integer> productMap=new HashMap();
			for(String productId:productIds){
				if(productMap.get(productId)==null){
					productMap.put(productId, 1);
				}else{
					productMap.put(productId,productMap.get(productId)+1);
				}
			}
			List<OrderProduct> orderProductList = new ArrayList();
			OrderProduct orderProduct = null;
			int total_num = 0;
			for (String productId:productMap.keySet()) {
				orderProduct = new OrderProduct();
				orderProduct.setProductId(productId);
				if (StringUtils.isEmpty(orderProduct.getProductId())) {            
 					r.put("code", GConstent.Request_parameter_No_Define);
					return r;
				}
				int product_num = productMap.get(productId);
				List<OrderBox> orderBoxList = new ArrayList();
				//根据商品编号查询售货机货道商品信息,RFID柜子规定 一个商品一个货道 不能多个货道 同一个商品
				OrderBox orderBox = null;
				VendingLanep vendingLanep=new VendingLanep();
				vendingLanep.setSiteId(orderApply.getSiteId());
				vendingLanep.setProductId(productId);
				VendingLanep vendingLane=vendingLanepDao.findOneSCM(vendingLanep);
				if(vendingLane==null){
					r.put("code", GConstent.Request_parameter_No_Define);
					return r;
				}
				int outIndex = 0;
				for(int k = 0; k < product_num; k++){
					orderBox = new OrderBox();
					orderBox.setProductId(orderProduct.getProductId());
					orderBox.setOutIndex(outIndex + 1);
					orderBox.setLaneSId(vendingLane.getLaneSId());
					orderBox.setLaneEId(vendingLane.getLaneEId());
					if (orderBox.getLaneSId() == 0 || "".equals(orderBox.getLaneSId())) {
						r.put("code", GConstent.Request_parameter_No_Define);
						return r;
					}
					if (orderBox.getLaneEId()== 0 || "".equals(orderBox.getLaneEId())) {
						r.put("code", GConstent.Request_parameter_No_Define);
						return r;
					}
					orderBoxList.add(orderBox);
				}
				total_num = total_num + product_num;
				orderProduct.setSaleNum(product_num);
				orderProduct.setOrderBoxList(orderBoxList);
				orderProductList.add(orderProduct);
			}
			if (orderProductList.size() <= 0) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
			orderApply.setpNum(total_num);
			orderApply.setPayType(GParameter.payType_weChat);
			orderApply.setCreateTime(DateUtil.getNow());
			orderApply.setLoginName(orderApply.getLoginId());
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
	public VendingLanepDao getVendingLanepDao() {
		return vendingLanepDao;
	}
	public void setVendingLanepDao(VendingLanepDao vendingLanepDao) {
		this.vendingLanepDao = vendingLanepDao;
	}

}
