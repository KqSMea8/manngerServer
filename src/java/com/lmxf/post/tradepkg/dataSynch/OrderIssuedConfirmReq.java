package com.lmxf.post.tradepkg.dataSynch;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.putils.utils.StringUtils;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.JSONUtils;
import com.lmxf.post.entity.order.OrderApply;
import com.lmxf.post.tradepkg.common.ReqHead;

public class OrderIssuedConfirmReq extends ReqHead {
	private final static Log log = LogFactory.getLog(OrderIssuedConfirmReq.class);

	@SuppressWarnings("unchecked")
	public Map parseXml(String report_json) {
		Map r = new HashMap();
		OrderApply orderApply = new OrderApply();
		try {
			// json解析协议
			JSONObject JSONBody = JSONUtils.getBody(report_json);
			// 解析body
			orderApply.setOrderId(JSONBody.getString("OrderId"));
			if (StringUtils.isEmpty(orderApply.getOrderId())) {
				r.put("code", GConstent.Request_OrderId_No_Define);
				return r;
			}
			r.put("orderApply", orderApply);
			r.put("code", 0);
			return r;
		} catch (Exception e) {
			log.error("parseXml is error!", e);
			r.put("code", GConstent.Compile_Json_Error);
			return r;
		}
	}
}