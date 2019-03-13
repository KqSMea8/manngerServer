package com.lmxf.post.tradepkg.partner;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.JSONUtils;
import com.lmxf.post.entity.vending.VendingPerson;
import com.lmxf.post.tradepkg.common.ReqHead;

public class VendingPersonReq extends ReqHead{

	@SuppressWarnings({"unchecked","rawtypes"})
	public Map parseXml(String report_json) {
		Map r = new HashMap();
		VendingPerson vendingPerson = new VendingPerson();
		try {
			// json解析协议
			JSONObject JSONBody = JSONUtils.getHeader(report_json);
			r.put("tcode", JSONBody.getString("TCode"));
			r.put("bcode", JSONBody.getString("BCode"));
			r.put("istart", JSONBody.getString("IStart"));
			JSONBody = JSONUtils.getBody(report_json);
			vendingPerson.setLoginId("" + JSONBody.getString("LoginId"));
			vendingPerson.setSiteId("" + JSONBody.getString("SiteId"));
			vendingPerson.setCurState("" + JSONBody.getString("State"));
			if ((vendingPerson.getLoginId() == null || "".equals(vendingPerson.getLoginId())) ) {
				r.put("code", GConstent.Request_LoginId_No_Define);
				return r;
			}
			if ((vendingPerson.getSiteId() == null || "".equals(vendingPerson.getSiteId())) ) {
				r.put("code", GConstent.Request_SiteId_No_Define);
				return r;
			}
			if ((vendingPerson.getCurState() == null || "".equals(vendingPerson.getCurState())) ) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
		} catch (Exception e) {
			e.printStackTrace();
			r.put("code", GConstent.Program_App_Execp);
			return r;
		}
		r.put("code", 0);
		r.put("vendingPerson", vendingPerson);
		return r;
	}

}
