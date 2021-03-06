package com.lmxf.post.service.partner;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.putils.utils.DateUtil;
import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.GParameter;
import com.lmxf.post.core.utils.pay.OrderPayUtils;
import com.lmxf.post.entity.order.OrderApply;
import com.lmxf.post.entity.order.OrderChange;
import com.lmxf.post.repository.order.OrderApplyDao;
import com.lmxf.post.repository.order.OrderChangeDao;
import com.lmxf.post.repository.parameter.SequenceIdDao;
import com.lmxf.post.service.core.TradeProcess;
import com.lmxf.post.tradepkg.partner.OrderRefundReq;
import com.lmxf.post.tradepkg.partner.OrderRefundResp;

public class TradeOrderRefundService extends TradeProcess {
	private final static Log log = LogFactory.getLog(TradeOrderRefundService.class);
	private OrderRefundReq orderRefundReq;
	private OrderRefundResp orderRefundResp;
	private OrderApplyDao orderApplyDao;
	private SequenceIdDao sequenceIdDao;
	private OrderChangeDao orderChangeDao;

	@SuppressWarnings("rawtypes")
	public String tradeProcess(String apply_xml) {
		OrderApply orderApply = null;
		Map ret = null;
		try {
			ret = orderRefundReq.parseXml(apply_xml);
			if (ret.get("code") == null) {
				return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
			} else if (ret.get("code") != null && Integer.parseInt(ret.get("code").toString()) != 0) {
				log.error("orderRefundReq.parseXml is error!");
				return errorCodeDao.getErrorRespJson(Integer.parseInt(ret.get("code").toString()));
			}
		} catch (Exception e) {
			log.error("---parseXml  error-----", e);
			return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
		}
		orderApply = (OrderApply) ret.get("orderApply");
		OrderApply orderApplyR = this.orderApplyDao.findOneByOrderId(orderApply);
		if (null == orderApplyR) {
			return errorCodeDao.getErrorRespJson(GConstent.Order_No_Exsit);
		}
		if (orderApplyR.getPayState().equals(GParameter.payState_success) && GParameter.returnType_none.equals(orderApplyR.getReturnType())) {// 已支付的订单满足以下条件可以取消订单退款
			Map<String, String> payMap = OrderPayUtils.refundOrder(orderApplyR);
			// 更新订单状态信息
			if ("0000".equals(payMap.get("code"))) {
				orderApplyR.setReturnMoney(orderApplyR.getPayPrice());
				orderApplyR.setrTradeNo(payMap.get("OutTradeNo"));
				orderApplyR.setReturnType(GParameter.returnType_success);
				orderApplyR.setReturnMoney(orderApplyR.getPayPrice());
				this.orderApplyDao.update(orderApplyR);
				// 插入订单状态变化信息
				OrderChange orderChange = new OrderChange();
				orderChange.setLogid(DateUtil.getLogid());
				orderChange.setChangeId(orderApplyR.getCorpId() + "-" + this.sequenceIdDao.findNextVal(orderApplyR.getCorpId(), "order_change_id", 7));
				orderChange.setCorpId(orderApplyR.getCorpId());
				orderChange.setOrderId(orderApplyR.getOrderId());
				orderChange.setOperId(orderApply.getOperId());
				orderChange.setOperName(orderApply.getOperName());
				orderChange.setSiteId(orderApplyR.getSiteId());
				orderChange.setSiteName(orderApplyR.getSiteName());
				orderChange.setOperAction(GParameter.orderChange_refund);
				orderChange.setOperTime(DateUtil.getNow());
				orderChange.setOperCont("售货机:" + orderApplyR.getSiteName() + " 订单编号:" + orderApplyR.getOrderId() + " 退款成功!");
				orderChange.setCreateTime(DateUtil.getNow());
				orderChange.setPocState(GParameter.OrderChangeProc_Pushed);
				orderChange.setPocTimes(0);
				orderChange.setPocResult("push success");
				orderChange.setProState(GParameter.OrderChangeProState_waitFinsh);
				this.orderChangeDao.insert(orderChange);
			} else {
				return errorCodeDao.getErrorRespJson(GConstent.Refund_Pay_Server_Error);
			}
		} else {
			return errorCodeDao.getErrorRespJson(GConstent.Refund_Pay_Error);
		}
		return orderRefundResp.CreateJson(GConstent.SUCCESS_CODE, GConstent.SUCCESS_MSG, 1, 1);
	}

	public void setOrderRefundReq(OrderRefundReq orderRefundReq) {
		this.orderRefundReq = orderRefundReq;
	}

	public void setOrderRefundResp(OrderRefundResp orderRefundResp) {
		this.orderRefundResp = orderRefundResp;
	}

	public void setOrderApplyDao(OrderApplyDao orderApplyDao) {
		this.orderApplyDao = orderApplyDao;
	}

	public void setSequenceIdDao(SequenceIdDao sequenceIdDao) {
		this.sequenceIdDao = sequenceIdDao;
	}

	public void setOrderChangeDao(OrderChangeDao orderChangeDao) {
		this.orderChangeDao = orderChangeDao;
	}
}
