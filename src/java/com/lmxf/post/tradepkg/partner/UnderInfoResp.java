package com.lmxf.post.tradepkg.partner;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lmxf.post.core.utils.CacheUtils;
import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.GParameter;
import com.lmxf.post.entity.product.ProductInfo;
import com.lmxf.post.entity.product.ProductLunder;
import com.lmxf.post.entity.product.ProductUnder;
import com.lmxf.post.entity.product.ProductVUnder;
import com.lmxf.post.entity.supply.SupplyConfig;
import com.lmxf.post.entity.vending.Vending;
import com.lmxf.post.entity.vending.VendingLanep;
import com.lmxf.post.entity.vending.VendingLine;
import com.lmxf.post.tradepkg.common.RespHead;

public class UnderInfoResp extends RespHead {

	@SuppressWarnings("unchecked")
	public String CreateJson(Object... parm) {
		List<VendingLine> vendingLineList = (List<VendingLine>) parm[4];
		String bcode = (String) parm[5];
		JSONObject rootObject = super.getJSONObject((String) parm[0], (String) parm[1], (Integer) parm[2], (Integer) parm[3]);
		JSONObject JSONBody = new JSONObject();
		JSONArray JSONBoxArray = new JSONArray();
		JSONObject JSONbox = null;
		for (VendingLine vendingLine : vendingLineList) {
			JSONbox = new JSONObject();
			JSONbox.put("LineId", "" + vendingLine.getLineId());
			JSONbox.put("LineName", "" + vendingLine.getName());
			JSONbox.put("DistrictName", "" + vendingLine.getDistrictName());
			JSONArray vendingjsonArray = new JSONArray();
			JSONArray productjsonArray = new JSONArray();
			List<ProductVUnder> productVUnderList = vendingLine.getProductVUnder();
			for (ProductVUnder productVUnder : productVUnderList) {
				Vending vending = productVUnder.getVending();
				JSONObject JSONVending = new JSONObject();
				JSONVending.put("VUnderId", productVUnder.getvUnderId());
				JSONVending.put("SiteId", vending.getSiteId());
				JSONVending.put("SiteName", vending.getSiteName());
				JSONVending.put("PMaxNum", vending.getpMaxNum());
				JSONVending.put("PCurNum", vending.getpCurNum());
				JSONVending.put("NetSate", vending.getNetSate());
				JSONVending.put("Address", vending.getAddress());
				JSONVending.put("Latitude", vending.getLatitude());
				JSONVending.put("Longitude", vending.getLongitude());
				JSONVending.put("UnderNum", productVUnder.getUnderNum());
				JSONVending.put("UnderState", productVUnder.getCurState());
				JSONVending.put("UnderSTime", productVUnder.getStateTime());
				JSONVending.put("CabinetType", vending.getCabinetType());
				productjsonArray = new JSONArray();
				List<ProductLunder> productLunderList = productVUnder.getProductLunderList();
				for (ProductLunder productLunder : productLunderList) {
					JSONObject JSONVendingP = new JSONObject();
					JSONVendingP.put("UnderId", productLunder.getLunderId());
					JSONVendingP.put("ProductId", productLunder.getProductId());
					JSONVendingP.put("PicUrl", productLunder.getVendingLanep().getProductPic());
					String productPic = "{\"ProductPic\":" + productLunder.getVendingLanep().getProductPic() + "}";
					JSONObject jsonObjectR = JSONObject.fromObject(productPic);
					JSONArray jsonArray = jsonObjectR.getJSONArray("ProductPic");
					for (int j = 0; j < jsonArray.size(); j++) {
						JSONObject jSONObject = (JSONObject) jsonArray.get(j);
						String os = jSONObject.getString("os");
						String picUrl = jSONObject.getString("pic");
						if ((bcode.equals(GParameter.platCode_Platform) && os.equals(GParameter.platName_Platform)) || (bcode.equals(GParameter.platCode_WeChatOfficialAccountPurchase) && os.equals(GParameter.platName_WeChatOfficialAccountPurchase))
								|| (bcode.equals(GParameter.platCode_Terminal) && os.equals(GParameter.platName_Terminal)) || (bcode.equals(GParameter.platCode_WeChatAppletPurchase) && os.equals(GParameter.platName_WeChatAppletPurchase))
								|| (bcode.equals(GParameter.platCode_WeChatOfficialAccountReplenishment) && os.equals(GParameter.platName_WeChatOfficialAccountReplenishment))
								|| (bcode.equals(GParameter.platCode_AndroidPurchase) && os.equals(GParameter.platName_AndroidPurchase))
								|| (bcode.equals(GParameter.platCode_AndroidReplenishment) && os.equals(GParameter.platName_AndroidReplenishment))
								|| (bcode.equals(GParameter.platCode_MessageTransfer) && os.equals(GParameter.platName_MessageTransfer))) {
							JSONVendingP.put("PicUrl", picUrl);
							continue;
						}
					}
					JSONVendingP.put("LaneSId", productLunder.getLaneSId());
					JSONVendingP.put("LaneEId", productLunder.getLaneEId());
					JSONVendingP.put("State", productLunder.getCurState());
					JSONVendingP.put("UnderNum", "" + productLunder.getUnderNum());
					if (productLunder.getVendingLanep() != null) {
						JSONVendingP.put("CurNum", productLunder.getVendingLanep().getCurCap());
						JSONVendingP.put("seqId", "" + productLunder.getVendingLanep().getSeqId());
						JSONVendingP.put("handType", "" + productLunder.getVendingLanep().getHangType());
					} else {
						JSONVendingP.put("CurNum", "0");
						JSONVendingP.put("seqId", "0");
						JSONVendingP.put("handType", "0");
					}
					productjsonArray.add(JSONVendingP);
				}
				JSONVending.put("ProductInfo", productjsonArray);
				vendingjsonArray.add(JSONVending);
			}
			JSONbox.put("SiteInfo", vendingjsonArray);
			JSONBoxArray.add(JSONbox);
		}
		JSONBody.put("UnderInfo", JSONBoxArray);
		JSONObject JSONObject = (JSONObject) rootObject.get(GConstent.ZxmlRoot);
		JSONObject.put(GConstent.ZxmlBody, JSONBody);
		return rootObject.toString();
	}
}
