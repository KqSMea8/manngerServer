package com.lmxf.post.tradepkg.common;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lmxf.post.core.utils.GConstent;

public class CommonResp extends RespHead {
	private final static Log log = LogFactory.getLog(CommonResp.class);

	@Override
	public String CreateJson(Object... parm) {
		JSONObject rootObject = super.getJSONObject((String) parm[0], (String) parm[1], (Integer) parm[2], (Integer) parm[3]);
		JSONObject JSONBody = new JSONObject();
		JSONObject JSONObject = (JSONObject) rootObject.get(GConstent.ZxmlRoot);
		JSONObject.put(GConstent.ZxmlBody, JSONBody);
		return rootObject.toString();
	}

}
 