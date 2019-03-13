package com.lmxf.post.tradepkg.init;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.entity.emp.User;
import com.lmxf.post.entity.vending.VendingPerson;
import com.lmxf.post.tradepkg.common.RespHead;

public class EmpAllIssuedResp extends RespHead {

	public String CreateJson(Object... parm) {
		List<User> typeList = (List<User>) parm[4];
		try {
			JSONObject rootObject = super.getJSONObject((String) parm[0], (String) parm[1], (Integer) parm[2], (Integer) parm[3]);
			JSONObject JSONBody = new JSONObject();
			JSONArray JSONBoxArray = new JSONArray();
			JSONObject JSONbox = null;
			for (int i = 0; i < typeList.size(); i++) {
				JSONbox = new JSONObject();
				JSONbox.put("AccessCode", "" + typeList.get(i).getAccess_code());
				JSONbox.put("LoginId", "" + typeList.get(i).getLogin_name());
				JSONbox.put("PassWord", "" + typeList.get(i).getPassword());
				JSONbox.put("EncryptType",  "-" );
				JSONbox.put("Salt", "" + typeList.get(i).getSalt());
				JSONbox.put("UserType", "" + typeList.get(i).getRole_key());
				JSONbox.put("UserName", "" + typeList.get(i).getUser_name());
				JSONbox.put("PhoneNumber", "" + typeList.get(i).getPhonenumber());
				SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Calendar cal=Calendar.getInstance();
				cal.setTime(sf.parse(typeList.get(i).getCreatetime()));
				cal.set(Calendar.YEAR, cal.get(Calendar.YEAR)+1);
				JSONbox.put("UnableTime", "" +sf.format(cal.getTime()));
				JSONBoxArray.add(JSONbox);
			}
			JSONBody.put("LoginInfo", JSONBoxArray);
			JSONObject JSONObject = (JSONObject) rootObject.get(GConstent.ZxmlRoot);
			JSONObject.put(GConstent.ZxmlBody, JSONBody);
			return rootObject.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

}

