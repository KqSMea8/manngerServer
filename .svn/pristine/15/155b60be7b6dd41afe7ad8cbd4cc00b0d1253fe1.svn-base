package com.lmxf.post.entity.pay;

import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lmxf.post.core.utils.pay.PrintUtils;
import com.lmxf.post.core.utils.pay.SDKRuntimeException;

/**
 * 扫码请求支付订单，网页申请支付订单 的信息实体
 * @author Administrator
 *
 */
public class Order {
	private final static Log log = LogFactory.getLog(Order.class);
	/**站点编号*/     
	private String auth_name;
	private String apply_xml;
	
    public String getAuth_name() {
		return auth_name;
	}
	public void setAuth_name(String auth_name) {
		this.auth_name = auth_name;
	}
	public String getApply_xml() {
		return apply_xml;
	}
	public void setApply_xml(String apply_xml) {
		this.apply_xml = apply_xml;
	}
	/**站点编号*/     
	private String site_id;
	/**站点名称*/ 
	private String site_name;
	/**箱型（产品、商品）编号*/
	private String product_typeid;
	/**箱型（产品、商品）名称*/
	private String product_typename;
	/**箱柜（产品、商品）编号*/
	private String product_id;
	/**收款公司编号*/
	private String corp_id;
	/**收款公司名称*/
	private String corp_name;
	/**收款方式：支付宝扫码支付00、微信扫码支付01*/
	private String pay_type;
	/**终端订单流水编号*/
	private String order_id;
	/**终端订单条码*/
	private String bar_code;
	/**终端订单请求支付的时间*/
	private String order_time;
	/**货币类型CNY人民币USD美元HKG港币*/
	private String currency_type;	
	/**货币金额，以分为单位,实际交易金额*/
	private int total_fee;
	/**费用类型：00箱柜寄存01售买单个产品金额02租用物品费用03广告费用04门票05会员卡充值06餐费07内部交通费08小费09公益捐赠10赔偿金
	 * 21条码支付-储物柜消费　22条码支付-浮圈消费　
　　　　　　自设密码柜和腕带显示: 条码支付-储物柜消费　　　　浮圈显示: 条码支付-浮圈消费
	 * */
	private String fee_type;
	/**订单内容简述*/
	private String order_desc;
	/**业务编号**/
	private String bcode;
	/**终端IP,APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP**/
	private String spbill_create_ip;
	/**货币金额，以分为单位，服务或商品原始价格*/
	private String fee;
	private String is_subscribe;
	private String bank_type;
	private String sub_mch_id;
	private String time_end;
	private String fee_num;
	
