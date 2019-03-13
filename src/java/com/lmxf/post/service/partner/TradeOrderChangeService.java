package com.lmxf.post.service.partner;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.entity.order.OrderChange;
import com.lmxf.post.repository.order.OrderChangeDao;
import com.lmxf.post.service.core.TradeProcess;
import com.lmxf.post.tradepkg.partner.OrderChangeReq;
import com.lmxf.post.tradepkg.partner.OrderChangeResp;

public class TradeOrderChangeService extends TradeProcess {
	private final static Log log = LogFactory.getLog(TradeOrderChangeService.class);
	private OrderChangeReq orderChangeReq;
	private OrderChangeResp orderChangeResp;
	private OrderChangeDao orderChangeDao;

	@SuppressWarnings("rawtypes")
	public String tradeProcess(String apply_xml) {
		OrderChange orderChangeP = null;
		List<OrderChange> orderChangeList = null;
		Map ret = null;
		try {
			ret = orderChangeReq.parseXml(apply_xml);
			if (ret.get("code") == null) {
				return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
			} else if (ret.get("code") != null && Integer.parseInt(ret.get("code").toString()) != 0) {
				log.error("orderChangeReq.parseXml is error!");
				return errorCodeDao.getErrorRespJson(Integer.parseInt(ret.get("code").toString()));
			}
		} catch (Exception e) {
			log.error("---parseXml  error-----", e);
			return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
		}
		orderChangeP = (OrderChange) ret.get("orderChange");
		OrderChange orderChange = new OrderChange();
		orderChange.setOrderId(orderChangeP.getOrderId());
		orderChangeList = this.orderChangeDao.findAll(orderChange);
		if (null == orderChangeList || orderChangeList.size() <= 0) {
			return errorCodeDao.getErrorRespJson(GConstent.OrderApplyChange_No_Exist);
		}
		return orderChangeResp.CreateJson(GConstent.SUCCESS_CODE, GConstent.SUCCESS_MSG, orderChangeList.size(), orderChangeList.size(), orderChangeList);
	}

	public OrderChangeReq getOrderChangeReq() {
		return orderChangeReq;
	}

	public void setOrderChangeReq(OrderChangeReq orderChangeReq) {
		this.orderChangeReq = orderChangeReq;
	}

	public OrderChangeResp getOrderChangeResp() {
		return orderChangeResp;
	}

	public void setOrderChangeResp(OrderChangeResp orderChangeResp) {
		this.orderChangeResp = orderChangeResp;
	}

	public OrderChangeDao getOrderChangeDao() {
		return orderChangeDao;
	}

	public void setOrderChangeDao(OrderChangeDao orderChangeDao) {
		this.orderChangeDao = orderChangeDao;
	}

}
