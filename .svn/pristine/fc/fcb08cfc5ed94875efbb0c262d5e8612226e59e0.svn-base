package com.lmxf.post.service.partner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.putils.utils.DateUtil;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.GParameter;
import com.lmxf.post.entity.order.OrderApply;
import com.lmxf.post.entity.order.OrderBox;
import com.lmxf.post.entity.order.OrderChange;
import com.lmxf.post.entity.order.OrderProduct;
import com.lmxf.post.entity.vending.VendingStock;
import com.lmxf.post.repository.order.OrderApplyDao;
import com.lmxf.post.repository.order.OrderBoxDao;
import com.lmxf.post.repository.order.OrderChangeDao;
import com.lmxf.post.repository.order.OrderProductDao;
import com.lmxf.post.repository.parameter.SequenceIdDao;
import com.lmxf.post.repository.vending.VendingStockDao;
import com.lmxf.post.service.core.TradeProcess;
import com.lmxf.post.tradepkg.partner.OrderCancelReq;
import com.lmxf.post.tradepkg.partner.OrderCancelResp;

public class TradeOrderCancelService extends TradeProcess {
	private final static Log log = LogFactory.getLog(TradeOrderCancelService.class);
	private OrderCancelReq orderCancelReq;
	private OrderCancelResp orderCancelResp;
	private OrderApplyDao orderApplyDao;
	private SequenceIdDao sequenceIdDao;
	private OrderChangeDao orderChangeDao;
	private OrderProductDao orderProductDao;
	private VendingStockDao vendingStockDao;
	private OrderBoxDao orderBoxDao;

