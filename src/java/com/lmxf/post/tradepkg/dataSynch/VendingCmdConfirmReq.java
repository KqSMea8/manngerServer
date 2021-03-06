package com.lmxf.post.tradepkg.dataSynch;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.putils.utils.DateUtil;
import org.apache.commons.putils.utils.StringUtils;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.JSONUtils;
import com.lmxf.post.entity.vending.VendingCmd;
import com.lmxf.post.entity.vending.VendingPerson;
import com.lmxf.post.tradepkg.common.ReqHead;

public class VendingCmdConfirmReq extends ReqHead {
	private final static Log log = LogFactory.getLog(VendingCmdConfirmReq.class);

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
				r.put("code", GConstent.Emp_Info_No_Found);
				return r;
			}
			vendingCmd.setState(JSONBody.getString("State"));
			if (StringUtils.isEmpty(vendingCmd.getState())) {
				r.put("code", GConstent.Request_State_No_Define);
				return r;
			}
			vendingCmd.setResult(JSONBody.getString("Result"));
			if (StringUtils.isEmpty(vendingCmd.getResult())) {
				r.put("code", GConstent.Request_Result_No_Define);
				return r;
			}
			vendingCmd.setStateTime(JSONBody.getString("OperTime"));
			if (StringUtils.isEmpty(vendingCmd.getStateTime())) {
				vendingCmd.setStateTime(DateUtil.getNow());
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
