package com.lmxf.post.tradepkg.partner;


import net.sf.json.JSONObject;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.entity.emp.Emp;
import com.lmxf.post.entity.emp.User;
import com.lmxf.post.tradepkg.common.RespHead;

public class UserInfoResp extends RespHead{

	public String CreateJson(Object... parm) {
		User emp = (User) parm[4];
		try {
			JSONObject rootObject = super.getJSONObject((String) parm[0], (String) parm[1], (Integer) parm[2], (Integer) parm[3]);
			JSONObject JSONBody = new JSONObject();
			JSONBody.put("LoginId", "" + emp.getLogin_name());
			JSONBody.put("PassWord", "" + emp.getPassword());
			JSONBody.put("UserType", ""+ emp.getRole_key());
			JSONBody.put("EncryptType", "-");
			JSONBody.put("Salt", ""+ emp.getSalt());
			JSONBody.put("UserName", ""+ emp.getUser_name());
			JSONBody.put("PhoneNumber",""+ emp.getPhonenumber());
			JSONObject JSONObject = (JSONObject) rootObject.get(GConstent.ZxmlRoot);
			JSONObject.put(GConstent.ZxmlBody, JSONBody);
			return rootObject.toString();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}
}
