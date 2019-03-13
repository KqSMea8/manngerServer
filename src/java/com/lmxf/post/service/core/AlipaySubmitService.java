package com.lmxf.post.service.core;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.json.JSONException;
import org.json.JSONObject;

import com.lmxf.post.alipay.entity.Alipay;
import com.lmxf.post.alipay.services.AlipayCore;
import com.lmxf.post.alipay.services.config.AlipayConfig;
import com.lmxf.post.alipay.services.httpClient.HttpProtocolHandler;
import com.lmxf.post.alipay.services.httpClient.HttpRequest;
import com.lmxf.post.alipay.services.httpClient.HttpResponse;
import com.lmxf.post.alipay.services.httpClient.HttpResultType;
import com.lmxf.post.alipay.services.sign.MD5;
import com.lmxf.post.core.utils.CheckUtils;
import com.lmxf.post.core.utils.Constants;
import com.lmxf.post.core.utils.CxfClient;
import com.lmxf.post.core.utils.DateUtils;
import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.GParameter;
import com.lmxf.post.core.utils.pay.PayApplyResp;
import com.lmxf.post.core.utils.pay.PrintUtils;
import com.lmxf.post.entity.pay.Order;
import com.lmxf.post.repository.config.ErrorCodeDao;

/* *
 *类名：AlipaySubmitService
 *功能：支付宝各接口请求提交类
 *详细：构造支付宝各接口表单HTML文本，获取远程HTTP数据
 *版本：3.3
 *日期：2012-08-13
 */
public class AlipaySubmitService{
	private final static Log log = LogFactory.getLog(AlipaySubmitService.class);
	private ErrorCodeDao errorCodeDao;

	public void setErrorCodeDao(ErrorCodeDao errorCodeDao) {
		this.errorCodeDao = errorCodeDao;
	}

	/**
	 * 生成签名结果
	 * 
	 * @param sPara
	 *            要签名的数组
	 * @return 签名结果字符串
	 */
	public static String buildRequestMysign(Map<String, String> sPara) {
		String prestr = AlipayCore.createLinkString(sPara); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
		String mysign = "";
		if (AlipayConfig.sign_type.equals("MD5")) {
			mysign = MD5.sign(prestr, AlipayConfig.key_xmn, AlipayConfig.input_charset);
		}
		return mysign;
	}

	/**
	 * 生成要请求给支付宝的参数数组
	 * 
	 * @param sParaTemp
	 *            请求前的参数数组
	 * @return 要请求的参数数组
	 */
	public static Map<String, String> buildRequestPara(Map<String, String> sParaTemp) {
		// 除去数组中的空值和签名参数
		Map<String, String> sPara = AlipayCore.paraFilter(sParaTemp);
		// 生成签名结果
		String mysign = buildRequestMysign(sPara);

		// 签名结果与签名方式加入请求提交参数组中
		sPara.put("sign", mysign);
		sPara.put("sign_type", AlipayConfig.sign_type);

		return sPara;
	}

	/**
	 * 生成要请求给支付宝的参数数组
	 * 
	 * @param sParaTemp
	 *            请求前的参数数组
	 * @return 要请求的参数数组
	 */
	private static String buildRequestPara_alipay(Map<String, String> sParaTemp) {
		// 除去数组中的空值和签名参数
		Map<String, String> sPara = AlipayCore.paraFilter(sParaTemp);
		// 生成签名结果
		String mysign = buildRequestMysign(sPara);
		return mysign;
	}

	/**
	 * 建立请求，以表单HTML形式构造（默认）
	 * 
	 * @param sParaTemp
	 *            请求参数数组
	 * @param strMethod
	 *            提交方式。两个值可选：post、get
	 * @param strButtonName
	 *            确认按钮显示文字
	 * @return 提交表单HTML文本
	 */
	public static String buildRequest(Map<String, String> sParaTemp, String strMethod, String strButtonName) {
		// 待请求参数数组
		Map<String, String> sPara = buildRequestPara(sParaTemp);
		List<String> keys = new ArrayList<String>(sPara.keySet());

		StringBuffer sbHtml = new StringBuffer();

		sbHtml.append("<form target=\"blank\" id=\"alipaysubmit\" name=\"alipaysubmit\" action=\"" + AlipayConfig.ALIPAY_GATEWAY_NEW + "_input_charset=" + AlipayConfig.input_charset + "\" method=\"" + strMethod + "\">");

		for (int i = 0; i < keys.size(); i++) {
			String name = keys.get(i);
			String value = sPara.get(name);

			sbHtml.append("<input type=\"text\" name=\"" + name + "\" value=\"" + value + "\"/>");
		}

		// submit按钮控件请不要含有name属性 type=\"submit\" style=\"display:none;\"
		sbHtml.append("<input type=\"button\"  value=\"" + strButtonName + "\"  onclick=\"toAlipay()\"/></form>");
		sbHtml.append("<script>function toAlipay(){ document.forms['alipaysubmit'].submit();}</script>");
		String htmlString = sbHtml.toString();
		PrintUtils.OUT(htmlString);
		return htmlString;
	}

