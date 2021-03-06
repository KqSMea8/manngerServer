package com.lmxf.post.service.init;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.putils.web.Page;

import com.lmxf.post.core.utils.CacheUtils;
import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.GParameter;
import com.lmxf.post.entity.order.OrderApply;
import com.lmxf.post.entity.order.OrderBox;
import com.lmxf.post.entity.order.OrderProduct;
import com.lmxf.post.entity.parameter.Dict;
import com.lmxf.post.repository.order.OrderApplyDao;
import com.lmxf.post.repository.order.OrderBoxDao;
import com.lmxf.post.repository.order.OrderProductDao;
import com.lmxf.post.service.core.TradeProcess;
import com.lmxf.post.tradepkg.init.OrderAllIssuedReq;
import com.lmxf.post.tradepkg.init.OrderAllIssuedResp;

public class TradeOrderAllIssuedService extends TradeProcess {
	private final static Log log = LogFactory.getLog(TradeOrderAllIssuedService.class);
	private OrderAllIssuedReq orderAllIssuedReq;
	private OrderAllIssuedResp orderAllIssuedResp;
	private OrderApplyDao orderApplyDao;
	private OrderProductDao orderProductDao;
	private OrderBoxDao orderBoxDao;

	public String tradeProcess(String apply_xml) {
		List<OrderApply> list = new ArrayList<OrderApply>();
		List<OrderApply> listP = null;
		Map ret = null;
		try {
			ret = orderAllIssuedReq.parseXml(apply_xml);
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
		String siteId = (String) ret.get("siteId");
		Page page = new Page("IssuedOrderDetail");
		try {
			Dict dict = (Dict) CacheUtils.get(CacheUtils.errorCodeCache, "issuedPage_size");
			String pageNum = dict != null ? dict.getDictValue() : null;
			page.setCurrentPage(Integer.parseInt((String) ret.get("istart")));
			page.setPageNum(20);
			if (pageNum != null && !"".equals(pageNum)) {
				try {
					page.setPageNum(Integer.parseInt(pageNum));
				} catch (Exception e) {
					return errorCodeDao.getErrorRespJson(GConstent.Program_App_Execp);
				}
			}
			OrderApply orderDetail = new OrderApply();
			orderDetail.setSiteId(siteId);
			orderDetail.setOrderType(GParameter.orderOrderType_nomarl);
			list = this.orderApplyDao.findAll(orderDetail);
			for (OrderApply orderApplyTemp : list) {
				// 查询商品信息
				OrderBox orderBoxP = new OrderBox();
				orderBoxP.setOrderId(orderApplyTemp.getOrderId());
				List<OrderBox> orderBoxList = orderBoxDao.findAll(orderBoxP);
				orderApplyTemp.setOrderBoxList(orderBoxList);
			}
		} catch (Exception e) {
			log.error("---findOrderDetailIssued error---:" + e.getMessage());
			return errorCodeDao.getErrorRespJson(GConstent.Program_Query_Execp);
		}
		if (list != null && list.size() < 1) {
			log.error("---OrderDetail.findIssuedOrderDetail  nodata ---:");
			return errorCodeDao.getErrorRespJson(GConstent.Query_No_Data);
		}
		return orderAllIssuedResp.CreateJson(GConstent.SUCCESS_CODE, GConstent.SUCCESS_MSG, page.getTotalCount(), list.size(), list);
	}

	public OrderAllIssuedReq getOrderAllIssuedReq() {
		return orderAllIssuedReq;
	}

	public void setOrderAllIssuedReq(OrderAllIssuedReq orderAllIssuedReq) {
		this.orderAllIssuedReq = orderAllIssuedReq;
	}

	public OrderAllIssuedResp getOrderAllIssuedResp() {
		return orderAllIssuedResp;
	}

	public void setOrderAllIssuedResp(OrderAllIssuedResp orderAllIssuedResp) {
		this.orderAllIssuedResp = orderAllIssuedResp;
	}

	public void setOrderApplyDao(OrderApplyDao orderApplyDao) {
		this.orderApplyDao = orderApplyDao;
	}

	public void setOrderProductDao(OrderProductDao orderProductDao) {
		this.orderProductDao = orderProductDao;
	}

	public void setOrderBoxDao(OrderBoxDao orderBoxDao) {
		this.orderBoxDao = orderBoxDao;
	}

}
