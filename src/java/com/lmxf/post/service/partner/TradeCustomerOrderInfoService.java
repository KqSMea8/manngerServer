package com.lmxf.post.service.partner;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.putils.web.Page;

import com.lmxf.post.core.utils.CacheUtils;
import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.GParameter;
import com.lmxf.post.entity.order.OrderApply;
import com.lmxf.post.entity.order.OrderProduct;
import com.lmxf.post.entity.parameter.Dict;
import com.lmxf.post.repository.order.OrderApplyDao;
import com.lmxf.post.repository.order.OrderProductDao;
import com.lmxf.post.service.core.TradeProcess;
import com.lmxf.post.tradepkg.partner.CustomerOrderInfoReq;
import com.lmxf.post.tradepkg.partner.CustomerOrderInfoResp;

public class TradeCustomerOrderInfoService extends TradeProcess {
	private final static Log log = LogFactory.getLog(TradeCustomerOrderInfoService.class);
	private CustomerOrderInfoReq customerOrderInfoReq;
	private CustomerOrderInfoResp customerOrderInfoResp;
	private OrderApplyDao orderApplyDao;
	private OrderProductDao orderProductDao;

	@SuppressWarnings("rawtypes")
	public String tradeProcess(String apply_xml) {
		OrderApply orderApplyP = null;
		List<OrderApply> orderApplyList = null;
		Map ret = null;
		try {
			ret = customerOrderInfoReq.parseXml(apply_xml);
			if (ret.get("code") == null) {
				return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
			} else if (ret.get("code") != null && Integer.parseInt(ret.get("code").toString()) != 0) {
				log.error("customerOrderInfoReq.parseXml is error!");
				return errorCodeDao.getErrorRespJson(Integer.parseInt(ret.get("code").toString()));
			}
		} catch (Exception e) {
			log.error("---parseXml  error-----", e);
			return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
		}
		orderApplyP = (OrderApply) ret.get("orderApply");
		
		Page page = new Page("CustomerOrderInfo");
		Dict dict=(Dict) CacheUtils.get(CacheUtils.errorCodeCache,"issuedPage_size");
	    String pageNum = dict!=null?dict.getDictValue():null;
		page.setCurrentPage(Integer.parseInt((String) ret.get("istart")));
		page.setPageNum(20);
		if (pageNum != null && !"".equals(pageNum)) {
			try {
				page.setPageNum(Integer.parseInt(pageNum));
			} catch (Exception e) {
				return errorCodeDao.getErrorRespJson(GConstent.Program_App_Execp);
			}
		}
		orderApplyList = orderApplyDao.findCustomerOrder(page, orderApplyP);
		if (null == orderApplyList || orderApplyList.size() <= 0) {
			return errorCodeDao.getErrorRespJson(GConstent.Query_No_Data);
		}
		for (OrderApply orderApply : orderApplyList) {
			OrderProduct orderProduct = new OrderProduct();
			orderProduct.setOrderId(orderApply.getOrderId());
			List<OrderProduct> orderProductList = orderProductDao.findAll(orderProduct);
			if(null != orderProductList && orderProductList.size() > 0){
				orderApply.setOrderProductList(orderProductList);
			}
		}
		return customerOrderInfoResp.CreateJson(GConstent.SUCCESS_CODE, GConstent.SUCCESS_MSG, page.getTotalCount(), orderApplyList.size(), orderApplyList);
	}

	public CustomerOrderInfoReq getCustomerOrderInfoReq() {
		return customerOrderInfoReq;
	}

	public void setCustomerOrderInfoReq(CustomerOrderInfoReq customerOrderInfoReq) {
		this.customerOrderInfoReq = customerOrderInfoReq;
	}

	public CustomerOrderInfoResp getCustomerOrderInfoResp() {
		return customerOrderInfoResp;
	}

	public void setCustomerOrderInfoResp(CustomerOrderInfoResp customerOrderInfoResp) {
		this.customerOrderInfoResp = customerOrderInfoResp;
	}

	public OrderApplyDao getOrderApplyDao() {
		return orderApplyDao;
	}

	public void setOrderApplyDao(OrderApplyDao orderApplyDao) {
		this.orderApplyDao = orderApplyDao;
	}

	public OrderProductDao getOrderProductDao() {
		return orderProductDao;
	}

	public void setOrderProductDao(OrderProductDao orderProductDao) {
		this.orderProductDao = orderProductDao;
	}

}
