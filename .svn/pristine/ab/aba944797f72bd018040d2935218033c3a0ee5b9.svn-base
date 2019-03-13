package com.lmxf.post.tradepkg.partner;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.entity.product.ProductInfo;
import com.lmxf.post.tradepkg.common.RespHead;

public class ProductInfoResp extends RespHead{

	@SuppressWarnings("unchecked")
	public String CreateJson(Object... parm) {
		List<ProductInfo> typeList=(List<ProductInfo>) parm[4];
		try {
			JSONObject rootObject = super.getJSONObject((String)parm[0], (String)parm[1], (Integer)parm[2], (Integer)parm[3]);
			JSONObject JSONBody=new JSONObject();
			JSONArray JSONBoxArray=new JSONArray();
			JSONObject JSONbox = null;
			for (int i = 0; i < typeList.size(); i++) {
				JSONbox = new JSONObject();
				JSONbox.put("ProductId", "" + typeList.get(i).getProductId());
				JSONbox.put("ProductCode", "" + typeList.get(i).getProductCode());
				JSONbox.put("Name", "" + typeList.get(i).getName());
				JSONbox.put("FullName", "" + typeList.get(i).getFullName());
				JSONbox.put("TypeName", ""+ typeList.get(i).getTypeName());
				JSONbox.put("SalePrice", "" + typeList.get(i).getSalePrice());
				JSONbox.put("BagType", ""+ typeList.get(i).getBagType());
				JSONbox.put("PicUrl", ""+typeList.get(i).getPicJson());
				JSONbox.put("FactoryName", "" + typeList.get(i).getFactoryName());
				JSONbox.put("Spec", ""+ typeList.get(i).getSpec());
				JSONbox.put("Nutrition", ""+ typeList.get(i).getNutrition());
				JSONbox.put("DesOne", ""+ typeList.get(i).getDesOne());
				JSONbox.put("DesTwo", ""+ typeList.get(i).getDesTwo());
				JSONbox.put("DesThree", ""+ typeList.get(i).getDesThree());
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
