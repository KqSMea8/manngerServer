package com.lmxf.post.tradepkg.partner;

import net.sf.json.JSONObject;

import com.lmxf.post.tradepkg.common.RespHead;

public class SiteLanelsdifferResp extends RespHead {

	public String CreateJson(Object... parm) {
		try {
			JSONObject rootObject = super.getJSONObject((String) parm[0], (String) parm[1], (Integer) parm[2], (Integer) parm[3]);
			return rootObject.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

}