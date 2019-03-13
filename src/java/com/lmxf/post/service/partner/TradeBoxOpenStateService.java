package com.lmxf.post.service.partner;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.GParameter;
import com.lmxf.post.entity.order.OrderApply;
import com.lmxf.post.entity.order.OrderBox;
import com.lmxf.post.repository.order.OrderApplyDao;
import com.lmxf.post.repository.order.OrderBoxDao;
import com.lmxf.post.service.core.TradeProcess;
import com.lmxf.post.tradepkg.partner.BoxOpenStateReq;
import com.lmxf.post.tradepkg.partner.BoxOpenStateResp;

public class TradeBoxOpenStateService extends TradeProcess {
	private final static Log log = LogFactory.getLog(TradeBoxOpenStateService.class);
	private BoxOpenStateReq boxOpenStateReq;
	private BoxOpenStateResp boxOpenStateResp;
	private OrderApplyDao orderApplyDao;
	private OrderBoxDao orderBoxDao;

	@SuppressWarnings("rawtypes")
	public String tradeProcess(String apply_xml) {
		OrderApply orderApplyP = null;
		Map ret = null;
		try {
			ret = boxOpenStateReq.parseXml(apply_xml);
			if (ret.get("code") == null) {
				return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
			} else if (ret.get("code") != null && Integer.parseInt(ret.get("code").toString()) != 0) {
				log.error("boxOpenStateReq.parseXml is error!");
				return errorCodeDao.getErrorRespJson(Integer.parseInt(ret.get("code").toString()));
			}
		} catch (Exception e) {
			log.error("---parseXml  error-----", e);
			return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
		}
		orderApplyP = (OrderApply) ret.get("orderApply");
		OrderApply orderApply = orderApplyDao.findBoxOpenState(orderApplyP);
		if(orderApply == null){
			return errorCodeDao.getErrorRespJson(GConstent.Order_No_Exsit);
		}
		OrderBox orderBoxP = new OrderBox();
		orderBoxP.setOrderId(orderApply.getOrderId());
		OrderBox orderBox = orderBoxDao.findBoxOpenState(orderBoxP);
		if(orderBox == null){
			return errorCodeDao.getErrorRespJson(GConstent.Order_Box_No_Exsit);
		}
		if(orderBox.getOutState().equals(GParameter.orderBoxOutState_in) || orderBox.getOutState().equals(GParameter.orderBoxOutState_outFailed)){
			orderBox.setOutState("2");//门已关闭
		}else{
			orderBox.setOutState("1");//已开门
		}
		return boxOpenStateResp.CreateJson(GConstent.SUCCESS_CODE, GConstent.SUCCESS_MSG, 1, 1, orderBox);
	}

	public void setBoxOpenStateReq(BoxOpenStateReq boxOpenStateReq) {
		this.boxOpenStateReq = boxOpenStateReq;
	}

	public void setBoxOpenStateResp(BoxOpenStateResp boxOpenStateResp) {
		this.boxOpenStateResp = boxOpenStateResp;
	}

	public void setOrderApplyDao(OrderApplyDao orderApplyDao) {
		this.orderApplyDao = orderApplyDao;
	}

	public void setOrderBoxDao(OrderBoxDao orderBoxDao) {
		this.orderBoxDao = orderBoxDao;
	}

}
