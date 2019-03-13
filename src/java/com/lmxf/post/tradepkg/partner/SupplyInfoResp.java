package com.lmxf.post.tradepkg.partner;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.entity.supply.SupplyOrder;
import com.lmxf.post.entity.supply.SupplyVOrder;
import com.lmxf.post.entity.supply.SupplyVproduct;
import com.lmxf.post.entity.vending.Vending;
import com.lmxf.post.tradepkg.common.RespHead;

public class SupplyInfoResp extends RespHead {

	@SuppressWarnings("unchecked")
	public String CreateJson(Object... parm) {
		List<SupplyOrder> typeList = (List<SupplyOrder>) parm[4];
		try {
			JSONObject rootObject = super.getJSONObject((String) parm[0], (String) parm[1], (Integer) parm[2], (Integer) parm[3]);
			JSONObject JSONBody = new JSONObject();
			JSONArray JSONBoxArray = new JSONArray();
			JSONObject JSONbox = null;
			for (SupplyOrder supplyOrder : typeList) {
				JSONbox = new JSONObject();
				JSONbox.put("SOrderId", "" + supplyOrder.getsOrderId());
				JSONbox.put("LineId", "" + supplyOrder.getLineId());
				JSONbox.put("LineName", "" + supplyOrder.getLineName());
				JSONbox.put("DistrictName", "" + supplyOrder.getDistrictName());
				JSONbox.put("CurState", "" + supplyOrder.getCurState());
				JSONbox.put("StateTime", "" + supplyOrder.getStateTime());
				JSONbox.put("StockState", "" + supplyOrder.getStockState());
				JSONArray JSONVendingArray = new JSONArray();
				for (SupplyVOrder supplyVOrder : supplyOrder.getSupplyVOrderList()) {
					JSONObject JSONVending = new JSONObject();
					JSONVending.put("SiteId", "" + supplyVOrder.getSiteId());
					JSONVending.put("VOrderId", "" + supplyVOrder.getvOrderId());
					JSONVending.put("SiteName", "" + supplyVOrder.getVending().getSiteName());
					JSONVending.put("PMaxNum", "" + supplyVOrder.getVending().getpMaxNum());
					JSONVending.put("PCurNum", "" + supplyVOrder.getVending().getpCurNum());
					JSONVending.put("NetSate", "" + supplyVOrder.getVending().getNetSate());
					JSONVending.put("Address", "" + supplyVOrder.getVending().getAddress());
					JSONVending.put("Latitude", "" + supplyVOrder.getVending().getLatitude());
					JSONVending.put("Longitude", "" + supplyVOrder.getVending().getLongitude());
					JSONVending.put("CurState", "" + supplyVOrder.getCurState());
					JSONVending.put("StateTime", "" + supplyVOrder.getStateTime());
					JSONVending.put("Address", "" + supplyVOrder.getVending().getAddress());
					JSONVending.put("CabinetType", "" + supplyVOrder.getVending().getCabinetType());
					JSONArray JSONProductArray = new JSONArray();
					for (SupplyVproduct supplyVproduct : supplyVOrder.getSupplyVProductList()) {
						JSONObject JSONProduct = new JSONObject();
						JSONProduct.put("SLProductId", "" + supplyVproduct.getsVendingId());
						JSONProduct.put("ProductId", "" + supplyVproduct.getProductId());
						JSONProduct.put("ProductName", "" + supplyVproduct.getProductName());
						JSONProduct.put("PicUrl", "" + supplyVproduct.getProductPic());
						JSONProduct.put("LaneSId", "" + supplyVproduct.getLaneSId());
						JSONProduct.put("LaneEId", "" + supplyVproduct.getLaneEId());
						JSONProduct.put("SupplyNum", "" + supplyVproduct.getSupplyNum());
						JSONProduct.put("RSupplyNum", "" + supplyVproduct.getrSupplyNum());
						JSONProduct.put("State", "" + supplyVproduct.getCurState());
						if (supplyVproduct.getVendingLanep() != null) {
							JSONProduct.put("CurNum", "" + supplyVproduct.getVendingLanep().getCurCap());
							JSONProduct.put("MaxNum", "" + supplyVproduct.getVendingLanep().getCapacity());
							JSONProduct.put("seqId", "" + supplyVproduct.getVendingLanep().getSeqId());
							JSONProduct.put("handType", "" + supplyVproduct.getVendingLanep().getHangType());
						} else {
							JSONProduct.put("CurNum", "0");
							JSONProduct.put("MaxNum", "0");
							JSONProduct.put("seqId", "0");
							JSONProduct.put("handType", "0");
						}
						JSONProductArray.add(JSONProduct);
					}
					JSONVending.put("ProductInfo", JSONProductArray);
					JSONVendingArray.add(JSONVending);
				}
				JSONbox.put("SiteInfo", JSONVendingArray);
				JSONBoxArray.add(JSONbox);
			}
			JSONBody.put("SupplyInfo", JSONBoxArray);
			JSONObject JSONObject = (JSONObject) rootObject.get(GConstent.ZxmlRoot);
			JSONObject.put(GConstent.ZxmlBody, JSONBody);
			return rootObject.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}
}