	public String getSub_mch_id() {
		return sub_mch_id;
	}
	public void setSub_mch_id(String sub_mch_id) {
		this.sub_mch_id = sub_mch_id;
	}
	public String getTime_end() {
		return time_end;
	}
	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}
	public String getBank_type() {
		return bank_type;
	}
	public void setBank_type(String bank_type) {
		this.bank_type = bank_type;
	}
	public String getIs_subscribe() {
		return is_subscribe;
	}
	public void setIs_subscribe(String is_subscribe) {
		this.is_subscribe = is_subscribe;
	}
	/**费用金额**/
	public String getFee_num() {
		return fee_num;
	}
	public void setFee_num(String fee_num) {
		this.fee_num = fee_num;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee=fee;
	}
	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}
	public void setSpbill_create_ip(String spbill_create_ip) {
		if (null==spbill_create_ip||"".equals(spbill_create_ip)) {
			spbill_create_ip="127.0.0.1";
		}else {
			spbill_create_ip=spbill_create_ip.trim();
		}
		if (spbill_create_ip.length()>16||spbill_create_ip.length()<7) {
			spbill_create_ip="127.0.0.1";
		}
		this.spbill_create_ip = spbill_create_ip;
	}
	public String getBcode() {
		return bcode;
	}
	public void setBcode(String bcode) {
		this.bcode = bcode;
	}
	/**商家订单号，返回给终端的订单号**/
	private String out_trade_no;//
	
	public String getSite_id() {
		return site_id;
	}
	public void setSite_id(String site_id) {
		this.site_id = site_id;
	}
	public String getSite_name() {
		return site_name;
	}
	public void setSite_name(String site_name) {
		this.site_name = site_name;
	}
	public String getProduct_typeid() {
		return product_typeid;
	}
	public void setProduct_typeid(String product_typeid) {
		this.product_typeid = product_typeid;
	}
	public String getProduct_typename() {
		return product_typename;
	}
	public void setProduct_typename(String product_typename) {
		this.product_typename = product_typename;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getCorp_id() {
		return corp_id;
	}
	public void setCorp_id(String corp_id) {
		this.corp_id = corp_id;
	}
	public String getCorp_name() {
		return corp_name;
	}
	public void setCorp_name(String corp_name) {
		this.corp_name = corp_name;
	}
	/**收款方式：支付宝扫码支付00、微信扫码支付01,微信公众号支付02,微信小程序支付07、快盈刷卡支付08,快盈一码清支付09***/
	public String getPay_type() {
		return pay_type;
	}
	/**收款方式：支付宝扫码支付00、微信扫码支付01,微信公众号支付02,微信小程序支付07、快盈刷卡支付08,快盈一码清支付09***/
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getBar_code() {
		return bar_code;
	}
	public void setBar_code(String bar_code) {
		this.bar_code = bar_code;
	}
	public String getOrder_time() {
		return order_time;
	}
	public void setOrder_time(String order_time) {
		this.order_time = order_time;
	}
	public String getCurrency_type() {
		return currency_type;
	}
	public void setCurrency_type(String currency_type) {
		if (null==currency_type||"".equals(currency_type.trim())) {
			currency_type="CNY";//默认使用人民币
		}
		if (currency_type.trim().length()!=3) {
			currency_type="CNY";//默认使用人民币
		}
		this.currency_type = currency_type.trim();
	}
	public int getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(String total_fee) {
		try {
			DecimalFormat Format = new DecimalFormat("#.00");
			String totalFee=Format.format(Double.parseDouble(total_fee));
			Double d=Double.parseDouble(totalFee)*100;
			this.total_fee=d.intValue();
		} catch (NumberFormatException e) {
			e.printStackTrace();
			this.total_fee=0;
		}
	}
	/**费用类型：00箱柜寄存01售买单个产品金额02租用物品费用03广告费用04门票05会员卡充值06餐费07内部交通费08小费09公益捐赠10赔偿金
	 * 21条码支付-储物柜消费　22条码支付-浮圈消费　23腕带初次租用 24腕带充值 25腕带加值 26腕带消费  27全部退款(适用于异常退费,没有消费),28部分退款(适用于已消费情况,终端已减去租金),29部分退款(适用于已消费情况,委托支付平台减去租金)
　　　　　　自设密码柜和腕带显示: 条码支付-储物柜消费　　　　浮圈显示: 条码支付-浮圈消费
	 * */
	public String getFee_type() {
		return fee_type;
	}
	/**费用类型：00箱柜寄存01售买单个产品金额02租用物品费用03广告费用04门票05会员卡充值06餐费07内部交通费08小费09公益捐赠10赔偿金
	 * 21条码支付-储物柜消费　22条码支付-浮圈消费　23腕带初次租用 24腕带充值 25腕带加值 26腕带消费  27全部退款(适用于异常退费,没有消费),28部分退款(适用于已消费情况,终端已减去租金),29部分退款(适用于已消费情况,委托支付平台减去租金)
　　　　　　自设密码柜和腕带显示: 条码支付-储物柜消费　　　　浮圈显示: 条码支付-浮圈消费
	 * */
	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}
	public String getOrder_desc() {
		return order_desc;
	}
	public void setOrder_desc(String order_desc) {
		this.order_desc = order_desc;
	}
	/**商家订单号，返回给终端的订单号**/
	public String getOut_trade_no() {
		return out_trade_no;
	}
	/**商家订单号，返回给终端的订单号**/
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	
	///////////微信特有的数据字段
	/**附加数据 128自己一下 原样返回**/
	private String attach;
	private HashMap<String, String> parameters = new HashMap<String, String>();
	private String appid; //公众账号ID,微信分配的公众账号ID（企业号corpid即为此appId） String(32)
	private String appKey;
	private String mch_id;//商户号,微信支付分配的商户号String(32)
	private String limit_pay;//指定支付方式,no_credit--指定不能使用信用卡支付
	private String device_info;//设备号,终端设备号(门店号或收银设备ID)，注意：PC网页或公众号内支付请传"WEB" String(32)
	private String nonce_str;//随机字符串，不长于32位
	private String sign;//签名String(32)
	private String body;//商品描述,商品简单描述，该字段须严格按照规范传递String(128)
	private String detail;//商品详细列表，使用Json格式，传输签名前请务必使用CDATA标签将JSON文本串保护起来。String(6000)
	private String notify_url;//通知地址,接收支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
	private String return_url;//跳转地址
	private String trade_type;//交易类型,微信支付取值如下：JSAPI，NATIVE，APP  支付宝支付取值如下：10
    private String openid;//用户标识,trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识。openid如何获取，可参考【获取openid】。企业号请使用【企业号OAuth2.0接口】获取企业号内成员userid，再调用【企业号userid转openid接口】进行转换
	private String show_url;//商品展示地址,订单明细展示
	private String sign_type = "sha1";//签名加密方式
	
	public String getReturn_url() {
		return return_url;
	}
	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getShow_url() {
		return show_url;
	}
	public void setShow_url(String show_url) {
		this.show_url = show_url;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public String getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	public String getLimit_pay() {
		return limit_pay;
	}
	public void setLimit_pay(String limit_pay) {
		this.limit_pay = limit_pay;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public HashMap<String, String> getParameters() {
		return parameters;
	}
	public void setParameters(HashMap<String, String> parameters) {
		this.parameters = parameters;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	public String getMch_id() {
		return mch_id;
	}
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}
	public String getDevice_info() {
		return device_info;
	}
	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}
	public String getNonce_str() {
		return nonce_str;
	}
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	/**附加数据 128自己一下 原样返回**/
	public String getAttach() {
		return attach;
	}
	/**附加数据 128自己一下 原样返回**/
	public void setAttach(String attach) {
		this.attach = attach;
	}
	
	public void SetParameter(String key, String value) {
		parameters.put(key, value);
	}

	public String GetParameter(String key) {
		return parameters.get(key);
	}

	private Boolean CheckCftParameters() {
		log.debug("参数校验:"+parameters.toString());
		if ("".equals(parameters.get("bank_type"))|| "".equals(parameters.get("body"))
				|| "".equals(parameters.get("out_trade_no"))
				|| "0".equals(parameters.get("total_fee"))
				|| "".equals(parameters.get("fee_type"))
				|| "".equals(parameters.get("notify_url"))
				|| "".equals(parameters.get("spbill_create_ip"))
				) {
			return false;
		}
		return true;
	}

	public String GetBizSign(Map<String, String> bizObj) throws SDKRuntimeException {
		if (appKey == "") {
			throw new SDKRuntimeException("APPKEY为空！");
		}
		StringBuffer parms = new StringBuffer();
		List<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String, String>>(bizObj.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
				public int compare(Map.Entry<String, String> o1,Map.Entry<String, String> o2) {
					return o1.getKey().compareTo(o2.getKey());
				}
			});		
		
		for (int i = 0; i < list.size(); i++) {
			if ("sign".equals(list.get(i).getKey())) {
				continue;
			}
			if (!"".equals(list.get(i).getKey())&&null!=list.get(i).getValue()&&!"".equals(list.get(i).getValue())) {
				parms.append(list.get(i).getKey().toLowerCase()+"="+list.get(i).getValue()+ "&");
			}
		}
		list=null;
		parms.append("key"+"="+appKey);
		return MD5(parms.toString());

	}
	
	 /**
		 * MD5加密码
		 * 
		 * @param s
		 * @return 返回加密字符串
		 */
	    public static String MD5(String s) {
	        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
	        try {
				byte[] btInput = s.getBytes("utf-8");
				// 获得MD5摘要算法的 MessageDigest 对象
	            MessageDigest mdInst = MessageDigest.getInstance("MD5");
				// 使用指定的字节更新摘要
	            mdInst.update(btInput);
				// 获得密文
	            byte[] md = mdInst.digest();
				// 把密文转换成十六进制的字符串形式
	            int j = md.length;
	            char str[] = new char[j * 2];
	            int k = 0;
	            for (int i = 0; i < j; i++) {
	                byte byte0 = md[i];
	                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
	                str[k++] = hexDigits[byte0 & 0xf];
	            }
	            return new String(str);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
	
	// 生成原生支付请求xml
	public String CreateNativePackage() throws SDKRuntimeException {
		if (CheckCftParameters() == false) {
			throw new SDKRuntimeException("生成package参数缺失！");
		}
		parameters.put("sign", GetBizSign(parameters));
		return ArrayToXml(parameters);
	}
	
	
	
	public static String CreateNoncestr() {
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuffer res =new StringBuffer("");
		Random rd = null;
		for (int i = 0; i < 32; i++) {
			rd = new Random();
			res.append(chars.charAt(rd.nextInt(chars.length() - 1)));
		}
		rd = null;
		return res.toString();
	}
	
	public static String ArrayToXml(Map<String, String> arr) {
		StringBuffer xml =new StringBuffer("<xml>") ;
		String key =null;
		String val =null;
		Iterator<Entry<String, String>> iter = arr.entrySet().iterator();
		Entry<String, String> entry =null;
		while (iter.hasNext()) {
			entry = iter.next();
			key = entry.getKey();
			val = entry.getValue();
			if (null==val) {
				//xml.append("<" + key + ">" + "" + "</" + key + ">");
			}else if (isNumeric(val)) {
				xml.append("<" + key + ">" + val + "</" + key + ">");
			} else
				xml.append("<" + key + "><![CDATA[" + val + "]]></" + key + ">");
		}
		xml.append("</xml>");
		key =null;
		val =null;
		iter=null;
		entry =null;
		return xml.toString();
	}
	
	public static boolean isNumeric(String str){
		if (null==str||"".equals(str.trim())) {
			return false;
		}
		return Pattern.compile("[0-9]*").matcher(str).matches(); 
	}

		
	public static boolean IsNumeric(String str) {
		if (null==str) {
			return false;
		}
		if (str.matches("\\d*")) {
			return true;
		} else {
			return false;
		}
	}
	
	
	//申请支付链接或支付二维码时第三方支付平台返回值
	    /**申请预支付返回结果码**/
		private String return_code;
		/**申请预支付返回结果描述**/
		private String return_msg;
		/**申请预支付二维码返回结果码**/
		private String result_code;
		/**申请预支付订单号**/
		private String prepay_id;
		/**二维码内容**/
		private String code_url;
		/**二维码图片地址**/
		private String qrcodeimg_url;
		/**普通支付链接地址**/
		private String pay_url;
		
		public String getPay_url() {
			return pay_url;
		}
		public void setPay_url(String pay_url) {
			this.pay_url = pay_url;
		}
		public String getReturn_code() {
			return return_code;
		}

		public void setReturn_code(String return_code) {
			this.return_code = return_code;
		}

		public String getReturn_msg() {
			return return_msg;
		}

		public void setReturn_msg(String return_msg) {
			this.return_msg = return_msg;
		}

		public String getResult_code() {
			return result_code;
		}

		public void setResult_code(String result_code) {
			this.result_code = result_code;
		}

		public String getPrepay_id() {
			return prepay_id;
		}

		public void setPrepay_id(String prepay_id) {
			this.prepay_id = prepay_id;
		}

		public String getCode_url() {
			return code_url;
		}

		public void setCode_url(String code_url) {
			this.code_url = code_url;
		}

		public String getQrcodeimg_url() {
			return qrcodeimg_url;
		}

		public void setQrcodeimg_url(String qrcodeimg_url) {
			this.qrcodeimg_url = qrcodeimg_url;
		}
		
		private String source_qrcode;
		
		
		public String getSource_qrcode() {
			return source_qrcode;
		}
		public void setSource_qrcode(String source_qrcode) {
			this.source_qrcode = source_qrcode;
		}
		//用户信息
		/**用户在系统中的注册或登录账号**/
		private String cust_no;
		/**用户类型01 普通用户**/
		private String member_type;
		/**用户虚拟钱包账号**/
		private String user_acct;
		/**用户姓名**/
		private String user_name;
		/**用户手机**/
		private String user_mobile;
		/**用户电子邮箱地址**/
		private String user_email;
		/**支付截止时间**/
		private String limit_time;
		/**订单记录时间**/
		private String create_time;
		/**订单变化时间**/
		private String update_time;
		/**订单修改操作者**/
		private String update_person;
		
		public String getUpdate_person() {
			return update_person;
		}
		public void setUpdate_person(String update_person) {
			this.update_person = update_person;
		}
		/**支付状态的变化时间**/
		private String pay_time;
		/**支付状态00等待支付01支付成功02支付失效03部分退款04全部退款05支付结算完成**/
		private String pay_state;
		public String getPay_time() {
			return pay_time;
		}
		public void setPay_time(String pay_time) {
			this.pay_time = pay_time;
		}
		/**支付状态00等待支付01支付成功02支付失效03部分退款04全部退款05支付结算完成06转收入**/
		public String getPay_state() {
			return pay_state;
		}
		/**支付状态00等待支付01支付成功02支付失效03部分退款04全部退款05支付结算完成06转收入**/
		public void setPay_state(String pay_state) {
			this.pay_state = pay_state;
		}
		public String getCust_no() {
			return cust_no;
		}
		public void setCust_no(String cust_no) {
			this.cust_no = cust_no;
		}
		public String getMember_type() {
			return member_type;
		}
		public void setMember_type(String member_type) {
			this.member_type = member_type;
		}
		public String getLimit_time() {
			return limit_time;
		}
		public void setLimit_time(String limit_time) {
			this.limit_time = limit_time;
		}
		public String getCreate_time() {
			return create_time;
		}
		public void setCreate_time(String create_time) {
			this.create_time = create_time;
		}
		public String getUpdate_time() {
			return update_time;
		}
		public void setUpdate_time(String update_time) {
			this.update_time = update_time;
		}
		public String getUser_acct() {
			return user_acct;
		}
		public void setUser_acct(String user_acct) {
			this.user_acct = user_acct;
		}
		public String getUser_email() {
			return user_email;
		}
		public void setUser_email(String user_email) {
			this.user_email = user_email;
		}
		public String getUser_name() {
			return user_name;
		}
		public void setUser_name(String user_name) {
			this.user_name = user_name;
		}
		public String getUser_mobile() {
			return user_mobile;
		}
		public void setUser_mobile(String user_mobile) {
			this.user_mobile = user_mobile;
		}
		
		//日志编号
		private String logid;

		public String getLogid() {
			return logid;
		}
		public void setLogid(String logid) {
			this.logid = logid;
		}
		
		

		//alipay私钥，公钥
		
}
