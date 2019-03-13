package com.lmxf.post.core.utils.pay.alipay;



import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.lmxf.post.core.utils.GParameter;
import com.lmxf.post.core.web.BaseController;
import com.lmxf.post.entity.order.OrderApply;
import com.lmxf.post.service.core.OrderApplyService;

/**
 * 支付宝支付反馈处理
 * 
 * @author 思杰
 * 
 */
public class AlipayNotifyController extends BaseController {
	private final static Log log = LogFactory.getLog(AlipayNotifyController.class);
	private OrderApplyService orderApplyService;

	public void setOrderApplyService(OrderApplyService orderApplyService) {
		this.orderApplyService = orderApplyService;
	}

	/**
	 * 默认跳转的地址
	 * 
	 * @param req
	 * @param resp
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws Exception
	 */
	public void report(HttpServletRequest req, HttpServletResponse resp) throws IllegalAccessException, InvocationTargetException, Exception {
		// 获取支付宝POST过来反馈信息
		String outTradeNo = req.getParameter("out_trade_no");
		String tradeStatus = req.getParameter("trade_status");
		log.error("支付宝支付反馈信息 out_trade_no:" + req.getParameter("out_trade_no") + "  trade_status" + req.getParameter("trade_status"));
		try {
			OrderApply orderApplyP = new OrderApply();
			orderApplyP.setpTradeNo(outTradeNo);
			OrderApply orderApply = orderApplyService.findOneByTradeNo(orderApplyP);
			if (orderApply != null) {
				if (tradeStatus.equals("TRADE_SUCCESS")) {
					orderApplyService.updateByNotify(orderApply, GParameter.payState_success);
				} else {
					orderApplyService.updateByNotify(orderApply, GParameter.payState_failed);
				}
			} else {
				log.error("支付宝反馈信息 out_trade_no:" + outTradeNo + "  没有找到订单信息.");
			}
		} catch (Exception e) {
			log.error("支付宝反馈信息 out_trade_no:" + outTradeNo + "  核心服务器处理出错:" + e.getMessage());
		}
		log.debug("核心服务器响应支付宝平台成功信息 success");
		resp.getOutputStream().write("success".getBytes());
	}

}
