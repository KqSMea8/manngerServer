package com.lmxf.post.core.utils.pay.wechat;



import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import com.lmxf.post.core.utils.GParameter;
import com.lmxf.post.core.utils.MsgPushUtils;
import com.lmxf.post.core.utils.MyXmlUtil;
import com.lmxf.post.core.utils.pay.SDKRuntimeException;
import com.lmxf.post.core.web.BaseController;
import com.lmxf.post.entity.order.OrderApply;
import com.lmxf.post.entity.pay.Order;
import com.lmxf.post.entity.pay.PayconfigWechat;
import com.lmxf.post.service.core.OrderApplyService;
import com.lmxf.post.service.core.PayconfigWechatService;

/**
 * 2018.12.19
 * 微信支付反馈信息处理
 * @author 思杰
 *
 */
public class WxNotifyController extends BaseController {
	private static Logger log = Logger.getLogger(WxNotifyController.class);
	private OrderApplyService orderApplyService;
	private PayconfigWechatService payconfigWechatService;

	public void setOrderApplyService(OrderApplyService orderApplyService) {
		this.orderApplyService = orderApplyService;
	}

	public void setPayconfigWechatService(PayconfigWechatService payconfigWechatService) {
		this.payconfigWechatService = payconfigWechatService;
	}

	/**
	 * 默认跳转的地址
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 */
	public void report(HttpServletRequest request, HttpServletResponse resp) throws IllegalAccessException, InvocationTargetException, Exception {
		notify(request, resp);
	}

	/**
	 * 微信支付成功响应报文 // <xml><appid><![CDATA[wx176de7a3403494ed]]></appid> //
	 * <attach><![CDATA[ilikeXingdu8886]]></attach> //
	 * <bank_type><![CDATA[CFT]]></bank_type> //
	 * <cash_fee><![CDATA[5]]></cash_fee> //
	 * <device_info><![CDATA[20171130]]></device_info> //
	 * <fee_type><![CDATA[CNY]]></fee_type> //
	 * <is_subscribe><![CDATA[Y]]></is_subscribe> //
	 * <mch_id><![CDATA[1371982002]]></mch_id> //
	 * <nonce_str><![CDATA[CUXdVUXa0PVgOaFdwrb4dX3E3nm5MuZX]]></nonce_str> //
	 * <openid><![CDATA[oj0_Gt7ni4ztUMdarLsMo3KkuPG8]]></openid> //
	 * <out_trade_no><![CDATA[88864069407482560213]]></out_trade_no> //
	 * <result_code><![CDATA[SUCCESS]]></result_code> //
	 * <return_code><![CDATA[SUCCESS]]></return_code> //
	 * <sign><![CDATA[DE90D8EE29C03178CC9C989815F92E06]]></sign> //
	 * <time_end><![CDATA[20181218172359]]></time_end> //
	 * <total_fee>5</total_fee> // <trade_type><![CDATA[NATIVE]]></trade_type>
	 * // <transaction_id><![CDATA[4200000209201812184037466300]]></
	 * transaction_id> // </xml>
	 * 
	 * @param request
	 * @param resp
	 * @throws SDKRuntimeException
	 * @throws Exception
	 */
	private void notify(HttpServletRequest request, HttpServletResponse resp) throws SDKRuntimeException, Exception {
		// 获取请求数据
		String xml = MyXmlUtil.getXml(request, resp);
		log.debug("微信支付反馈信息:" + xml);
		// 解析XML数据
		Order vo = getOrder(xml);
		// 检查是否是管理台测试支付信息
		OrderApply orderApplyTest = GParameter.payOrderNoticeMap.get(vo.getOut_trade_no());
		if (orderApplyTest != null) {
			// 判断签名是否正确
			OrderApply orderApply = orderApplyTest;
			boolean tag = false;
			PayconfigWechat payconfigWechat = payconfigWechatService.findOneByCorpId(orderApply.getCorpId());
			if (payconfigWechat != null) {
				tag = judgeAppSign(vo, payconfigWechat);
			} else {
				log.error("微信支付成功,但核心服务器处理失败 公司编号:" + orderApply.getCorpId() + " 无法找到支付配置信息");
			}
			if (!tag) {
				log.error("微信协议签名值错误:" + xml);
			}
			// 删除内存数据
			GParameter.payOrderNoticeMap.remove(vo.getOut_trade_no());
			// 商户订单号,商户系统内部的订单号，32 个字符内、可包含字母，确保在商户系统唯一
			if ("SUCCESS".equals(vo.getResult_code())) {
				MsgPushUtils.doGet(orderApply.getNotice() + "?OrderId=" + orderApply.getOrderId() + "&PayState=1");
			} else {
				MsgPushUtils.doGet(orderApply.getNotice() + "?OrderId=" + orderApply.getOrderId() + "&PayState=2");
			}
			// 响应成功信息
			HashMap<String, String> nativeObj = new HashMap<String, String>();
			nativeObj.put("return_code", "SUCCESS");
			nativeObj.put("return_msg", "SUCCESS");
			xml = ArrayToXml(nativeObj);
			resp.setContentType("text/xml");
			resp.setContentLength(xml.length());
			resp.setHeader("content-type", "text/xml;charset=UTF-8");
			resp.getOutputStream().write(xml.getBytes());
			log.debug("核心服务器反馈微信支付平台信息:" + xml);
		} else {// 正常商品购买支付处理
			// 判断签名是否正确
			OrderApply orderApplyP = new OrderApply();
			orderApplyP.setpTradeNo(vo.getOut_trade_no());
			OrderApply orderApply = orderApplyService.findOneByTradeNo(orderApplyP);
			boolean tag = false;
			PayconfigWechat payconfigWechat = payconfigWechatService.findOneByCorpId(orderApply.getCorpId());
			if (payconfigWechat != null) {
				tag = judgeAppSign(vo, payconfigWechat);
			} else {
				log.error("微信支付成功,但核心服务器处理失败 公司编号:" + orderApply.getCorpId() + " 无法找到支付配置信息");
			}
			if (!tag) {
				log.error("微信协议签名值错误:" + xml);
			}
			try {
				if ("SUCCESS".equals(vo.getResult_code())) {
					orderApplyService.updateByNotify(orderApply, GParameter.payState_success);
				} else {
					orderApplyService.updateByNotify(orderApply, GParameter.payState_failed);
				}
			} catch (Exception e) {
				log.error("微信支付成功,但核心服务器处理失败:" + xml + "  " + e.getMessage());
			}
			// 响应成功信息
			HashMap<String, String> nativeObj = new HashMap<String, String>();
			nativeObj.put("return_code", "SUCCESS");
			nativeObj.put("return_msg", "SUCCESS");
			xml = ArrayToXml(nativeObj);
			resp.setContentType("text/xml");
			resp.setContentLength(xml.length());
			resp.setHeader("content-type", "text/xml;charset=UTF-8");
			resp.getOutputStream().write(xml.getBytes());
			log.debug("核心服务器反馈微信支付平台信息:" + xml);

		}
	}