	/**
	 * 建立请求，以表单HTML形式构造（默认）
	 * 
	 * @param sParaTemp
	 *            请求参数数组
	 * @param strMethod
	 *            提交方式。两个值可选：post、get
	 * @param strButtonName
	 *            确认按钮显示文字
	 * @return 提交表单HTML文本
	 */
	public static String buildRequest_zzw(Map<String, String> sParaTemp, String strMethod) throws Exception {
		// 待请求参数数组
		Map<String, String> sPara = buildRequestPara(sParaTemp);
		List<String> keys = new ArrayList<String>(sPara.keySet());

		StringBuffer sbHtml = new StringBuffer();
		sbHtml.append(AlipayConfig.ALIPAY_GATEWAY_NEW + "_input_charset=" + AlipayConfig.input_charset + "&");

		for (int i = 0; i < keys.size(); i++) {
			String name = keys.get(i);
			String value = sPara.get(name);

			sbHtml.append(name + "=" + URLEncoder.encode(value, AlipayConfig.input_charset) + "&");
		}

		// 去掉最后一个&
		String htmlString = sbHtml.substring(0, sbHtml.lastIndexOf("&"));

		PrintUtils.OUT(htmlString);
		return htmlString;
	}

	/**
	 * 建立请求，以表单HTML形式构造，带文件上传功能
	 * 
	 * @param sParaTemp
	 *            请求参数数组
	 * @param strMethod
	 *            提交方式。两个值可选：post、get
	 * @param strButtonName
	 *            确认按钮显示文字
	 * @param strParaFileName
	 *            文件上传的参数名
	 * @return 提交表单HTML文本
	 */
	public static String buildRequest(Map<String, String> sParaTemp, String strMethod, String strButtonName, String strParaFileName) {
		// 待请求参数数组
		Map<String, String> sPara = buildRequestPara(sParaTemp);
		List<String> keys = new ArrayList<String>(sPara.keySet());

		StringBuffer sbHtml = new StringBuffer();

		sbHtml.append("<form id=\"alipaysubmit\" name=\"alipaysubmit\"  enctype=\"multipart/form-data\" action=\"" + AlipayConfig.ALIPAY_GATEWAY_NEW + "_input_charset=" + AlipayConfig.input_charset + "\" method=\"" + strMethod + "\">");

		for (int i = 0; i < keys.size(); i++) {
			String name = keys.get(i);
			String value = sPara.get(name);

			sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
		}

		sbHtml.append("<input type=\"file\" name=\"" + strParaFileName + "\" />");

		// submit按钮控件请不要含有name属性
		sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");

		return sbHtml.toString();
	}

	/**
	 * 建立请求，以模拟远程HTTP的POST请求方式构造并获取支付宝的处理结果
	 * 如果接口中没有上传文件参数，那么strParaFileName与strFilePath设置为空值 如：buildRequest("",
	 * "",sParaTemp)
	 * 
	 * @param strParaFileName
	 *            文件类型的参数名
	 * @param strFilePath
	 *            文件路径
	 * @param sParaTemp
	 *            请求参数数组
	 * @return 支付宝处理结果
	 * @throws Exception
	 */
	public static String buildRequest(String strParaFileName, String strFilePath, Map<String, String> sParaTemp) throws Exception {
		// 待请求参数数组
		Map<String, String> sPara = buildRequestPara(sParaTemp);

		HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();

		HttpRequest request = new HttpRequest(HttpResultType.BYTES);
		// 设置编码集
		request.setCharset(AlipayConfig.input_charset);

		request.setParameters(generatNameValuePair(sPara));
		request.setUrl(AlipayConfig.ALIPAY_GATEWAY_NEW + "_input_charset=" + AlipayConfig.input_charset);

		HttpResponse response = httpProtocolHandler.execute(request, strParaFileName, strFilePath);
		if (response == null) {
			return null;
		}

		String strResult = response.getStringResult();

		return strResult;
	}

	/**
	 * MAP类型数组转换成NameValuePair类型
	 * 
	 * @param properties
	 *            MAP类型数组
	 * @return NameValuePair类型数组
	 */
	private static NameValuePair[] generatNameValuePair(Map<String, String> properties) {
		NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
		int i = 0;
		for (Map.Entry<String, String> entry : properties.entrySet()) {
			nameValuePair[i++] = new NameValuePair(entry.getKey(), entry.getValue());
		}

		return nameValuePair;
	}

