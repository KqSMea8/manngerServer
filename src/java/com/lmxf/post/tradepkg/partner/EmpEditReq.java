package com.lmxf.post.tradepkg.partner;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.JSONUtils;
import com.lmxf.post.entity.emp.Emp;
import com.lmxf.post.tradepkg.common.ReqHead;

public class EmpEditReq extends ReqHead{

	@SuppressWarnings("unchecked")
	public Map parseXml(String report_json) {
		Map r = new HashMap();
		Emp emp = new Emp();
		try {
			JSONObject JSONBody = JSONUtils.getHeader(report_json);
			r.put("tcode", JSONBody.getString("TCode"));
			r.put("bcode", JSONBody.getString("BCode"));
			r.put("istart", JSONBody.getString("IStart"));
			
			JSONBody = JSONUtils.getBody(report_json);
			emp.setLoginId(JSONBody.getString("LoginId"));
			emp.setMobile(JSONBody.getString("PhoneNumber"));
			emp.setLoginName(JSONBody.getString("UserName"));
			emp.setPassWord(JSONBody.getString("PassWord"));
			emp.setEncodeType(JSONBody.getString("EncryptType"));
			emp.setSalt(JSONBody.getString("Salt"));
			if (emp.getLoginId() == null || "".equals(emp.getLoginId())) {
				r.put("code", GConstent.Request_LoginId_No_Define);
				return r;
			}
			
			if (emp.getLoginName() == null || "".equals(emp.getLoginName())) {
				r.put("code", GConstent.Request_LoginName_No_Define);
				return r;
			}
			if (emp.getPassWord() == null || "".equals(emp.getPassWord())) {
				r.put("code", GConstent.Request_PassWord_No_Define);
				return r;
			}
			
			if (emp.getEncodeType() == null || "".equals(emp.getEncodeType())) {
				r.put("code", GConstent.Request_EncryptType_No_Define);
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