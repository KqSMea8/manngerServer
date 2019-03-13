package com.lmxf.post.tradepkg.partner;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.JSONUtils;
import com.lmxf.post.entity.product.ProductClassify;
import com.lmxf.post.entity.product.ProductInfo;
import com.lmxf.post.entity.vending.Vending;
import com.lmxf.post.entity.vending.VendingStock;
import com.lmxf.post.tradepkg.common.ReqHead;

public class ProductClassifyReq extends ReqHead{

	@SuppressWarnings({"unchecked","rawtypes"})
	public Map parseXml(String report_json){
		Map r=new HashMap();
		Vending vending = new Vending();
		try {
			//json解析协议
			JSONObject JSONBody=JSONUtils.getHeader(report_json);
			r.put("tcode", JSONBody.getString("TCode"));
			r.put("bcode", JSONBody.getString("BCode"));
			r.put("istart", JSONBody.getString("IStart"));
			JSONBody=JSONUtils.getBody(report_json);
			vending.setSiteId("" + JSONBody.getString("SiteId"));
			if (vending.getSiteId() == null || "".equals(vending.getSiteId())) {
				r.put("code", GConstent.Request_StationNumber_No_Define);
				return r;
			}
		} catch (Exception e) {
			e.printStackTrace();
			r.put("code", GConstent.Program_App_Execp);
			return r;
		}
		r.put("code", 0);
		r.put("vending", vending);
		return r;
	}
}