	/**
	 * 用于防钓鱼，调用接口query_timestamp来获取时间戳的处理函数 注意：远程解析XML出错，与服务器是否支持SSL等配置有关
	 * 
	 * @return 时间戳字符串
	 * @throws IOException
	 * @throws DocumentException
	 * @throws MalformedURLException
	 */
	@SuppressWarnings("unchecked")
	public static String query_timestamp() throws MalformedURLException, DocumentException, IOException {

		// 构造访问query_timestamp接口的URL串
		String strUrl = AlipayConfig.ALIPAY_GATEWAY_NEW + "service=query_timestamp&partner=" + AlipayConfig.partner_xmn + "&_input_charset" + AlipayConfig.input_charset;
		StringBuffer result = new StringBuffer();

		SAXReader reader = new SAXReader();
		Document doc = reader.read(new URL(strUrl).openStream());

		List<Node> nodeList = doc.selectNodes("//alipay/*");

		for (Node node : nodeList) {
			// 截取部分不需要解析的信息
			if (node.getName().equals("is_success") && node.getText().equals("T")) {
				// 判断是否有成功标示
				List<Node> nodeList1 = doc.selectNodes("//response/timestamp/*");
				for (Node node1 : nodeList1) {
					result.append(node1.getText());
				}
			}
		}

		return result.toString();
	}

	public String payURL(Order vo) {
		String payment_type = "1";// 支付类型 // 必填，不能修改
		String subject = "智汇合帐户充值";// 订单名称//必填
		String body = "虚拟钱包充值";// 订单描述
		String method = "get";

		String logistics_fee = "0.00";
		String receive_address = "深圳市南山区科技园高新南七路深圳软件园T3-B栋4层";
		String receive_phone = "0755-86020661-8024";
		String quantity = "1";
		String receive_zip = "518057";
		String logistics_payment = "SELLER_PAY";
		String logistics_type = "EXPRESS";

		String receive_name = vo.getUser_name();
		String price = vo.getTotal_fee()*0.01+"";
		String receive_mobile = vo.getUser_mobile();

		boolean checkMobile = CheckUtils.isMobile(receive_mobile);
		if (!checkMobile) {
			return errorCodeDao.getErrorRespJson(GConstent.Invalid_Mobile);
		}

		// String out_trade_no = DateUtils.getOrderNum();// + "_"+
		// orderInfoReq.getMobile(); // 商户订单号 商户网站订单系统中唯一订单号，必填
		String out_trade_no = OrderApplyService.getOrderId();
		String show_url = vo.getShow_url();// 商品展示地址需以http://开头的完整路径，例如：http://www.xxx.com/myorder.html
		// show_url="http://www.chinawebox.com/pay/pay.htm?method=seeOrder&out_trade_no="+out_trade_no;
		show_url = "http://115.29.232.128:8888/pay/pay.htm?method=seeOrder&out_trade_no=" + out_trade_no;

		if (null == receive_name || "".equals(receive_name)) {
			receive_name = vo.getUser_mobile();
		}
		vo.setOut_trade_no(out_trade_no);
		// ////////////////////////////////////////////////////////////////////////////////
		Map<String, String> sParaTemp = getParaMap(payment_type, subject, body, logistics_type, logistics_fee, receive_address, receive_phone, quantity, receive_zip, logistics_payment, receive_name, price, receive_mobile, out_trade_no, show_url);
		// 建立请求
		String sHtmlText = AlipaySubmitService.buildRequest(sParaTemp, method, "确认");
		String sHtmlText2 = null;

		try {
			sHtmlText2 = AlipaySubmitService.buildRequest_zzw(sParaTemp, method);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(sHtmlText2);
		}
		String sign = AlipaySubmitService.buildRequestPara_alipay(sParaTemp);
		log.debug("sHtmlText" + sHtmlText);

		// <img
		// src="https://mobilecodec.alipay.com/show.htm?code=gd3o45pbkdx32ffod3"
		// alt="即时二维码，请扫描支付">
		// String qrcode = "rr" + price + ".png";
		String qrcode = applyCreateQrcode(out_trade_no, price);
		// String qrcode = GetQrcode(out_trade_no, price, quantity);

		Alipay alipay = getAlipayInfo(payment_type, subject, body, method, logistics_type, logistics_fee, receive_address, receive_phone, quantity, receive_zip, logistics_payment, receive_name, price, receive_mobile, out_trade_no, show_url, sign, qrcode);
		if (alipay != null) {
			alipay.setPayType(vo.getPay_type());
		}
		return PreCreateXml(alipay, sHtmlText2);
	}

