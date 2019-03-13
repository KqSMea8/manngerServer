package com.lmxf.post.tradepkg.dataSynch;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.putils.utils.StringUtils;

import net.sf.json.JSONObject;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.JSONUtils;
import com.lmxf.post.entity.vending.VendingPerson;
import com.lmxf.post.tradepkg.common.ReqHead;

public class EmpIssuedConfirmReq extends ReqHead {
	private final static Log log = LogFactory.getLog(EmpIssuedConfirmReq.class);

	@SuppressWarnings("unchecked")
	public Map parseXml(String report_json) {
		Map r = new HashMap();
		VendingPerson vendingPerson = new VendingPerson();
		try {
			// json解析协议
			JSONObject JSONBody = JSONUtils.getBody(report_json);
			// 解析body
			vendingPerson.setSiteId(JSONBody.getString("SiteId"));
			if (StringUtils.isEmpty(vendingPerson.getSiteId())) {
				r.put("code", GConstent.Request_StationNumber_No_Define);
				return r;
			}
			vendingPerson.setLoginId(JSONBody.getString("LoginId"));
			if (StringUtils.isEmpty(vendingPerson.getLoginId())) {
				r.put("code", GConstent.Request_LoginId_No_Define);
				return r;
			}
			
			r.put("vendingPerson", vendingPerson);
			r.put("code", 0);
			return r;
		} catch (Exception e) {
			log.error("parseXml is error!", e);
			r.put("code", GConstent.Compile_Json_Error);
			return r;
		}
	}
}