	@SuppressWarnings("rawtypes")
	public String tradeProcess(String apply_xml) {
		OrderApply orderApply = null;
		Map ret = null;
		try {
			ret = orderCancelReq.parseXml(apply_xml);
			if (ret.get("code") == null) {
				return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
			} else if (ret.get("code") != null && Integer.parseInt(ret.get("code").toString()) != 0) {
				log.error("orderCancelReq.parseXml is error!");
				return errorCodeDao.getErrorRespJson(Integer.parseInt(ret.get("code").toString()));
			}
		} catch (Exception e) {
			log.error("---parseXml  error-----", e);
			return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
		}
		orderApply = (OrderApply) ret.get("orderApply");
		OrderApply orderApplyR = this.orderApplyDao.findOneByOrderId(orderApply);
		if(null == orderApplyR){
			return errorCodeDao.getErrorRespJson(GConstent.Order_No_Exsit);
		}
		if(orderApplyR.getCurState().equals(GParameter.orderState_cancel)){
			return errorCodeDao.getErrorRespJson(GConstent.OrderState_No_Change);
		}
		String pay_state = orderApplyR.getPayState();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//出柜状态有未出柜或者出柜失败的能取消
		OrderBox orderBoxP=new OrderBox();
		orderBoxP.setOrderId(orderApplyR.getOrderId());
		List<OrderBox> orderBoxList=orderBoxDao.findAll(orderBoxP);
		try {
			if(pay_state.equals(GParameter.payState_success)){//已支付的订单满足以下条件可以取消订单退款
				//订单生成时间在30分钟之前的才能取消
				Date dateCreate=df.parse(orderApplyR.getCreateTime());
				Calendar nowCalendar=Calendar.getInstance();
				nowCalendar.set(Calendar.MINUTE, nowCalendar.get(Calendar.MINUTE)-30);
				if(dateCreate.getTime()>nowCalendar.getTime().getTime()){
					return errorCodeDao.getErrorRespJson(GConstent.OrderCancel_Time_Error);
				}
				//判断订单是否有未出柜的货,如果有为出柜则可以取消
				boolean isOut=true;
				for(OrderBox orderBox:orderBoxList){
					if(GParameter.orderBoxOutState_in.equals(orderBox.getOutState())){
						isOut=false;
					}
				}
				if(isOut){
					return errorCodeDao.getErrorRespJson(GConstent.OrderCancel_Time_Error);
				}
			}
		} catch (ParseException e) {
			log.error("时间转换出错");
		}
		//更新订单状态信息
		orderApplyR.setCurState(GParameter.orderState_cancel);
		orderApplyR.setaStateTime(DateUtil.getNow());
		orderApplyR.setOrderType(GParameter.orderType_close);
		orderApplyR.setPassWord("");
		this.orderApplyDao.update(orderApplyR);
		//插入订单状态变化信息
		OrderChange orderChange = new OrderChange();
		orderChange.setLogid(DateUtil.getLogid());
		orderChange.setChangeId(orderApplyR.getCorpId() + "-" + this.sequenceIdDao.findNextVal(orderApplyR.getCorpId(), "order_change_id", 7));
		orderChange.setCorpId(orderApplyR.getCorpId());
		orderChange.setOrderId(orderApplyR.getOrderId());
		orderChange.setOperId(orderApplyR.getLoginId());
		orderChange.setOperName(orderApplyR.getLoginName());
		orderChange.setSiteId(orderApplyR.getSiteId());
		orderChange.setSiteName(orderApplyR.getSiteName());
		orderChange.setOperAction(GParameter.orderChange_customerCancel);
		orderChange.setOperTime(DateUtil.getNow());
		orderChange.setOperCont("售货机:" + orderApplyR.getSiteName() + " 订单编号:" + orderApplyR.getOrderId() + " 客户取消成功!");
		orderChange.setCreateTime(DateUtil.getNow());
		orderChange.setPocState(GParameter.OrderChangeProc_waitPush);
		orderChange.setPocTimes(0);
		orderChange.setPocResult("wait push");
		orderChange.setProState(GParameter.OrderChangeProState_waitFinsh);
		this.orderChangeDao.insert(orderChange);
		
		//插入订单状态变化信息
		orderChange.setLogid(DateUtil.getLogid());
		orderChange.setChangeId(orderApplyR.getCorpId() + "-" + this.sequenceIdDao.findNextVal(orderApplyR.getCorpId(), "order_change_id", 7));
		orderChange.setOperAction(GParameter.orderChange_end);
		orderChange.setOperTime(DateUtil.getNow());
		orderChange.setOperCont("售货机:" + orderApplyR.getSiteName() + " 订单编号:" + orderApplyR.getOrderId() + " 已结单!");
		orderChange.setCreateTime(DateUtil.getNow());
		orderChange.setPocState(GParameter.OrderChangeProc_waitPush);
		orderChange.setPocTimes(0);
		orderChange.setPocResult("wait push");
		orderChange.setProState(GParameter.OrderChangeProState_waitFinsh);
		this.orderChangeDao.insert(orderChange);
		//释放库存
		Map<String,Integer> stockMap=new HashMap();
		for(OrderBox orderBox:orderBoxList){
			if(GParameter.orderBoxOutState_in.equals(orderBox.getOutState())){
				int num=stockMap.get(orderBox.getProductId())==null?0:stockMap.get(orderBox.getProductId());
				stockMap.put(orderBox.getProductId(),num+1);
			}
		}
		for (String productId : stockMap.keySet()) {
			VendingStock vendingStock = new VendingStock();
			vendingStock.setSiteId(orderApplyR.getSiteId());
			vendingStock.setProductId(productId);
			VendingStock vendingStockR = vendingStockDao.findOne(vendingStock);
			if(null != vendingStockR){
				vendingStockR.setNum(vendingStockR.getNum() + stockMap.get(productId));
				vendingStockDao.update(vendingStockR);
			}
		}
		return orderCancelResp.CreateJson(GConstent.SUCCESS_CODE, GConstent.SUCCESS_MSG, 1, 1);
	}

	public void setOrderCancelReq(OrderCancelReq orderCancelReq) {
		this.orderCancelReq = orderCancelReq;
	}

	public void setOrderCancelResp(OrderCancelResp orderCancelResp) {
		this.orderCancelResp = orderCancelResp;
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

	public void setOrderProductDao(OrderProductDao orderProductDao) {
		this.orderProductDao = orderProductDao;
	}

	public void setVendingStockDao(VendingStockDao vendingStockDao) {
		this.vendingStockDao = vendingStockDao;
	}

	public void setOrderBoxDao(OrderBoxDao orderBoxDao) {
		this.orderBoxDao = orderBoxDao;
	}

}
