package com.lmxf.post.tradepkg.site;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.JSONUtils;
import com.lmxf.post.tradepkg.common.ReqHead;

public class VendingLaneReq extends ReqHead {
	private final static Log log = LogFactory.getLog(VendingLaneReq.class);
	
	@SuppressWarnings("unchecked")
	public Map parseXml(String report_json) {
		Map r = new HashMap();
		String siteId = null;
		String state = null;
		String operTime=null;
		try {
			// json解析协议
			JSONObject JSONBody = JSONUtils.getBody(report_json);
			siteId = JSONBody.getString("SiteId");
			state = JSONBody.getString("State");
			operTime = JSONBody.getString("OperTime");
			if (siteId == null || "".equals(siteId.trim())) {
				log.error("siteId is null!");
				r.put("code", GConstent.Request_StationNumber_No_Define);
				return r;
			}
			if (null == state) {
				log.error("state  is null!");
				log.error("siteName is null!");
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
			if (operTime == null || "".equals(operTime.trim())) {
				log.error("operTime is null!");
				r.put("code", GConstent.Request_StationSerialNumber_No_Define);
				return r;
			}
		}catch (Exception e) {
			log.error("parseXml is error!");
			r.put("code", GConstent.Program_App_Execp);
			return r;
		}
		r.put("code", 0);
		r.put("siteId", siteId);
		r.put("state", state);
		r.put("operTime", operTime);
		return r;
	} 
}