	/**
	 * 获取签名进行比对，返回布尔值
	 * 
	 * @param wxPayHelper
	 * @param winXin
	 * @return
	 */
	private static boolean judgeAppSign(Order order, PayconfigWechat payconfigWechat) {
		boolean tag = true;
		String sign = null;
		// 获取签名
		try {
			HashMap<String, String> nativeObj = new HashMap<String, String>();
			nativeObj.put("appid", order.getAppid());
			nativeObj.put("attach", order.getAttach());
			nativeObj.put("bank_type", order.getBank_type());
			nativeObj.put("cash_fee", order.getFee() + "");
			nativeObj.put("device_info", order.getDevice_info() + "");
			nativeObj.put("fee_type", order.getFee_type());
			nativeObj.put("is_subscribe", order.getIs_subscribe());
			nativeObj.put("mch_id", order.getMch_id());
			nativeObj.put("nonce_str", order.getNonce_str());
			nativeObj.put("openid", order.getOpenid());
			nativeObj.put("out_trade_no", order.getOut_trade_no());
			nativeObj.put("result_code", order.getReturn_code());
			nativeObj.put("return_code", order.getReturn_code());
			nativeObj.put("time_end", order.getTime_end());
			nativeObj.put("total_fee", order.getTotal_fee() + "");
			nativeObj.put("trade_type", order.getTrade_type());
			nativeObj.put("transaction_id", order.getPrepay_id());
			order.setAppKey(payconfigWechat.getAppSecret());
			log.debug("微信支付反馈响应签名字符串" + nativeObj.toString());
			sign = order.GetBizSign(nativeObj);
			if (!sign.equals(order.getSign())) {
				tag = false;
				log.error("微信支付反馈响应签名字符串 发生错误 " + nativeObj.toString());
			}
		} catch (SDKRuntimeException e) {
			tag = false;
			e.printStackTrace();
		}
		sign = null;
		return tag;
	}

