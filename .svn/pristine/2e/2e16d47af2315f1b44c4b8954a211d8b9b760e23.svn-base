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
import com.lmxf.post.entity.vending.VendingLanep;
import com.lmxf.post.repository.vending.VendingLanepDao;
import com.lmxf.post.tradepkg.common.ReqHead;

public class OrderDeliveryPayReq extends ReqHead {
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
			orderApply.setLaneSId(JSONBody.getString("laneSId"));
			orderApply.setLoginName(JSONBody.getString("laneEId"));
			orderApply.setPayType(JSONBody.getString("PayType"));
			if (orderApply.getSiteId() == null || "".equals(orderApply.getSiteId())) {
				r.put("code", GConstent.Request_SiteId_No_Define);
				return r;
			}
			if (orderApply.getPayType() == null || "".equals(orderApply.getPayType())) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
			List<OrderProduct> orderProductList = new ArrayList();

			List<OrderBox> orderBoxList = new ArrayList();
			OrderBox orderBox =new OrderBox();
			orderBox.setOutIndex( 1);
			orderBox.setLaneSId(Integer.parseInt(JSONBody.getString("LaneSId")));
			orderBox.setLaneEId(Integer.parseInt(JSONBody.getString("LaneEId")));
			if (orderBox.getLaneSId() == 0 || "".equals(orderBox.getLaneSId())) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
			if (orderBox.getLaneEId()== 0 || "".equals(orderBox.getLaneEId())) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
			VendingLanep vendingLanep=new VendingLanep();
			vendingLanep.setSiteId(orderBox.getSiteId());
			vendingLanep.setLaneSId(orderBox.getLaneSId());
			vendingLanep.setLaneEId(orderBox.getLaneEId());
			VendingLanep vendingLanepR=vendingLanepDao.findOne(vendingLanep);
			if(vendingLanepR==null){
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
			orderBox.setProductId(vendingLanepR.getProductId());
			OrderProduct orderProduct = new OrderProduct();
			orderProduct.setProductId(vendingLanepR.getProductId());
			orderProduct.setSaleNum(1);
			orderBoxList.add(orderBox);
			
			orderProduct.setOrderBoxList(orderBoxList);
			orderProductList.add(orderProduct);
			orderApply.setpNum(1);
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
