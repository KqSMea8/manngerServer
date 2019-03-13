package com.lmxf.post.service.dataSynch;

import java.util.Map;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.GParameter;
import com.lmxf.post.entity.order.OrderApply;
import com.lmxf.post.repository.order.OrderApplyDao;
import com.lmxf.post.service.core.TradeProcess;
import com.lmxf.post.tradepkg.dataSynch.OrderIssuedConfirmReq;
import com.lmxf.post.tradepkg.dataSynch.OrderIssuedConfirmResp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.putils.utils.DateUtil;

public class TradeOrderIssuedConfirmService extends TradeProcess {
	private final static Log log = LogFactory.getLog(TradeOrderIssuedConfirmService.class);
	private OrderIssuedConfirmReq orderIssuedConfirmReq;
	private OrderIssuedConfirmResp orderIssuedConfirmResp;
	private OrderApplyDao orderApplyDao;
	
	@SuppressWarnings("rawtypes")
	public String tradeProcess(String apply_xml) {
		Map ret = null;
		OrderApply orderApply = null;
		try {
			ret = orderIssuedConfirmReq.parseXml(apply_xml);
			if (ret.get("code") == null) {
				return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
			} else if (ret.get("code") != null && Integer.parseInt(ret.get("code").toString()) != 0) {
				log.error("orderIssuedConfirmReq.parseXml is error!");
				return errorCodeDao.getErrorRespJson(Integer.parseInt(ret.get("code").toString()));
			}
		} catch (Exception e) {
			log.error("---parseXml  error-----", e);
			return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
		}
		
		orderApply = (OrderApply) ret.get("orderApply");
		OrderApply orderApplyR = orderApplyDao.findOneByOrderId(orderApply);
		
		if(null == orderApplyR){
			return errorCodeDao.getErrorRespJson(GConstent.Order_No_Exsit);
		}
		if(orderApplyR.getPushState().equals(GParameter.pushState_pushSuccess)){
			return errorCodeDao.getErrorRespJson(GConstent.Repeat_Confirm_Error);
		}
		orderApplyR.setPushState(GParameter.pushState_pushSuccess);
		orderApplyR.setpStateTime(DateUtil.getNow());
		int r = orderApplyDao.updateDownTime(orderApplyR);
		if(r < 0){
			return errorCodeDao.getErrorRespJson(GConstent.Confirm_Failed_Error);
		}
		return orderIssuedConfirmResp.CreateJson(GConstent.SUCCESS_CODE, GConstent.SUCCESS_MSG, 1, 1);
	}

	public void setOrderIssuedConfirmReq(OrderIssuedConfirmReq orderIssuedConfirmReq) {
		this.orderIssuedConfirmReq = orderIssuedConfirmReq;
	}

	public void setOrderIssuedConfirmResp(
			OrderIssuedConfirmResp orderIssuedConfirmResp) {
		this.orderIssuedConfirmResp = orderIssuedConfirmResp;
	}

	public void setOrderApplyDao(OrderApplyDao orderApplyDao) {
		this.orderApplyDao = orderApplyDao;
	}

	
}