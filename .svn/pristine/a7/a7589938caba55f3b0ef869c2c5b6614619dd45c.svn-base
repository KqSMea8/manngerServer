package com.lmxf.post.tradepkg.partner;

import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.entity.vending.VendingStock;
import com.lmxf.post.tradepkg.common.RespHead;

public class VendingProductInfoResp extends RespHead {

	public String CreateJson(Object... parm) {
		List<VendingStock> typeList = (List<VendingStock>) parm[4];
		try {
			JSONObject rootObject = super.getJSONObject((String) parm[0], (String) parm[1], (Integer) parm[2], (Integer) parm[3]);
			JSONObject JSONBody = new JSONObject();
			JSONArray JSONBoxArray = new JSONArray();
			JSONObject JSONbox = null;
			for (int i = 0; i < typeList.size(); i++) {
				JSONbox = new JSONObject();
				JSONbox.put("ProductId", "" + typeList.get(i).getProductId());
				JSONbox.put("ClassifyId", "" + typeList.get(i).getTypeId());
				JSONbox.put("ProductName", "" + StringEscapeUtils.unescapeHtml4(typeList.get(i).getProductName()));
				JSONbox.put("SalePrice", "" + typeList.get(i).getSalePrice());
				JSONbox.put("SaleNum", "" + typeList.get(i).getNum());
				JSONbox.put("PicUrl", ""+ typeList.get(i).getPicUrl());
				JSONBoxArray.add(JSONbox);
			}
			JSONBody.put("ProductInfo", JSONBoxArray);
			JSONObject JSONObject = (JSONObject) rootObject.get(GConstent.ZxmlRoot);
			JSONObject.put(GConstent.ZxmlBody, JSONBody);
			return rootObject.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}
}