	private Alipay getAlipayInfo(String payment_type, String subject, String body, String method, String logistics_type, String logistics_fee, String receive_address, String receive_phone, String quantity, String receive_zip, String logistics_payment, String receive_name,
			String price, String receive_mobile, String out_trade_no, String show_url, String sign, String qrcode) {
		String target = "blank";
		String service = "trade_create_by_buyer";
		String action = AlipayConfig.ALIPAY_GATEWAY_NEW + "_input_charset=" + AlipayConfig.input_charset;
		Alipay alipay = new Alipay();
		alipay.setAction(action);
		alipay.setMethod(method);
		alipay.setTarget(target);
		alipay.setBody(body);
		alipay.setLogistics_type(logistics_type);
		alipay.setLogistics_fee(logistics_fee);
		alipay.setSubject(subject);
		alipay.setSign_type(AlipayConfig.sign_type);
		alipay.setReceive_address(receive_address);
		alipay.setReceive_phone(receive_phone);
		alipay.setReceive_name(receive_name);
		CxfClient cxfClient=new CxfClient();
		String path = cxfClient.getPathInfo();
		alipay.setNotify_url(path + GParameter.notify_url);
		alipay.setOut_trade_no(out_trade_no);
		alipay.setReturn_url(path + GParameter.return_url);
		alipay.set_input_charset(AlipayConfig.input_charset);
		alipay.setService(service);
		alipay.setPrice(price);
		alipay.setTotal_fee(price);
		alipay.setReceive_mobile(receive_mobile);
		alipay.setQuantity(quantity);
		alipay.setPartner(AlipayConfig.partner_xmn);
		alipay.setSeller_email(AlipayConfig.seller_email_xmn);
		alipay.setReceive_zip(receive_zip);
		alipay.setLogistics_payment(logistics_payment);
		alipay.setPayment_type(payment_type);
		alipay.setShow_url(show_url);
		alipay.setQrcode(qrcode);
		alipay.setSign(sign);
		return alipay;
	}

