package com.lmxf.post.tradepkg.partner;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.putils.utils.StringUtils;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.JSONUtils;
import com.lmxf.post.entity.vending.VendingCmd;
import com.lmxf.post.tradepkg.common.ReqHead;

public class VendingCmdReq extends ReqHead {
	private final static Log log = LogFactory.getLog(VendingCmdReq.class);

	@SuppressWarnings("unchecked")
	public Map parseXml(String report_json) {
		Map r = new HashMap();
		VendingCmd vendingCmd = new VendingCmd();
		try {
			// json解析协议
			JSONObject JSONBody = JSONUtils.getBody(report_json);
			// 解析body
			vendingCmd.setCmdId(JSONBody.getString("CmdId"));
			if (StringUtils.isEmpty(vendingCmd.getCmdId())) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
			vendingCmd.setCmdCode(JSONBody.getString("CmdCode"));
			if (StringUtils.isEmpty(vendingCmd.getCmdCode())) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
			vendingCmd.setCmdType(JSONBody.getString("CmdType"));
			if (StringUtils.isEmpty(vendingCmd.getCmdType())) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
			vendingCmd.setCmd(JSONBody.getString("Cmd"));
			if (StringUtils.isEmpty(vendingCmd.getCmd())) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
			vendingCmd.setCont(JSONBody.getString("Cont"));
			if (StringUtils.isEmpty(vendingCmd.getCont())) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
			vendingCmd.setCreateTime(JSONBody.getString("CreateTime"));
			if (StringUtils.isEmpty(vendingCmd.getCreateTime())) {
				r.put("code", GConstent.Request_parameter_No_Define);
				return r;
			}
			r.put("vendingCmd", vendingCmd);
			r.put("code", 0);
			return r;
		} catch (Exception e) {
			log.error("parseXml is error!", e);
			r.put("code", GConstent.Compile_Json_Error);
			return r;
		}
	}
}
