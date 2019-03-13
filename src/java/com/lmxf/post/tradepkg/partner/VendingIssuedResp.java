package com.lmxf.post.tradepkg.partner;

import net.sf.json.JSONObject;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.entity.vending.VendingCmd;
import com.lmxf.post.tradepkg.common.RespHead;

public class VendingIssuedResp extends RespHead {
	public String CreateJson(Object... parm) {
		VendingCmd vendingCmd = (VendingCmd) parm[4];
		try {
			JSONObject rootObject = super.getJSONObject((String) parm[0], (String) parm[1], (Integer) parm[2], (Integer) parm[3]);
			JSONObject JSONBody = new JSONObject();
			JSONBody.put("SiteId", vendingCmd.getSiteId());
			JSONBody.put("CmdId", vendingCmd.getCmdId());
			JSONBody.put("CmdCode", vendingCmd.getCmdCode());
			JSONBody.put("CmdType", vendingCmd.getCmdType());
			JSONBody.put("Cmd", vendingCmd.getCmd());
			JSONBody.put("Cont", vendingCmd.getCont());
			JSONBody.put("CreateTime", vendingCmd.getCreateTime());
			JSONObject JSONObject = (JSONObject) rootObject.get(GConstent.ZxmlRoot);
			JSONObject.put(GConstent.ZxmlBody, JSONBody);
			return rootObject.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}
}
