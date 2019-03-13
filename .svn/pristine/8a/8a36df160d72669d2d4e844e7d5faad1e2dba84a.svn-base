package com.lmxf.post.alipay.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.lmxf.post.alipay.services.config.AlipayConfig;
import com.lmxf.post.alipay.services.sign.MD5;


/* *
 *类名：AlipayNotify
 *功能：支付宝通知处理类
 *详细：处理支付宝各接口通知返回
 *版本：3.3
 *日期：2012-08-17
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考

 *************************注意*************************
 *调试通知返回时，可查看或改写log日志的写入TXT里的数据，来检查通知返回是否正常
 */
public class AlipayNotify {

	private static Logger log = Logger.getLogger(AlipayNotify.class);
	/**
	 * 支付宝消息验证地址
	 */
	public static final String HTTPS_VERIFY_URL = "https://mapi.alipay.com/gateway.do?service=notify_verify&";

	/**
	 * 验证消息是否是支付宝发出的合法消息
	 * 
	 * @param params
	 *            通知返回来的参数数组
	 * @return 验证结果
	 * @throws UnsupportedEncodingException 
	 */
	public static boolean verify(Map<String, String> params,String Out_trade_no) throws UnsupportedEncodingException {
		// 判断responsetTxt是否为true，isSign是否为true
		// responsetTxt的结果不是true，与服务器设置问题、合作身份者ID、notify_id一分钟失效有关
		// isSign不是true，与安全校验码、请求时的参数格式（如：带自定义参数等）、编码格式有关
		String responseTxt = "true";
		if (params.get("notify_id") != null) {
			responseTxt = verifyResponse(params.get("notify_id"));
		}else if(params.get("notify_datas") != null) {
			responseTxt = verifyResponse2(params.get("notify_data"));
		}
		boolean isSign = false;
		if (params.get("sign") != null) {
			isSign = getSignVeryfy(params, params.get("sign"));
		}

		// 写日志记录（若要调试，请取消下面两行注释）
		// String sWord = "responseTxt=" + responseTxt + "\n isSign=" + isSign +
		// "\n 返回回来的参数：" + AlipayCore.createLinkString(params);
		// AlipayCore.logResult(sWord);
        log.debug("Out_trade_no="+Out_trade_no+"verify==> isSign="+isSign+"  responseTxt="+responseTxt);
		if (isSign &&null!=responseTxt&&responseTxt.equals("true")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 根据反馈回来的信息，生成签名结果
	 * 
	 * @param Params
	 *            通知返回来的参数数组
	 * @param sign
	 *            比对的签名结果
	 * @return 生成的签名结果
	 */
	private static boolean getSignVeryfy(Map<String, String> Params, String sign) {
		// 过滤空值、sign与sign_type参数
		Map<String, String> sParaNew = AlipayCore.paraFilter(Params);
		// 获取待签名字符串
		String preSignStr = AlipayCore.createLinkString(sParaNew);
		// 获得签名验证结果
		boolean isSign = false;
		if (AlipayConfig.sign_type.equals("MD5")) {
			isSign = MD5.verify(preSignStr, sign, AlipayConfig.key_xmn,AlipayConfig.input_charset);
		}
		return isSign;
	}

	/**
	 * 获取远程服务器ATN结果,验证返回URL
	 * 
	 * @param notify_id
	 *            通知校验ID
	 * @return 服务器ATN结果 验证结果集： invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空 true
	 *         返回正确信息 false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
	 */

	private static String verifyResponse(String notify_id) {
		// 获取远程服务器ATN结果，验证是否是支付宝服务器发来的请求
		String partner = AlipayConfig.partner_xmn;
		String veryfy_url = HTTPS_VERIFY_URL + "partner=" + partner	+ "&notify_id=" + notify_id;
		return checkUrl(veryfy_url);
	}
	
	private static String verifyResponse2(String notify_id) throws UnsupportedEncodingException {
		// 获取远程服务器ATN结果，验证是否是支付宝服务器发来的请求
		String partner = AlipayConfig.partner_xmn;
		String veryfy_url = HTTPS_VERIFY_URL + "partner=" + partner	+ "&notify_data=" + java.net.URLEncoder.encode(notify_id,"utf-8");
		return checkUrl(veryfy_url);
	}

	/**
	 * 获取远程服务器ATN结果
	 * 
	 * @param urlvalue
	 *            指定URL路径地址
	 * @return 服务器ATN结果 验证结果集： invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空 true
	 *         返回正确信息 false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
	 */
	public static String checkUrl(String urlvalue) {
		String inputLine = null;
		try {
			//System.out.println(urlvalue);
			//System.out.println("https://mapi.alipay.com/gateway.do?service=notify_verify&partner=2088211235148494&notify_data=%3Cnotify%3E%3Cgmt_payment%3E2016-08-31%2017:19:06%3C/gmt_payment%3E%3Cpartner%3E2088211235148494%3C/partner%3E%3Cbuyer_email%3E376973701@qq.com%3C/buyer_email%3E%3Ctrade_no%3E2016083121001004220299519258%3C/trade_no%3E%3Ctotal_fee%3E0.01%3C/total_fee%3E%3Cgmt_create%3E2016-08-31%2017:18:48%3C/gmt_create%3E%3Cout_trade_no%3E33079629276982%3C/out_trade_no%3E%3Csubject%3E%E6%89%AB%E7%A0%81-%E6%94%AF%E4%BB%980.01%E5%85%83%E4%BA%BA%E6%B0%91%E5%B8%81%3C/subject%3E%3Ctrade_status%3ETRADE_SUCCESS%3C/trade_status%3E%3Cqrcode%3Egdxox0xogtiljbr5ae%3C/qrcode%3E%3C/notify%3E");
			//urlvalue="https://mapi.alipay.com/gateway.do?service=notify_verify&partner=2088211235148494&notify_data=%3Cnotify%3E%3Cgmt_payment%3E2016-08-31%2017:19:06%3C/gmt_payment%3E%3Cpartner%3E2088211235148494%3C/partner%3E%3Cbuyer_email%3E376973701@qq.com%3C/buyer_email%3E%3Ctrade_no%3E2016083121001004220299519258%3C/trade_no%3E%3Ctotal_fee%3E0.01%3C/total_fee%3E%3Cgmt_create%3E2016-08-31%2017:18:48%3C/gmt_create%3E%3Cout_trade_no%3E33079629276982%3C/out_trade_no%3E%3Csubject%3E%E6%89%AB%E7%A0%81-%E6%94%AF%E4%BB%980.01%E5%85%83%E4%BA%BA%E6%B0%91%E5%B8%81%3C/subject%3E%3Ctrade_status%3ETRADE_SUCCESS%3C/trade_status%3E%3Cqrcode%3Egdxox0xogtiljbr5ae%3C/qrcode%3E%3C/notify%3E";
			log.debug("urlvalue="+urlvalue);
			URL url = new URL(urlvalue);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			inputLine = in.readLine();
		} catch (Exception e) {
			e.printStackTrace();
			inputLine = null;
		}
		return inputLine;
	}

	/**
	 * 发送数据
	 * 
	 * @param resp
	 * @param result
	 */
	public static void respAlipay(HttpServletResponse resp, String result) {
		// 发送数据
		resp.setContentType("text/xml");
		resp.setContentLength(result.length());
		try {
			ServletOutputStream os = resp.getOutputStream();
			os.write(result.getBytes());
			os.flush();
			os.close();
		} catch (IOException e) {
		}
	}

	/**
	 * 保存返回或通知信息
	 * 
	 * @param params
	 * @param out_trade_no
	 * @param trade_no
	 * @param trade_status
	 * @param tot_fee
	 * @param acct_No
	 * @param custno
	 */
//	public static void saveParams(Map<String, String> params,
//			String out_trade_no, String trade_no, String trade_status,
//			String tot_fee, String acct_No, String custno,
//			PkgLogService pkgLogService, String remark) {
//		try {
//			// 如果有乱码就转成中文，如果没有就不会转码
//			String saveParams = AlipayConfig.transCode(params.toString());
//			ReturnPayInfo returnPayInfo = new ReturnPayInfo();
//			returnPayInfo.setOut_trade_no(out_trade_no);
//			returnPayInfo.setTransaction_id(trade_no);
//			returnPayInfo.setTrade_state(trade_status);
//			returnPayInfo.setRequestParams(saveParams);
//			returnPayInfo.setTotal_fee(tot_fee);
//			returnPayInfo.setAcct_No(acct_No);
//			returnPayInfo.setCustno(custno);
//			returnPayInfo.setMobile(acct_No);
//			returnPayInfo.setRemark(remark);
//			pkgLogService.insertReturnPayInfo(returnPayInfo);
//		} catch (Exception e) {
//			log.error(e.getMessage() + "  返回参数保存失败");
//		}
//	}

}
