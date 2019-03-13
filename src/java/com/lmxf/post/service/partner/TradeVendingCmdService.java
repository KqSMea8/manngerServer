package com.lmxf.post.service.partner;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.putils.utils.DateUtil;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.GParameter;
import com.lmxf.post.core.utils.MsgPushUtils;
import com.lmxf.post.entity.vending.VendingCmd;
import com.lmxf.post.repository.vending.VendingCmdDao;
import com.lmxf.post.service.core.TradeProcess;
import com.lmxf.post.tradepkg.partner.VendingCmdReq;
import com.lmxf.post.tradepkg.partner.VendingCmdResp;
import com.lmxf.post.tradepkg.partner.VendingIssuedResp;

public class TradeVendingCmdService extends TradeProcess {
	private final static Log log = LogFactory.getLog(TradeVendingCmdService.class);
	private VendingCmdReq vendingCmdReq;
	private VendingCmdResp vendingCmdResp;
	private VendingIssuedResp vendingIssuedResp;
	private VendingCmdDao vendingCmdDao;
	
	@SuppressWarnings({ "rawtypes" })
	public String tradeProcess(String apply_xml) {
		Map ret = null;
		VendingCmd vendingCmd = null;
		try {
			ret = vendingCmdReq.parseXml(apply_xml);
			if (ret.get("code") == null) {
				return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
			} else if (ret.get("code") != null && Integer.parseInt(ret.get("code").toString()) != 0) {
				log.error("applyReservePersonFewReq.parseXml is error!");
				return errorCodeDao.getErrorRespJson(Integer.parseInt(ret.get("code").toString()));
			}
		} catch (Exception e) {
			log.error("---parseXml  error-----", e);
			return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
		}
		
		vendingCmd = (VendingCmd) ret.get("vendingCmd");
		VendingCmd vendingCmdR = vendingCmdDao.findOne(vendingCmd);
		if(null != vendingCmdR){
			return errorCodeDao.getErrorRespJson(GConstent.Vending_Cmd_Exsit);
		}
		vendingCmd.setLogid(DateUtil.getLogid());
		vendingCmd.setCmdLazy(vendingCmd.getCmdLazy());
		vendingCmd.setState("");
		vendingCmd.setStateTime(vendingCmd.getStateTime());
		vendingCmd.setResult(vendingCmd.getResult());
		vendingCmd.setCorpId(vendingCmd.getCorpId());
		vendingCmd.setCreateTime(vendingCmd.getCreateTime());
		this.vendingCmdDao.insert(vendingCmd);
		//人员推送
		String content = vendingIssuedResp.CreateJson(GConstent.SUCCESS_CODE, GConstent.SUCCESS_MSG, 1, 1, vendingCmd);
		String deviceId = vendingCmd.getSiteId();
		String msgId = vendingCmd.getLogid();
		int result = MsgPushUtils.push(content, deviceId, msgId);
		if(result == 0){
			vendingCmd.setState(GParameter.state_executing);
			vendingCmd.setStateTime(DateUtil.getNow());
			this.vendingCmdDao.update(vendingCmd);
		}
		return vendingCmdResp.CreateJson(GConstent.SUCCESS_CODE, GConstent.SUCCESS_MSG, 1, 1);
	}


	public void setVendingCmdReq(VendingCmdReq vendingCmdReq) {
		this.vendingCmdReq = vendingCmdReq;
	}


	public void setVendingCmdResp(VendingCmdResp vendingCmdResp) {
		this.vendingCmdResp = vendingCmdResp;
	}


	public void setVendingCmdDao(VendingCmdDao vendingCmdDao) {
		this.vendingCmdDao = vendingCmdDao;
	}


	public void setVendingIssuedResp(VendingIssuedResp vendingIssuedResp) {
		this.vendingIssuedResp = vendingIssuedResp;
	}
	
}
