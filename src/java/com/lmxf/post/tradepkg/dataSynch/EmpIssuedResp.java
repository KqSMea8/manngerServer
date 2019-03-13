package com.lmxf.post.tradepkg.dataSynch;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import net.sf.json.JSONObject;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.entity.vending.VendingPerson;
import com.lmxf.post.tradepkg.common.RespHead;

public class EmpIssuedResp extends RespHead {

	public String CreateJson(Object... parm) {
		VendingPerson vendingPerson = (VendingPerson) parm[4];
		try {
			JSONObject rootObject = super.getJSONObject((String) parm[0], (String) parm[1], (Integer) parm[2], (Integer) parm[3]);
			JSONObject JSONBody = new JSONObject();
			JSONBody.put("SiteId", vendingPerson.getSiteId());
			JSONBody.put("LoginId", vendingPerson.getLoginId());
			JSONBody.put("AccessCode", vendingPerson.getUser().getAccess_code());
			JSONBody.put("PassWord", vendingPerson.getUser().getPassword());
			JSONBody.put("EncryptType", "-");
			JSONBody.put("Salt", vendingPerson.getUser().getSalt());
			JSONBody.put("UserType", vendingPerson.getUser().getRole_key());
			JSONBody.put("UserName", vendingPerson.getEmail());
			JSONBody.put("PhoneNumber", vendingPerson.getMobile());
			SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar cal=Calendar.getInstance();
			cal.setTime(sf.parse(vendingPerson.getCreateTime()));
			cal.set(Calendar.YEAR, cal.get(Calendar.YEAR)+1);
			JSONBody.put("UnableTime", "" +sf.format(cal.getTime()));
			JSONBody.put("State", vendingPerson.getCurState());
			JSONObject JSONObject = (JSONObject) rootObject.get(GConstent.ZxmlRoot);
			JSONObject.put(GConstent.ZxmlBody, JSONBody);
			return rootObject.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

}
