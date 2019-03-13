package com.lmxf.post.tradepkg.partner;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.entity.product.ProductClassify;
import com.lmxf.post.tradepkg.common.RespHead;

public class ProductClassifyResp extends RespHead{

	public String CreateJson(Object... parm) {
		List<ProductClassify> typeList = (List<ProductClassify>) parm[4];
		try {
			JSONObject rootObject = super.getJSONObject((String) parm[0], (String) parm[1], (Integer) parm[2], (Integer) parm[3]);
			JSONObject JSONBody = new JSONObject();
			JSONArray JSONBoxArray = new JSONArray();
			JSONObject JSONbox = null;
			for (int i = 0; i < typeList.size(); i++) {
				JSONbox = new JSONObject();
				JSONbox.put("Level", "" + typeList.get(i).getLevel());
				JSONbox.put("ParentId", "" + typeList.get(i).getParentId());
				JSONbox.put("SortBy", "" + typeList.get(i).getSortBy());
				JSONbox.put("ClassifyId", ""+ typeList.get(i).getClassifyId());
				JSONbox.put("ClassifyName", "" + typeList.get(i).getClassifyName());
				JSONbox.put("ClassifyDesc","" + typeList.get(i).getClassifyDesc());
				JSONbox.put("PicUrl", "" + typeList.get(i).getPicJson());
				JSONBoxArray.add(JSONbox);
			}
			JSONBody.put("ClassifyInfo", JSONBoxArray);
			JSONObject JSONObject = (JSONObject) rootObject.get(GConstent.ZxmlRoot);
			JSONObject.put(GConstent.ZxmlBody, JSONBody);
			return rootObject.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

}
