package com.lmxf.post.tradepkg.site;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.putils.utils.DateUtil;
import org.apache.commons.putils.utils.StringUtils;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.JSONUtils;
import com.lmxf.post.entity.vending.VendingEvent;
import com.lmxf.post.tradepkg.common.ReqHead;

public class VendingEventReq extends ReqHead {
	private final static Log log = LogFactory.getLog(VendingEventReq.class);

	@SuppressWarnings("unchecked")
	public Map parseXml(String report_json) {
		Map r = new HashMap();
		VendingEvent vendingEvent = new VendingEvent();
		try {
			// json解析协议
			JSONObject JSONBody = JSONUtils.getBody(report_json);
			vendingEvent.setSiteId(JSONBody.getString("SiteId"));
			vendingEvent.setEventType(JSONBody.getString("EventType"));
			vendingEvent.setEventCont(JSONBody.getString("EventCont"));
			vendingEvent.setEvnentState(JSONBody.getString("EventState"));
			vendingEvent.setEventTime(JSONBody.getString("EventTime"));
			vendingEvent.setEventTId(JSONBody.getString("EventId"));
			vendingEvent.setCreateTime(DateUtil.getNow());
			if (StringUtils.isEmpty(vendingEvent.getSiteId())) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
			if (StringUtils.isEmpty(vendingEvent.getEventType())) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
			if (StringUtils.isEmpty(vendingEvent.getEvnentState())) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
			if (StringUtils.isEmpty(vendingEvent.getEventTime())) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
			if (StringUtils.isEmpty(vendingEvent.getEventTId())) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
		} catch (Exception e) {
			e.printStackTrace();
			r.put("code", GConstent.Program_App_Execp);
			return r;
		}
		r.put("code", 0);
		r.put("vendingEvent", vendingEvent);
		return r;
	}
}