	/**
	 * 生成javabean
	 * 
	 */

	private static Order getOrder(String xml) {
		Order vo = new Order();
		if (xml.contains("<appid>") && xml.contains("</appid>")) {
			vo.setAppid(MyXmlUtil.xmlToString(xml, "<appid>"));
		}
		if (xml.contains("<attach>") && xml.contains("</attach>")) {
			vo.setAttach(MyXmlUtil.xmlToString(xml, "<attach>"));
		}
		if (xml.contains("<bank_type>") && xml.contains("</bank_type>")) {
			vo.setBank_type(MyXmlUtil.xmlToString(xml, "<bank_type>"));
		}
		if (xml.contains("<cash_fee>") && xml.contains("</cash_fee>")) {
			vo.setFee(MyXmlUtil.xmlToString(xml, "<cash_fee>"));
		}
		if (xml.contains("<device_info>") && xml.contains("</device_info>")) {
			vo.setFee(MyXmlUtil.xmlToString(xml, "<device_info>"));
		}
		if (xml.contains("<fee_type>") && xml.contains("</fee_type>")) {
			vo.setFee_type(MyXmlUtil.xmlToString(xml, "<fee_type>"));
		}
		if (xml.contains("<is_subscribe>") && xml.contains("</is_subscribe>")) {
			vo.setIs_subscribe(MyXmlUtil.xmlToString(xml, "<is_subscribe>"));
		}
		if (xml.contains("<mch_id>") && xml.contains("</mch_id>")) {
			vo.setMch_id(MyXmlUtil.xmlToString(xml, "<mch_id>"));
		}
		if (xml.contains("<nonce_str>") && xml.contains("</nonce_str>")) {
			vo.setNonce_str(MyXmlUtil.xmlToString(xml, "<nonce_str>"));
		}
		if (xml.contains("<openid>") && xml.contains("</openid>")) {
			vo.setOpenid(MyXmlUtil.xmlToString(xml, "<openid>"));
		}
		if (xml.contains("<out_trade_no>") && xml.contains("</out_trade_no>")) {
			vo.setOut_trade_no(MyXmlUtil.xmlToString(xml, "<out_trade_no>"));
		}
		if (xml.contains("<result_code>") && xml.contains("</result_code>")) {
			vo.setResult_code(MyXmlUtil.xmlToString(xml, "<result_code>"));
			if ("SUCCESS".equals(vo.getResult_code())) {
				vo.setPay_state("01");
			}
		}
		if (xml.contains("<return_code>") && xml.contains("</return_code>")) {
			vo.setReturn_code(MyXmlUtil.xmlToString(xml, "<return_code>"));
		}
		if (xml.contains("<sign>") && xml.contains("</sign>")) {
			vo.setSign(MyXmlUtil.xmlToString(xml, "<sign>"));
		}
		if (xml.contains("<time_end>") && xml.contains("</time_end>")) {
			vo.setTime_end(MyXmlUtil.xmlToString(xml, "<time_end>"));
		}
		if (xml.contains("<total_fee>") && xml.contains("</total_fee>")) {
			vo.setTotal_fee(MyXmlUtil.xmlToString(xml, "<total_fee>"));
		}
		if (xml.contains("<trade_type>") && xml.contains("</trade_type>")) {
			vo.setTrade_type(MyXmlUtil.xmlToString(xml, "<trade_type>"));
		}
		if (xml.contains("<transaction_id>") && xml.contains("</transaction_id>")) {
			vo.setPrepay_id(MyXmlUtil.xmlToString(xml, "<transaction_id>"));
		}

		if (xml.contains("<sub_mch_id>") && xml.contains("</sub_mch_id>")) {
			vo.setSub_mch_id(MyXmlUtil.xmlToString(xml, "<sub_mch_id>"));
		}
		return vo;
	}

	public static String ArrayToXml(HashMap<String, String> arr) {
		String xml = "<xml>";

		Iterator<Entry<String, String>> iter = arr.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, String> entry = iter.next();
			String key = entry.getKey();
			String val = entry.getValue();
			if (IsNumeric(val)) {
				xml += "<" + key + ">" + val + "</" + key + ">";
			} else
				xml += "<" + key + "><![CDATA[" + val + "]]></" + key + ">";
		}

		xml += "</xml>";
		return xml;
	}

	public static boolean IsNumeric(String str) {
		if (str.matches("\\d *")) {
			return true;
		} else {
			return false;
		}
	}
}
