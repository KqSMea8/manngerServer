package com.lmxf.post.tradepkg.common;
public class ErrorResp extends RespHead {
	private String xml;

	@Override
	public String CreateJson(Object... parm) {
		return super.getJSONObject((String) parm[0], (String) parm[1], (Integer) parm[2], (Integer) parm[3]).toString();
	}

}
