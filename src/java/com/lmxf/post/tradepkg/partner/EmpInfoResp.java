package com.lmxf.post.tradepkg.partner;


import net.sf.json.JSONObject;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.entity.emp.Emp;
import com.lmxf.post.tradepkg.common.RespHead;

public class EmpInfoResp extends RespHead{

	public String CreateJson(Object... parm) {
		Emp emp = (Emp) parm[4];
		try {
			JSONObject rootObject = super.getJSONObject((String) parm[0], (String) parm[1], (Integer) parm[2], (Integer) parm[3]);
			JSONObject JSONBody = new JSONObject();
			JSONBody.put("LoginId", "" + emp.getLoginName());
			JSONBody.put("PassWord", "" + emp.getPassWord());
			JSONBody.put("UserType", ""+ emp.getEmpType());
			JSONBody.put("EncryptType", ""+ emp.getEncodeType());
			JSONBody.put("Salt", ""+ emp.getSalt());
			JSONBody.put("UserName", ""+ emp.getLoginName());
			JSONBody.put("PhoneNumber",""+ emp.getMobile());
			JSONObject JSONObject = (JSONObject) rootObject.get(GConstent.ZxmlRoot);
			JSONObject.put(GConstent.ZxmlBody, JSONBody);
			return rootObject.toString();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}
}
