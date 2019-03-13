package com.lmxf.post.tradepkg.site;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.putils.utils.StringUtils;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.JSONUtils;
import com.lmxf.post.entity.vending.VendingState;
import com.lmxf.post.tradepkg.common.ReqHead;

public class VendingStateReq extends ReqHead {
	private final static Log log = LogFactory.getLog(VendingStateReq.class);

	@SuppressWarnings("unchecked")
	public Map parseXml(String report_json) {
		Map r = new HashMap();
		VendingState vendingState = new VendingState();
		try {
			// json解析协议
			JSONObject JSONBody = JSONUtils.getBody(report_json);
			vendingState.setSiteId(JSONBody.getString("SiteId"));
			vendingState.setSeqId(JSONBody.getString("SeqId"));
			vendingState.setvFirmware(JSONBody.getString("vFirmware"));
			vendingState.setvVCS(JSONBody.getString("vVCS"));
			vendingState.setSignalValue(JSONBody.getString("signal"));
			vendingState.setIccid(JSONBody.getString("Iccid"));
			vendingState.setResoution(JSONBody.getString("Resolution"));
			vendingState.setScreenType(JSONBody.getString("screenType"));
			vendingState.setvAndroid(JSONBody.getString("vAndroid"));
			vendingState.setvBaseband(JSONBody.getString("vBaseband"));
			vendingState.setLatitude(JSONBody.getString("Latitude"));
			vendingState.setLongitude(JSONBody.getString("Longitude"));
			vendingState.setIpAddress(JSONBody.getString("IpAddress"));
			if (StringUtils.isEmpty(vendingState.getSiteId())) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
			if (StringUtils.isEmpty(vendingState.getSeqId())) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
			if (StringUtils.isEmpty(vendingState.getvFirmware())) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
			if (StringUtils.isEmpty(vendingState.getvVCS())) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
			if (StringUtils.isEmpty(vendingState.getSignalValue())) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
			if (StringUtils.isEmpty(vendingState.getIccid())) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
			if (StringUtils.isEmpty(vendingState.getResoution())) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
			if (StringUtils.isEmpty(vendingState.getScreenType())) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
			if (StringUtils.isEmpty(vendingState.getvAndroid())) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
			if (StringUtils.isEmpty(vendingState.getvBaseband())) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
			if (StringUtils.isEmpty(vendingState.getLatitude())) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
			if (StringUtils.isEmpty(vendingState.getLongitude())) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
		} catch (Exception e) {
			e.printStackTrace();
			r.put("code", GConstent.Program_App_Execp);
			return r;
		}
		r.put("code", 0);
		r.put("vendingState", vendingState);
		return r;
	}
}