	private Map<String, String> getParaMap(String payment_type, String subject, String body, String logistics_type, String logistics_fee, String receive_address, String receive_phone, String quantity, String receive_zip, String logistics_payment, String receive_name,
			String price, String receive_mobile, String out_trade_no, String show_url) {
		// 把请求参数打包成数组
		CxfClient cxfClient=new CxfClient();
		String path = cxfClient.getPathInfo();
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "trade_create_by_buyer");
		sParaTemp.put("partner", AlipayConfig.partner_xmn);
		sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("payment_type", payment_type);
		sParaTemp.put("notify_url", path + GParameter.notify_url);
		sParaTemp.put("return_url", path + GParameter.return_url);
		sParaTemp.put("seller_email", AlipayConfig.seller_email_xmn);
		sParaTemp.put("out_trade_no", out_trade_no);
		sParaTemp.put("subject", subject);
		sParaTemp.put("price", price);
		sParaTemp.put("quantity", quantity);
		sParaTemp.put("logistics_fee", logistics_fee);
		sParaTemp.put("logistics_type", logistics_type);
		sParaTemp.put("logistics_payment", logistics_payment);
		sParaTemp.put("body", body);
		sParaTemp.put("show_url", show_url);
		sParaTemp.put("receive_name", receive_name);
		sParaTemp.put("receive_address", receive_address);
		sParaTemp.put("receive_zip", receive_zip);
		sParaTemp.put("receive_phone", receive_phone);
		sParaTemp.put("receive_mobile", receive_mobile);
		return sParaTemp;
	}

	private String PreCreateXml(Alipay alipay, String sHtmlText) {
		PayApplyResp resp = new PayApplyResp();
		resp.setRetcode(Constants.SUCCESS_CODE);
		resp.setCurnum(1);
		resp.setTotnum(1);
		resp.setRetmsg(sHtmlText);
		resp.setAlipay(alipay);
		String ret = resp.CreateXml();
		return ret;
	}

	
	/**
	 * 请求生成二维码
	 * 
	 * @param biz_name
	 * @param biz_amount_list
	 * @param biz_count_list
	 * @param biz_amount
	 * @return
	 */
	private String applyCreateQrcode(String out_trade_no, String price) {
		String qrcodes = "";
		try {
			String service = "alipay.mobile.qrcode.manage";
			// 接口调用时间 格式为：yyyy-MM-dd HH:mm:ss
			String timestamp = DateUtils.getDateFormatter();
			// 动作 创建商品二维码
			String method = "add";
			// 业务类型 目前只支持1
			String biz_type = "10";
			// 业务数据 // 格式：JSON 大字符串，详见技术文档4.2.1章节
			// String biz_data = getJsonString(out_trade_no, price);

			// 业务数据 // 格式：JSON 大字符串，详见技术文档4.2.1章节
			// String biz_data = getJsonStringForPay(out_trade_no, price);

			// 业务数据 // 格式：JSON 大字符串，详见技术文档4.2.1章节
			String biz_data = getJsonStringForRecharge(out_trade_no, price);
			biz_data = getJsonStringForPayment(out_trade_no, price);
			// 把请求参数打包成数组
			Map<String, String> sParaTemp = new HashMap<String, String>();
			sParaTemp.put("service", service);
			sParaTemp.put("partner", AlipayConfig.partner_xmn);
			sParaTemp.put("_input_charset", AlipayConfig.input_charset);
			sParaTemp.put("timestamp", timestamp);
			sParaTemp.put("method", method);
			sParaTemp.put("biz_type", biz_type);
			sParaTemp.put("biz_data", biz_data);

			// 建立请求
			String qrxml = AlipaySubmitService.buildRequest("", "", sParaTemp);
			qrcodes = AlipaySubmitService.parseQrcode(qrxml);
		} catch (Exception e) {
			qrcodes = "";
		}

		return qrcodes;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getJsonString(String out_trade_no, String price) {
		String ret = "";
		CxfClient cxfClient=new CxfClient();
		String path = cxfClient.getPathInfo();
		try {
			JSONObject ext_info = new JSONObject();
			ext_info.put("single_limit", "1");
			ext_info.put("user_limit", "999999");
			ext_info.put("pay_timeout", "30");
			ext_info.put("logo_name", "帐户充值");// "充值" + price + "元");//
												// AlipayConfig.short_recevie_account_name);//二维码logo名称，最多5个汉字或者10个数字/字母。

			JSONObject ext_field = new JSONObject();
			ext_field.put("input_title", "请输入智汇合钱包对应的11位手机号码");
			ext_field.put("input_regex", "^[1][3-8]+\\d{9}$");
			JSONObject ext_field2 = new JSONObject();
			ext_field2.put("input_title", "请输入要支付费用的8位支付码如14060001");
			// ext_field2.put("input_regex", "^\\d{20}$");
			// ext_field2.put("input_regex", "^\\w{0,20}$");
			// ext_field2.put("input_regex", "^[14]+\\d{6}$");

			// 网站的二维码用R, 箱柜终端用N, 手机终端用M, 平面终端用 P;
			List aList = new ArrayList();
			aList.add(ext_field);
			// aList.add(ext_field2);
			ext_info.put("ext_field", aList);
			JSONObject goods_info = new JSONObject();
			goods_info.put("id", out_trade_no);
			goods_info.put("name", "R" + price);
			goods_info.put("price", price);
			goods_info.put("expiry_date", "2014-05-01 00:00:01|2015-12-30 23:59:59");
			goods_info.put("desc", "向智汇合虚拟钱包帐户充值，帐户一般是以手机号注册");

			JSONObject json = new JSONObject();
			json.put("goods_info", goods_info);
			json.put("memo", "test");
			json.put("need_address", "F");
			json.put("trade_type", "1");
			json.put("return_url", path + GParameter.qrcode_return_url);
			json.put("notify_url", path + GParameter.aliQrcode_notify_url);
			json.put("ext_info", ext_info);

			ret = json.toString();
		} catch (JSONException e) {
		}

		return ret;
	}

	/**
	 * 为充值产生的二维码
	 * 
	 * @param out_trade_no
	 * @param price
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getJsonStringForRecharge(String out_trade_no, String price) {
		String ret = "";
		CxfClient cxfClient=new CxfClient();
		String path = cxfClient.getPathInfo();
		try {
			JSONObject ext_info = new JSONObject();
			ext_info.put("single_limit", "10");
			ext_info.put("user_limit", "999999");
			ext_info.put("pay_timeout", "30");
			// ext_info.put("logo_name","充值"+price+"元");//
			ext_info.put("logo_name", "帐户充值");// "钱包充值");//
												// AlipayConfig.short_recevie_account_name);//二维码logo名称，最多5个汉字或者10个数字/字母。

			JSONObject ext_field = new JSONObject();
			ext_field.put("input_title", "请输入充值用户的手机号码");// "请输入智汇合钱包对应的11位手机号码");
			ext_field.put("input_regex", "^[1][3-8]+\\d{9}$");
			List aList = new ArrayList();
			aList.add(ext_field);
			ext_info.put("ext_field", aList);
			JSONObject goods_info = new JSONObject();
			goods_info.put("id", "N" + out_trade_no);
			goods_info.put("name", "智汇合帐户充值");// "充值"+price+"元人民币");// "R帐户充值");
			goods_info.put("price", price);
			goods_info.put("expiry_date", "2016-05-01 00:00:01|2017-12-30 23:59:59");
			goods_info.put("desc", "该充值用于超期包裹费用支付、寄件支付");
			// goods_info.put("desc", "智汇合虚拟钱包帐户充值");

			goods_info.put("sku_title", "请选择充值金额");
			// 网站的二维码用R, 箱柜终端用N, 手机终端用M, 平面终端用 P;
			goods_info.put("sku", getGoodList("N"));

			JSONObject json = new JSONObject();
			json.put("goods_info", goods_info);
			json.put("memo", "test");
			json.put("need_address", "F");
			json.put("trade_type", "1");
			json.put("return_url", path + GParameter.qrcode_return_url);
			json.put("notify_url", path + GParameter.qrcode_notify_url);
			json.put("ext_info", ext_info);

			ret = json.toString();
		} catch (JSONException e) {
		}

		return ret;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List getGoodList(String r) throws JSONException {
		// 网站的二维码用R, 箱柜终端用N, 手机终端用M, 平面终端用 P;
		JSONObject sku001 = new JSONObject();
		sku001.put("sku_id", r + "0.01");
		// sku001.put("sku_id", "充值0.01元人民币");
		sku001.put("sku_name", "0.01元");
		sku001.put("sku_price", "0.01");
		sku001.put("sku_inventory", "99999999");

		JSONObject sku010 = new JSONObject();
		sku010.put("sku_id", r + "0.10");
		sku010.put("sku_name", "0.10元");
		sku010.put("sku_price", "0.10");
		sku010.put("sku_inventory", "99999999");

		JSONObject sku100 = new JSONObject();
		sku100.put("sku_id", r + "1.00");
		sku100.put("sku_name", "1.00元");
		sku100.put("sku_price", "1.00");
		sku100.put("sku_inventory", "99999999");

		JSONObject sku200 = new JSONObject();
		sku200.put("sku_id", r + "2.00");
		sku200.put("sku_name", "2.00元");
		sku200.put("sku_price", "2.00");
		sku200.put("sku_inventory", "99999999");

		JSONObject sku300 = new JSONObject();
		sku300.put("sku_id", r + "3.00");
		sku300.put("sku_name", "3.00元");
		sku300.put("sku_price", "3.00");
		sku300.put("sku_inventory", "99999999");

		JSONObject sku500 = new JSONObject();
		sku500.put("sku_id", r + "5.00");
		sku500.put("sku_name", "5.00元");
		sku500.put("sku_price", "5.00");
		sku500.put("sku_inventory", "99999999");

		JSONObject sku1000 = new JSONObject();
		sku1000.put("sku_id", r + "10.00");
		sku1000.put("sku_name", "10.0元");
		sku1000.put("sku_price", "10.00");
		sku1000.put("sku_inventory", "99999999");

		JSONObject sku2000 = new JSONObject();
		sku2000.put("sku_id", r + "20.00");
		sku2000.put("sku_name", "20.0元");
		sku2000.put("sku_price", "20.00");
		sku2000.put("sku_inventory", "99999999");

		JSONObject sku3000 = new JSONObject();
		sku3000.put("sku_id", r + "30.00");
		sku3000.put("sku_name", "30.0元");
		sku3000.put("sku_price", "30.00");
		sku3000.put("sku_inventory", "9999999");

		JSONObject sku5000 = new JSONObject();
		sku5000.put("sku_id", r + "50.00");
		sku5000.put("sku_name", "50.0元");
		sku5000.put("sku_price", "50.00");
		sku5000.put("sku_inventory", "999999");

		JSONObject sku10000 = new JSONObject();
		sku10000.put("sku_id", r + "100.00");
		sku10000.put("sku_name", "100元");
		sku10000.put("sku_price", "100.00");
		sku10000.put("sku_inventory", "99999");

		JSONObject sku20000 = new JSONObject();
		sku20000.put("sku_id", r + "200.00");
		sku20000.put("sku_name", "200元");
		sku20000.put("sku_price", "200.00");
		sku20000.put("sku_inventory", "9999");

		JSONObject sku30000 = new JSONObject();
		sku30000.put("sku_id", r + "300.00");
		sku30000.put("sku_name", "300元");
		sku30000.put("sku_price", "300.00");
		sku30000.put("sku_inventory", "9999");

		List skuList = new ArrayList();
		// skuList.add(sku001);
		// skuList.add(sku010);
		skuList.add(sku100);
		skuList.add(sku200);
		// skuList.add(sku300);
		skuList.add(sku500);
		skuList.add(sku1000);
		skuList.add(sku2000);
		skuList.add(sku3000);
		skuList.add(sku5000);
		skuList.add(sku10000);
		skuList.add(sku20000);
		skuList.add(sku30000);

		return skuList;
	}

	/**
	 * 为支付费用产生的二维码
	 * 
	 * @param out_trade_no
	 * @param price
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getJsonStringForPay(String out_trade_no, String price) {
		String ret = "";
		CxfClient cxfClient=new CxfClient();
		String path = cxfClient.getPathInfo();
		try {
			JSONObject ext_info = new JSONObject();
			ext_info.put("single_limit", "1");
			ext_info.put("user_limit", "100");
			ext_info.put("pay_timeout", "30");
			ext_info.put("logo_name", "支付费用");// AlipayConfig.short_recevie_account_name);//二维码logo名称，最多5个汉字或者10个数字/字母。

			JSONObject ext_field = new JSONObject();
			ext_field.put("input_title", "请输入要支付费用的8位支付码如14060001");
			ext_field.put("input_regex", "^[14]+\\d{6}$");
			List aList = new ArrayList();
			aList.add(ext_field);
			ext_info.put("ext_field", aList);
			JSONObject goods_info = new JSONObject();
			goods_info.put("id", out_trade_no);
			goods_info.put("name", "N费用支付");
			goods_info.put("price", price);
			goods_info.put("expiry_date", "2014-05-01 00:00:01|2015-12-30 23:59:59");
			goods_info.put("desc", "向智汇合支付箱子费用");
			goods_info.put("sku_title", "请选择支付金额");
			String r = "n";// 网站的二维码用R, 箱柜终端用N, 手机终端用M, 平面终端用 P;
			goods_info.put("sku", getGoodList(r));

			JSONObject json = new JSONObject();
			json.put("goods_info", goods_info);
			json.put("memo", "test");
			json.put("need_address", "T");
			json.put("trade_type", "2");
			json.put("return_url", path + GParameter.qrcode_return_url);
			json.put("notify_url", path + GParameter.qrcode_notify_url);
			json.put("ext_info", ext_info);

			ret = json.toString();
		} catch (JSONException e) {
		}

		return ret;
	}

	public String getJsonString_bak(String out_trade_no, String price) {
		String ret = "";
		CxfClient cxfClient=new CxfClient();
		String path = cxfClient.getPathInfo();
		try {
			JSONObject ext_info = new JSONObject();
			ext_info.put("single_limit", "2");
			ext_info.put("user_limit", "3");
			ext_info.put("logo_name", AlipayConfig.recevie_account_name);
			JSONObject goods_info = new JSONObject();
			goods_info.put("id", out_trade_no);
			goods_info.put("name", "钱包充值");
			goods_info.put("price", price);
			goods_info.put("expiry_date", "2014-05-01 00:00:01|2015-12-30 23:59:59");
			goods_info.put("desc", "人民币金额1分");
			goods_info.put("sku_title", "1分");
			JSONObject sku = new JSONObject();
			sku.put("sku_id", "001");
			sku.put("sku_name", "1分");
			sku.put("sku_price", price);
			sku.put("sku_inventory", "500");
			goods_info.put("sku", sku);

			JSONObject json = new JSONObject();
			json.put("memo", "智汇合网盒充值");
			// json.put("ext_info", ext_info);
			json.put("goods_info", goods_info);
			json.put("need_address", "T");
			json.put("trade_type", "1");
			json.put("return_url", path + GParameter.qrcode_return_url);
			json.put("notify_url", path + GParameter.qrcode_notify_url);

			ret = json.toString();
		} catch (JSONException e) {
		}

		return ret;
	}

	public static String parseHead(String xml) {
		String qrcode = "";
		if (null == xml)
			return "";
		try {
			Document document = DocumentHelper.parseText(xml);
			Element root = document.getRootElement();
			qrcode = root.selectSingleNode("response/createQrcode/qrcode").getText();
		} catch (DocumentException e) {
		}
		return qrcode.substring(qrcode.lastIndexOf("/") + 1);
	}

	public static String parseNotifyXml(String xml, String name) {
		String qrcode = "";
		if (null == xml)
			return "";
		try {
			Document document = DocumentHelper.parseText(xml);
			Element root = document.getRootElement();
			qrcode = root.selectSingleNode("notify/" + name).getText();
		} catch (DocumentException e) {
			log.error("parse qrcode error",e);
		}
		return qrcode;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map xmlStringToMap(String xmlString) {
		Map map = null;
		try {
			map = new HashMap();
			if (!"".equals(xmlString) || null != xmlString) {
				// 解析xml文件
				Document doc = DocumentHelper.parseText(xmlString);
				// 获取根元素
				Element root = doc.getRootElement();

				Iterator elementIterator = root.elementIterator();
				while (elementIterator.hasNext()) {
					Node next = (Node) elementIterator.next();
					String name = next.getName();
					String text = next.getText();
					map.put(name, text);
				}
			}
		} catch (Exception e) {
			log.error("parse xml error",e);
			e.printStackTrace();
		}
		return map;
	}

	/***
	 * <?xml version="1.0" encoding="utf-8"?>
<alipay><is_success>T</is_success><request><param name="sign">df1057c81df25ab12a78cc726e761bce</param><param name="timestamp">2016-08-05 17:54:21</param><param name="biz_type">10</param><param name="_input_charset">utf-8</param><param name="biz_data">{"memo":"test","ext_info":{"single_limit":"1","logo_name":"水公园支付","user_limit":"1","pay_timeout":"30"},"notify_url":"http://pay.chinawebox.com/alipayQrcode_notify_url.action","need_address":"F","goods_info":{"id":"T30516368714279","desc":"该资金用于寄存包裹、超期包裹费用支付、寄件支付","price":"0.01","name":"订单号30516368714279","expiry_date":"2016-08-04 00:00:01|2016-09-18 23:59:59"},"trade_type":"1","return_url":"http://pay.chinawebox.com/alipayQrcode_return_url.action"}</param><param name="sign_type">MD5</param><param name="service">alipay.mobile.qrcode.manage</param><param name="method">add</param><param name="partner">2088211235148494</param></request><response><alipay><qrcode>https://qr.alipay.com/gdxox0xo1fty7wim5c</qrcode><qrcode_img_url>https://mobilecodec.alipay.com/show.htm?code=gdxox0xo1fty7wim5c&amp;picSize=S</qrcode_img_url><result_code>SUCCESS</result_code></alipay></response><sign>70041338a613f97f63c33f1a8e329052</sign><sign_type>MD5</sign_type></alipay>
	 * @param xml
	 * @return
	 */
	public static String parseQrcode(String xml) {
		String qrcode = "";
		if (null == xml)
			return "";
		try {
			Document document = DocumentHelper.parseText(xml);
			Element root = document.getRootElement();
			qrcode = root.selectSingleNode("response/alipay/qrcode_img_url").getText();
		} catch (DocumentException e) {
		}
		return getQrcodeUrl(qrcode);
	}

	private static String getQrcodeUrl(String a) {
		try {
			// String a="https://mobilecodec.alipay.com/show.htm?code=gdhngki5l7tj2tk4cd&picSize=S";
			//a = a.substring(a.indexOf("=") + 1, a.indexOf("&"));
			a = a.substring(0, a.indexOf("&"));
		} catch (Exception e) {
		}
		return a;
	}

	/**
	 * 产生支付即时二维码
	 * 
	 * @param out_trade_no
	 * @param price
	 * @return
	 */
	public String getJsonStringForPayment(String out_trade_no, String price) {
		String ret = "";
		CxfClient cxfClient=new CxfClient();
		String path = cxfClient.getPathInfo();
		try {
			JSONObject ext_info = new JSONObject();
			ext_info.put("single_limit", "1");
			ext_info.put("user_limit", "1");
			ext_info.put("pay_timeout", "30");
			ext_info.put("logo_name", "水公园支付");// AlipayConfig.short_recevie_account_name);//二维码logo名称，最多5个汉字或者10个数字/字母。

			JSONObject goods_info = new JSONObject();
			goods_info.put("id", "T" + out_trade_no);
			goods_info.put("name", "订单号" + out_trade_no);// "充值"+price+"元人民币");//
															// "R帐户充值");
			goods_info.put("price", price);
			goods_info.put("expiry_date", "2016-08-04 00:00:01|2016-09-18 23:59:59");
			goods_info.put("desc", "该资金用于寄存包裹、超期包裹费用支付、寄件支付");

			JSONObject json = new JSONObject();
			json.put("goods_info", goods_info);
			json.put("memo", "test");
			json.put("need_address", "F");
			json.put("trade_type", "1");
			json.put("return_url", path + GParameter.qrcode_return_url);
			json.put("notify_url", path + GParameter.qrcode_notify_url);
			json.put("ext_info", ext_info);

			ret = json.toString();
		} catch (JSONException e) {
		}

		return ret;
	}

}
