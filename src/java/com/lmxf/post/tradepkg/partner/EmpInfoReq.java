package com.lmxf.post.tradepkg.partner;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;


import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.JSONUtils;
import com.lmxf.post.entity.emp.Emp;
import com.lmxf.post.tradepkg.common.ReqHead;

public class EmpInfoReq extends ReqHead{

	@SuppressWarnings({"unchecked","rawtypes"})
	public Map parseXml(String report_json) {
		Map r = new HashMap();
		Emp emp = new Emp();
		try {
			// json瑙ｆ瀽鍗忚
			JSONObject JSONBody = JSONUtils.getHeader(report_json);
			r.put("tcode", JSONBody.getString("TCode"));
			r.put("bcode", JSONBody.getString("BCode"));
			r.put("istart", JSONBody.getString("IStart"));
			JSONBody = JSONUtils.getBody(report_json);
			if (JSONBody.containsKey("LoginId")){
				emp.setLoginId("" + JSONBody.getString("LoginId"));
			}
			if (JSONBody.containsKey("AccessCode")){
				emp.setAccessCode("" + JSONBody.getString("AccessCode"));
			}
			if ((emp.getLoginId() == null || "".equals(emp.getLoginId())) ) {
				r.put("code", GConstent.Request_LoginId_No_Define);
				return r;
			}
		} catch (Exception e) {
			e.printStackTrace();
			r.put("code", GConstent.Program_App_Execp);
			return r;
		}
		r.put("code", 0);
		r.put("emp", emp);
		return r;
	}

}
