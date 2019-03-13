package com.lmxf.post.alipay.services.config;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.putils.utils.CPropertyUtil;

import com.lmxf.post.alipay.services.AlipaySubmit;
import com.lmxf.post.core.utils.pay.PrintUtils;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。

 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class AlipayConfig {

	// ↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	public static String partner_xmn = (String) CPropertyUtil.getContextProperty("alipay.partner");
	// 商户的私钥
	public static String key_xmn = (String) CPropertyUtil.getContextProperty("alipay.key_xmn");
	
	
	//商户的私钥,需要PKCS8格式，RSA公私钥生成：https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.nBDxfy&treeId=58&articleId=103242&docType=1
	public static String private_key_xmn =(String) CPropertyUtil.getContextProperty("alipay.private_key");
	// 支付宝的公钥,查看地址：https://b.alipay.com/order/pidAndKey.htm
	public static String alipay_public_key_xmn  =(String) CPropertyUtil.getContextProperty("alipay.alipay_public_key");
	public static String appid_xmn = (String) CPropertyUtil.getContextProperty("alipay.appid");
	public static String ali_title = (String) CPropertyUtil.getContextProperty("alipay.ali_title");
	// ↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

	// 调试用，创建TXT日志文件夹路径
	public static String log_path = "D:\\";
	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";
	// 签名方式 不需修改
	public static String sign_type = "MD5";

	public static String gb = "ISO-8859-1";

	// 卖家支付宝帐户//必填
	public static String seller_email_xmn = (String) CPropertyUtil.getContextProperty("alipay.seller_email");

	// 卖家支付宝帐户
	public static String recevie_account_name = (String) CPropertyUtil.getContextProperty("alipay.recevie_account_name");

	// 卖家支付宝帐户(简称)
	public static String short_recevie_account_name = (String) CPropertyUtil.getContextProperty("alipay.short_recevie_account_name");

	
	// 支付宝查询服务的api地址     https://openapi.alipay.com/gateway.do";
	public static final String ALIPAY_API = (String) CPropertyUtil.getContextProperty("alipay.ALIPAY_API");
		
	// 支付宝提供给商户的服务接入网关URL(新)
	// public static final String ALIPAY_GATEWAY_NEW = "https://mapi.alipay.com/gateway.do?";
	public static final String ALIPAY_GATEWAY_NEW = (String) CPropertyUtil.getContextProperty("alipay.ALIPAY_GATEWAY_NEW");

	/************************************* 支付宝手机支付参数 ****************************************/
	// //支付宝手机网站网关地址
	public static final String MOBILE_ALIPAY_GATEWAY_NEW = (String) CPropertyUtil.getContextProperty("alipay.MOBILE_ALIPAY_GATEWAY_NEW");

	// 返回格式 //必填，不需要修改
	public static String format = "xml";

	// 返回格式 必填，不需要修改
	public static String v = "2.0";

	public static byte[] reqstr(HttpServletRequest request, String name) throws UnsupportedEncodingException {
		String getString;
		try {
			getString = request.getParameter(name);
		} catch (Exception e) {
			getString = "";
		}
		if ("".equals(getString) || getString == null) {
			getString = "";
		}
		return getString.getBytes(gb);
	}

	public static float toFloat(Object str) {
		float value = 0;
		try {
			value = Float.parseFloat(str.toString());
		} catch (Exception e) {
			value = 0;
		}
		return value;
	}

	public static int toInt(Object str) {
		int value = 0;
		try {
			value = Integer.parseInt(str.toString());
		} catch (Exception e) {
			value = 0;
		}
		return value;
	}

	public static int getInt(int a, int b) {
		int c = 0;
		if (a <= 0 || b <= 0) {
			return c;
		}
		if (a % b == 0) {
			c = a / b;
		} else {
			c = a / b + 1;
		}
		return c;
	}

	public static String reqStr(HttpServletRequest request, String name) throws UnsupportedEncodingException {
		String reqStr = null;
		try {
			reqStr = request.getParameter(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if ("".equals(reqStr) || reqStr == null) {
			reqStr = "";
		}
		return new String(reqStr.getBytes(gb), input_charset);
	}

	@SuppressWarnings("rawtypes")
	public static Map<String, String> getParams(HttpServletRequest request) throws UnsupportedEncodingException {
		// 返回的参数集合
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			valueStr = new String(valueStr.getBytes(AlipayConfig.gb), AlipayConfig.input_charset);
			params.put(name, valueStr);
		}
		return params;
	}

	@SuppressWarnings("rawtypes")
	public static Map<String, String> getParamsUTF8(HttpServletRequest request) throws UnsupportedEncodingException {
		// 返回的参数集合
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		PrintUtils.OUT("requestParams:" + requestParams.toString());
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			// valueStr = new String(valueStr.getBytes(AlipayConfig.gb),AlipayConfig.input_charset);
			params.put(name, valueStr);
		}
		PrintUtils.OUT("params:" + params.toString());
		return params;
	}

	// 解析用户输入的手机号帐户和订单信息
	public static String getContextInfo(String context_data, String name) {
		String value1 = null;
		try {
			net.sf.json.JSONObject jsonobj = net.sf.json.JSONObject.fromObject(context_data);// 将字符串转化成json对象
			value1 = jsonobj.getString(name);// 获取字符串。
			/*
			 * JSONArray array=jsonobj.getJSONArray("array");//获取数组 JSONObject obj=jsonobj.getJSONObject("people");//获取对象
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value1;
	}

	/**
	 * 发货
	 * 
	 * @param trade_no
	 * @param trade_status
	 * @return
	 */
	public static String sendGood(String out_trade_no, String trade_no, String trade_status) {
		if ("".equals(trade_no) || trade_no == null) {
			return "交易号为空，不能发货";
		}
		if (!"WAIT_SELLER_SEND_GOODS".equals(trade_status)) {
			return "非发货状态，不能发货";
		}
		// 把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "send_goods_confirm_by_platform");
		sParaTemp.put("partner", AlipayConfig.partner_xmn);
		sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("trade_no", trade_no);
		sParaTemp.put("logistics_name", "空邮"); // 物流公司名称// 必填
		sParaTemp.put("invoice_no", "13410310002"); // 物流发货单号
		sParaTemp.put("transport_type", "EXPRESS");// 物流运输类型,三个值可选：POST（平邮）、EXPRESS（快递）、EMS（EMS）
		String sHtmlText = null;
		// 建立请求
		try {
			sHtmlText = AlipaySubmit.buildRequest("", "", sParaTemp);
		} catch (Exception e) {
		}
		return sHtmlText;
	}

	/**
	 * 判断如果是担保交易，那么财付通不会发送总金额给我们，那么需要自己从申请订单表里面获取总金额
	 */
//	public static String getTotalFee(String totalFee, String out_trade_no, PkgLogService pkgLogService) {
//
//		// 根据订单号查询请求记录表
//		if (!"".equals(out_trade_no) && null != out_trade_no) {
//			TradeInfo info = new TradeInfo();
//			info.setOut_trade_no(out_trade_no);
//			List<TradeInfo> list = pkgLogService.findAll(info);
//			if (null != list && list.size() > 0) {
//				info = list.get(0);
//				totalFee = info.getTotal_fee();
//			}
//		}
//		return totalFee;
//	}

	/**
	 * 将分转化为元单位
	 * 
	 * @param total_fee
	 * @return
	 */
	public static float getTotalFee(String total_fee) {
		float fee = 0.00f;
		try {
			fee = (Float.parseFloat(total_fee)) / 100;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fee;
	}

	/**
	 * 判断如果是担保交易，那么财付通不会发送总金额给我们，那么需要自己从申请订单表里面获取总金额
	 */
//	public static TradeInfo getTradeInfo(String out_trade_no, PkgLogService pkgLogService) {
//		TradeInfo info = new TradeInfo();
//		// 根据订单号查询请求记录表
//		if (!"".equals(out_trade_no) && null != out_trade_no) {
//			info.setOut_trade_no(out_trade_no);
//			List<TradeInfo> list = pkgLogService.findAll(info);
//			if (null != list && list.size() > 0) {
//				info = list.get(0);
//			}
//		}
//		return info;
//	}

	/**
	 * 如果进来的字符串有乱码就会解码，如果没有就不会
	 * 
	 * @param str
	 * @return
	 */
	public static String transCode(String str) {
		if (str == null) {
			return null;
		}
		String retStr = str;
		byte b[];
		try {
			b = str.getBytes("ISO8859_1");

			for (int i = 0; i < b.length; i++) {
				byte b1 = b[i];
				if (b1 == 63) {
					break; // 1
				} else if (b1 > 0) {
					continue;// 2
				} else if (b1 < 0) { // 不可能为0，0为字符串结束符
					retStr = new String(b, "UTF-8");
					break;
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return retStr;
	}
}
