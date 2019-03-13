package com.lmxf.post.core.utils.pay;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.lmxf.post.core.utils.CacheUtils;
import com.lmxf.post.core.utils.CheckUtils;
import com.lmxf.post.core.utils.Constants;
import com.lmxf.post.core.utils.CxfClient;
import com.lmxf.post.core.utils.DateUtils;
import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.GParameter;
import com.lmxf.post.core.utils.SpringContextUtil;
import com.lmxf.post.core.utils.TwoDimensionCode;
import com.lmxf.post.entity.order.OrderApply;
import com.lmxf.post.entity.parameter.Dict;
import com.lmxf.post.entity.pay.Order;
import com.lmxf.post.entity.pay.PayconfigAlipay;
import com.lmxf.post.entity.pay.PayconfigWechat;
import com.lmxf.post.repository.order.OrderApplyDao;
import com.lmxf.post.repository.pay.PayconfigAlipayDao;
import com.lmxf.post.repository.pay.PayconfigWechatDao;
/**
 * 2018.12.17
 * @author zzw/思杰
 *
 */
public class PayUtils{
	private final static Log log = LogFactory.getLog(PayUtils.class);
	private static String pngPath=null;

	public static String tradeHand(OrderApply orderApply) throws Exception {
		if (null == orderApply) {
			return errorInfo(GConstent.Xml_No_Field_bcode+"","请求数据为空");
		}
		Order vo =new Order();
		vo.setTotal_fee(orderApply.getPayPrice() + "");
		vo.setOpenid(orderApply.getOpenId());
		vo.setOrder_id(orderApply.getOrderId());
		String msg = orderApply.getPayMethod();
		//微信扫码支付
		if (GParameter.payType_weChat.equals(msg)) {
			vo.setAttach("ilikeXingdu"+orderApply.getCorpId()); // 附加数据 128自己一下 原样返回
			PayconfigWechatDao payconfigWechatDao = (PayconfigWechatDao)SpringContextUtil.getBean("payconfigWechatDao");
			PayconfigWechat payconfigWechat = payconfigWechatDao.findOneByCorpId(orderApply.getCorpId());
			if(payconfigWechat == null){
				return errorInfo(GConstent.Payconfig_Wechat_No_Exist+"","Payconfig_Wechat_No_Exist");
			}
			msg = getPackage(vo,payconfigWechat);				
			if(null==msg){
				return errorInfo(GConstent.Partner_Invalid_corp+"","corp not find or corpinfo error");
			}
			log.debug(vo.getOut_trade_no() + "native_package:" + msg);
			//String xml=new String(msg.getBytes("UTF-8"));
			//msg = PrintUtils.sendPost("https://api.mch.weixin.qq.com/pay/unifiedorder", msg);
			msg= PrintUtils.requestOnce("https://api.mch.weixin.qq.com/pay/unifiedorder",msg);
			getResult(msg, vo);
			if("".equals(vo.getResult_code())||"FAIL".equals(vo.getReturn_code())){
				log.error("微信支付响应错误Xml:"+msg);
				return errorInfo(GConstent.Create_Order_fail+"","create wx order error "+vo.getReturn_msg());
			}
			// 保存二维码图片
			CxfClient cxfClient=new CxfClient();
			cxfClient.getClientInfo(payconfigWechat, null);
			saveImage(vo,payconfigWechat.getContextPath());
			vo.setQrcodeimg_url(payconfigWechat.getPath() + "/qrcode/"+ vo.getOut_trade_no() + ".png");
			vo.setCreate_time(DateUtils.DateToString());
			vo.setPay_state("00");
			vo.setProduct_typeid("00");
			vo.setProduct_typename("待支付");
			vo.setPay_time(vo.getCreate_time());
			msg = PreCreateJson(vo, null);
			if ("01".equals(vo.getBcode()) && null != msg&& !"".equals(msg)) {
				msg = msg.replace("&amp;", "&");
			}
			vo.setFee(vo.getTotal_fee() + "");
			return msg;
		}
		//微信扫码公众号支付
		else if (GParameter.payType_weChatG.equals(msg)) {
			log.debug("进入微信公众号支付程序");
			vo.setAttach("xingdurongzhenghappy"+vo.getCorp_id()); // 附加数据 128自己一下 原样返回
			PayconfigWechatDao payconfigWechatDao = (PayconfigWechatDao)SpringContextUtil.getBean("payconfigWechatDao");
			PayconfigWechat payconfigWechat = payconfigWechatDao.findOneByCorpId(orderApply.getCorpId());
			if(payconfigWechat == null){
				return errorInfo(GConstent.Payconfig_Wechat_No_Exist+"","Payconfig_Wechat_No_Exist");
			}
			msg = getJSAPIpackage(vo,payconfigWechat);
			log.debug(vo.getOut_trade_no() + "jsapi_package:" + msg);
			if(null==msg){
				return errorInfo(GConstent.Partner_Invalid_corp+"","corp not find or corpinfo error");
			}
			log.debug("请求微信支付链接:"+"https://api.mch.weixin.qq.com/pay/unifiedorder"+" data:"+msg);
			msg= PrintUtils.requestOnce("https://api.mch.weixin.qq.com/pay/unifiedorder",msg);
			log.debug("请求微信支付链接响应结果:"+"https://api.mch.weixin.qq.com/pay/unifiedorder"+" RData:"+msg);
			getResult(msg, vo);
			if("".equals(vo.getResult_code())||"FAIL".equals(vo.getReturn_code())){
				log.error("微信支付响应错误Xml:"+msg);
				return errorInfo(GConstent.Create_Order_fail+"","create wx order error "+vo.getReturn_msg());
			}
			
			vo.setPay_state("00");
			vo.setCreate_time(DateUtils.DateToString());
			vo.setPay_time(vo.getCreate_time());
			msg = WeixinPayApplyResp.WxPayXml(vo);				
			return msg;
		} 
		//支付宝扫支付
		else if (GParameter.payType_Alipay.equals(msg)) {
			PayconfigAlipayDao payconfigAlipayDao = (PayconfigAlipayDao)SpringContextUtil.getBean("payconfigAlipayDao");
			PayconfigAlipay payconfigAlipay = payconfigAlipayDao.findOneByCorpId(orderApply.getCorpId());
			if(payconfigAlipay == null){
				return errorInfo(GConstent.Payconfig_Alipay_No_Exist+"","Payconfig_Alipay_No_Exist");
			}
			msg =setAppInfoToOrder(vo,payconfigAlipay);
			if(null!=msg){
				return errorInfo(GConstent.Partner_Invalid_corp+"",msg);
			}
			msg = createPre(vo,payconfigAlipay);//   https://mobilecodec.alipay.com/show.htm?code=gdxox0xowzsqp1vbeb
			if(null==msg){
				log.error("支付宝支付响应错误Json:"+msg);
				return errorInfo(GConstent.Trade_Code_No_Define+"","申请支付宝失败"); 
			}
			vo.setCode_url(msg);
			CxfClient cxfClient=new CxfClient();
			cxfClient.getClientInfo(null, payconfigAlipay);
			vo.setQrcodeimg_url(payconfigAlipay.getPath()+ "/qrcode/"+ vo.getOut_trade_no() + ".png");
			DownUtil.download(msg, vo.getOut_trade_no() + ".png",payconfigAlipay.getContextPath(),pngPath);
			vo.setPay_url(msg);
			vo.setPay_state("00");
			vo.setCreate_time(DateUtils.DateToString());
			vo.setPay_time(vo.getCreate_time());
			vo.setFee(vo.getTotal_fee() + "");
			msg = PreCreateJson(vo, null);
			return msg;
		}
		return msg;
	}
	
	public static String tradeRepeatHand(OrderApply orderApply) throws Exception {
		if (null == orderApply) {
			return errorInfo(GConstent.Xml_No_Field_bcode+"","请求数据为空");
		}
		Order vo =new Order();
		vo.setTotal_fee(orderApply.getPayPrice() + "");
		vo.setOpenid(orderApply.getOpenId());
		vo.setOrder_id(orderApply.getOrderId());
		String msg = orderApply.getPayMethod();
		//微信扫码支付
		if (GParameter.payType_weChat.equals(msg)) {
			vo.setAttach("ilikeXingdu"+orderApply.getCorpId()); // 附加数据 128自己一下 原样返回
			PayconfigWechatDao payconfigWechatDao = (PayconfigWechatDao)SpringContextUtil.getBean("payconfigWechatDao");
			PayconfigWechat payconfigWechat = payconfigWechatDao.findOneByCorpId(orderApply.getCorpId());
			if(payconfigWechat == null){
				return errorInfo(GConstent.Payconfig_Wechat_No_Exist+"","Payconfig_Wechat_No_Exist");
			}
			msg = getPackage(vo,payconfigWechat);				
			if(null==msg){
				return errorInfo(GConstent.Partner_Invalid_corp+"","corp not find or corpinfo error");
			}
			log.debug(vo.getOut_trade_no() + "native_package:" + msg);
			//String xml=new String(msg.getBytes("UTF-8"));
			//msg = PrintUtils.sendPost("https://api.mch.weixin.qq.com/pay/unifiedorder", msg);
			msg= PrintUtils.requestOnce("https://api.mch.weixin.qq.com/pay/unifiedorder",msg);
			getResult(msg, vo);
			if("".equals(vo.getResult_code())||"FAIL".equals(vo.getReturn_code())){
				return errorInfo(GConstent.Create_Order_fail+"","create wx order error "+vo.getReturn_msg());
			}
			// 保存二维码图片
			CxfClient cxfClient=new CxfClient();
			cxfClient.getClientInfo(payconfigWechat, null);
			saveImage(vo,payconfigWechat.getContextPath());
			vo.setQrcodeimg_url(payconfigWechat.getPath() + "/qrcode/"+ vo.getOut_trade_no() + ".png");
			vo.setCreate_time(DateUtils.DateToString());
			vo.setPay_state("00");
			vo.setProduct_typeid("00");
			vo.setProduct_typename("待支付");
			vo.setPay_time(vo.getCreate_time());
			msg = PreCreateJson(vo, null);
			if ("01".equals(vo.getBcode()) && null != msg&& !"".equals(msg)) {
				msg = msg.replace("&amp;", "&");
			}
			vo.setFee(vo.getTotal_fee() + "");
			return msg;
		}
		//微信扫码公众号支付
		else if (GParameter.payType_weChatG.equals(msg)) {
			vo.setAttach("xingdurongzhenghappy"+vo.getCorp_id()); // 附加数据 128自己一下 原样返回
			PayconfigWechatDao payconfigWechatDao = (PayconfigWechatDao)SpringContextUtil.getBean("payconfigWechatDao");
			PayconfigWechat payconfigWechat = payconfigWechatDao.findOneByCorpId(orderApply.getCorpId());
			if(payconfigWechat == null){
				return errorInfo(GConstent.Payconfig_Wechat_No_Exist+"","Payconfig_Wechat_No_Exist");
			}
			msg = getJSAPIRepeatpackage(vo,payconfigWechat,orderApply);
			log.debug(vo.getOut_trade_no() + "jsapi_package:" + msg);
			if(null==msg){
				return errorInfo(GConstent.Partner_Invalid_corp+"","corp not find or corpinfo error");
			}
			msg= PrintUtils.requestOnce("https://api.mch.weixin.qq.com/pay/unifiedorder",msg);
			getResult(msg, vo);
			if("".equals(vo.getResult_code())||"FAIL".equals(vo.getReturn_code())){
				return errorInfo(GConstent.Create_Order_fail+"","create wx order error "+vo.getReturn_msg());
			}
			
			vo.setPay_state("00");
			vo.setCreate_time(DateUtils.DateToString());
			vo.setPay_time(vo.getCreate_time());
			msg = WeixinPayApplyResp.WxPayXml(vo);				
			return msg;
		} 
		//支付宝扫支付
		else if (GParameter.payType_Alipay.equals(msg)) {
			PayconfigAlipayDao payconfigAlipayDao = (PayconfigAlipayDao)SpringContextUtil.getBean("payconfigAlipayDao");
			PayconfigAlipay payconfigAlipay = payconfigAlipayDao.findOneByCorpId(orderApply.getCorpId());
			if(payconfigAlipay == null){
				return errorInfo(GConstent.Payconfig_Alipay_No_Exist+"","Payconfig_Alipay_No_Exist");
			}
			msg =setAppInfoToOrder(vo,payconfigAlipay);
			if(null!=msg){
				return errorInfo(GConstent.Partner_Invalid_corp+"",msg);
			}
			msg = createPre(vo,payconfigAlipay);//   https://mobilecodec.alipay.com/show.htm?code=gdxox0xowzsqp1vbeb
			if(null==msg){
				log.error("apply alipay error" + vo.getOrder_id());
				return errorInfo(GConstent.Trade_Code_No_Define+"","申请支付宝失败"); 
			}
			vo.setCode_url(msg);
			CxfClient cxfClient=new CxfClient();
			cxfClient.getClientInfo(null, payconfigAlipay);
			vo.setQrcodeimg_url(payconfigAlipay.getPath()+ "/qrcode/"+ vo.getOut_trade_no() + ".png");
			DownUtil.download(msg, vo.getOut_trade_no() + ".png",payconfigAlipay.getContextPath(),pngPath);
			vo.setPay_url(msg);
			vo.setPay_state("00");
			vo.setCreate_time(DateUtils.DateToString());
			vo.setPay_time(vo.getCreate_time());
			vo.setFee(vo.getTotal_fee() + "");
			msg = PreCreateJson(vo, null);
			return msg;
		}
		return msg;
	}
	
	
	/**
	 * 应用服务器、终端向支付平台申请退款信息
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public static String refundPayResult(OrderApply orderApply) throws Exception {
		String msg = "";
		Order vo =new Order();
		if("1".equals(orderApply.getPayType())){
			vo.setPay_type("00");
		}else if("2".equals(orderApply.getPayType()) && "4".equals(orderApply.getSaleChannel())){
			vo.setPay_type("01");
		}else if("2".equals(orderApply.getPayType()) && "1".equals(orderApply.getSaleChannel())){
			vo.setPay_type("02");
		}
		if("1".equals(orderApply.getPayState())){
			vo.setPay_state("00");
		}else if("2".equals(orderApply.getPayState())){
			vo.setPay_state("01");
		}else if("3".equals(orderApply.getPayState())){
			vo.setPay_state("02");
		}
		vo.setCorp_id(orderApply.getCorpId());
		vo.setOut_trade_no(orderApply.getpTradeNo());
		int refundFee=(int) (orderApply.getReturnMoney() * 100);
		String payType=vo.getPay_type();
		int totfee=(int) (orderApply.getPayPrice() * 100);
		if(refundFee>totfee){
			msg="退款金额不能大于付款金额";
		}
		vo.setTotal_fee(orderApply.getPayPrice() * 100 + "");
		vo.setFee(orderApply.getReturnMoney() * 100 + "");
		OrderApplyDao orderApplyDao = (OrderApplyDao)SpringContextUtil.getBean("orderApplyDao");
		///////////////////////////////验证第三方的订单的支付状态//////////////////////////////////////////////////////////////////////////
		String dbState=vo.getPay_state();//数据库保存的数据库状态
		payType=vo.getPay_type();
		// 查看是否已支付，01为已支付00申请支付状态02失效
		if ("01".equals(vo.getPay_state())||"00".equals(vo.getPay_state())||"03".equals(vo.getPay_state())) {
			if ("00".equals(payType)) {
				PayconfigAlipayDao payconfigAlipayDao = (PayconfigAlipayDao)SpringContextUtil.getBean("payconfigAlipayDao");
				PayconfigAlipay payconfigAlipay = payconfigAlipayDao.findOneByCorpId(orderApply.getCorpId());
				if(payconfigAlipay == null){
					return errorInfo(GConstent.Payconfig_Alipay_No_Exist+"","Payconfig_Alipay_No_Exist");
				}
				searchAliPlat(vo,payconfigAlipay);
			} else if ("01".equals(payType)||"02".equals(payType)) {
				PayconfigWechatDao payconfigWechatDao = (PayconfigWechatDao)SpringContextUtil.getBean("payconfigWechatDao");
				PayconfigWechat payconfigWechat = payconfigWechatDao.findOneByCorpId(orderApply.getCorpId());
				if(payconfigWechat == null){
					return errorInfo(GConstent.Payconfig_Wechat_No_Exist+"","Payconfig_Wechat_No_Exist");
				}
				searchWXPlat(vo);
			}
		}
		try {
			if (null!=dbState&&!dbState.equals(vo.getPay_state())) {
				if("00".equals(vo.getPay_state())){
					orderApply.setPayState("1");
				}else if("01".equals(vo.getPay_state())){
					orderApply.setPayState("2");
				}else if("02".equals(vo.getPay_state())){
					orderApply.setPayState("3");
				}
				orderApplyDao.updateByNotify(orderApply);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// 查看是否已支付，01为已支付00申请支付状态02失效
		if (!"01".equals(vo.getPay_state())&&!"03".equals(vo.getPay_state())) {
			msg="不是支付状态或部分退款状态，不能退款！";
		}
		boolean ck=false;
		vo.setFee(refundFee+"");
		if ("00".equals(payType)) {
			PayconfigAlipayDao payconfigAlipayDao = (PayconfigAlipayDao)SpringContextUtil.getBean("payconfigAlipayDao");
			PayconfigAlipay payconfigAlipay = payconfigAlipayDao.findOneByCorpId(orderApply.getCorpId());
			if(payconfigAlipay == null){
				return errorInfo(GConstent.Payconfig_Alipay_No_Exist+"","Payconfig_Alipay_No_Exist");
			}
			msg=reFundAliPlat(vo,payconfigAlipay);
			ck=true;
		} else if ("01".equals(payType)) {
//			ClientCustomSSL test=new ClientCustomSSL(vo); 	
//			msg=test.connect();
	    	ck=true;
		} else if ("02".equals(payType)) {
//			ClientCustomSSL test=new ClientCustomSSL(vo); 	
//			msg=test.connect();
			log.debug("退款结果:"+msg);
	    	ck=true;
		}
		try {
			if(ck){
				if(null!=msg&&(msg.startsWith("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]>")||"0000".equals(msg))){
				//<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg><appid><![CDATA[wx176de7a3403494ed]]></appid><mch_id><![CDATA[1371982002]]></mch_id><nonce_str><![CDATA[7LOSIzjew7lVLQXM]]></nonce_str><sign><![CDATA[A5F41202F4785751BFA3320B0EBD0D58]]></sign><result_code><![CDATA[SUCCESS]]></result_code><transaction_id><![CDATA[4200000073201801228658110259]]></transaction_id><out_trade_no><![CDATA[zlWx16304125470387998]]></out_trade_no><out_refund_no><![CDATA[zw9756929798577]]></out_refund_no><refund_id><![CDATA[50000305612018012303260753913]]></refund_id><refund_channel><![CDATA[]]></refund_channel><refund_fee>1</refund_fee><coupon_refund_fee>0</coupon_refund_fee><total_fee>1</total_fee><cash_fee>1</cash_fee><coupon_refund_count>0</coupon_refund_count><cash_refund_fee>1</cash_refund_fee></xml>	
					msg="退款成功";
					if(refundFee<totfee){
						 vo.setPay_state("03");
					}else{
						 vo.setPay_state("04");
					}
					vo.setProduct_typeid("02");
					vo.setProduct_typename("退款或失效");
					orderApply.setReturnType(GParameter.returnType_wait);;
					orderApplyDao.update(orderApply);
				}else{
					msg="退款失败";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		QrcodePayApplyResp resp = new QrcodePayApplyResp();
		resp.setOrder(vo);
		resp.setRetcode((ck&&"退款成功".equals(msg))?Constants.SUCCESS_CODE:"7777");
		resp.setRetmsg(msg);
		return resp.CreateXml();
	}
	
	private static String reFundAliPlat(Order ro,PayconfigAlipay corpVo) throws AlipayApiException {
		if(null==ro||null==corpVo){
			return null;
		} 
		AlipayClient client =new DefaultAlipayClient(corpVo.getAlipayApi(), corpVo.getPid(), corpVo.getPrivKey(), "json", "GBK", corpVo.getPublKey(), corpVo.getSignType()); //获得初始化的AlipayClient
		
		AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
		request.setBizContent("{" +
		"\"out_trade_no\":\"" + ro.getOut_trade_no()+ "\"," +
		"\"trade_no\":\"\"," +
		"\"refund_amount\":"+ro.getFee()+"," +
		"\"refund_reason\":\"正常退款\"," +
		"\"out_request_no\":\"ZW_"+System.nanoTime()+"\"," +
		"\"operator_id\":\"ZW001OP\"," +
		"\"store_id\":\"SZ_ZW_001\"," +
		"\"terminal_id\":\"SZ_ZW_1001\"" +
		"  }");
		AlipayTradeRefundResponse response = client.execute(request);
		if(response.isSuccess()){
			log.debug("支付宝退款成功:"+response.getBody());
			//{"alipay_trade_refund_response":{"code":"10000","msg":"Success","buyer_logon_id":"152****0715","buyer_user_id":"2088902981908310","fund_change":"Y","gmt_refund_pay":"2018-01-24 15:01:52","out_trade_no":"16389900508981170","refund_detail_item_list":[{"amount":"0.01","fund_channel":"ALIPAYACCOUNT"}],"refund_fee":"0.01","send_back_fee":"0.01","trade_no":"2018012321001004310251362658"},"sign":"gUvDDAjTtcDahc1KCFZVvFJUkRILW2XuQFShGhrKs/363d0j5C8LbEF66w8APUywsDognhpvntdy/4jxPwv5yWwVbutfUTdYVLLpv5zVTYpSMnrz4hGQej3mkNawC0EBlUqH1RHlEPP8o0GEE0eQkrec4/AIdT7jLL3cNnmaXFI="}
			return "0000";
		}
		log.debug("支付宝退款调用失败:"+response.getBody());
		return null;
	}

	/**
	 * 应用服务器、终端向支付平台查询订单位支付信息
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public static String queryPayResult(OrderApply orderApply) throws Exception {
		Order vo =new Order();
		vo.setOut_trade_no(orderApply.getpTradeNo());
		vo.setTotal_fee(orderApply.getPayPrice() + "");
		if("1".equals(orderApply.getPayType())){
			vo.setPay_type("00");
		}else if("2".equals(orderApply.getPayType()) && "4".equals(orderApply.getSaleChannel())){
			vo.setPay_type("01");
		}else if("2".equals(orderApply.getPayType()) && "1".equals(orderApply.getSaleChannel())){
			vo.setPay_type("02");
		}
		if("1".equals(orderApply.getPayState())){
			vo.setPay_state("00");
		}else if("2".equals(orderApply.getPayState())){
			vo.setPay_state("01");
		}else if("3".equals(orderApply.getPayState())){
			vo.setPay_state("02");
		}
		vo.setCorp_id(orderApply.getCorpId());
		String msg = null;
		PayconfigAlipayDao payconfigAlipayDao = (PayconfigAlipayDao)SpringContextUtil.getBean("payconfigAlipayDao");
		int flag = 0;
		// 查看是否已支付，2为已支付1申请支付状态3失效
		if ("01".equals(vo.getPay_state())) {
			msg = "已支付";
			if ("00".equals(vo.getPay_type())) {
				PayconfigAlipay payconfigAlipay = payconfigAlipayDao.findOneByCorpId(orderApply.getCorpId());
				if(payconfigAlipay == null){
					return errorInfo(GConstent.Payconfig_Alipay_No_Exist+"","Payconfig_Alipay_No_Exist");
				}
				searchAliPlat(vo,payconfigAlipay);
			} else if ("01".equals(vo.getPay_type())) {
				searchWXPlat(vo);
			}
			if ("04".equals(vo.getPay_state())) {
				msg = "已退款";
				flag = 1;
			}
		} else if ("00".equals(vo.getPay_state())) {
			msg=null;
			if ("00".equals(vo.getPay_type())) {
				PayconfigAlipay payconfigAlipay = payconfigAlipayDao.findOneByCorpId(orderApply.getCorpId());
				if(payconfigAlipay == null){
					return errorInfo(GConstent.Payconfig_Alipay_No_Exist+"","Payconfig_Alipay_No_Exist");
				}
				searchAliPlat(vo,payconfigAlipay);
			} else if ("01".equals(vo.getPay_type())||"02".equals(vo.getPay_type())) {
				searchWXPlat(vo);
			}
			msg ="未支付";
			
			if ("01".equals(vo.getPay_state())) {
				msg = "已支付";
				flag = 1;
			} else if ("02".equals(vo.getPay_state())) {
				msg = "订单已关闭或退款";
				flag = 1;
			}
		}
		
		if(flag == 0&&"02".equals(vo.getPay_type())){
		     return WxOrderPayXml(vo);
		}

		QrcodePayApplyResp resp = new QrcodePayApplyResp();
		resp.setOrder(vo);
		resp.setRetcode(Constants.SUCCESS_code);
		resp.setRetmsg(msg);
		return resp.CreateXml();
	}
	
	private static String getPackage(Order order,PayconfigWechat vo) throws SDKRuntimeException {
		// 先设置基本信息
		if(null==vo||null==order){
			return null;
		}		
		int minute=30;
		try {
			Dict dict=(Dict) CacheUtils.get(CacheUtils.dictCache,"shop_limitTime");
			minute=Integer.parseInt(dict.getDictValue());
			if(minute<5||minute>60*24*2){
				minute=30;
			}
		} catch (Exception e) {
			minute=30;
		}
		order.setAppid(vo.getAppId());
		order.setAppKey(vo.getAppSecret());
		order.setBody(vo.getBody());// zhilai智莱 商品描述winXin
		order.setMch_id(vo.getContactCode());
		order.setDevice_info(vo.getCorpId());
		order.setDetail(null);
		order.setNonce_str(Order.CreateNoncestr());
		order.setNotify_url(vo.getAuthBack());
		order.setOut_trade_no(vo.getCorpId() + System.nanoTime());
		order.setSpbill_create_ip(vo.getSpbillCreateIp());// 指用户浏览器端IP，丌是商户服务器IP，格式IPV4；
		//order.setFee_type(vo.getFee_type());// 符合ISO 4217标准的三位字母代码，默认人民币：CNY
		order.setTrade_type(vo.getTradeType());
		if (null == order.getLimit_time() || "".equals(order.getLimit_time())) {
			order.setLimit_time(DateUtils.addMinute(minute));
		}
		// 先清空签名
		order.SetParameter("sign", null);//签名 sign 是 String(32) C380BEC2BFD727A4B6845133519F3AD6 通过签名算法计算得出的签名值，详见签名生成算法 
		// 设置请求package信息
		order.SetParameter("appid", order.getAppid());//公众账号ID appid 是 String(32) wxd678efh567hg6787 微信支付分配的公众账号ID（企业号corpid即为此appId） 
		order.SetParameter("attach", order.getAttach());//附加数据 attach 否 String(127) 深圳分店 附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用。
		order.SetParameter("body", order.getBody());//商品描述 body 是 String(128) 腾讯充值中心-QQ会员充值 商品简单描述，该字段请按照规范传递，具体请见参数规定
		order.SetParameter("mch_id", order.getMch_id());//商户号 mch_id 是 String(32) 1230000109 微信支付分配的商户号 
		order.SetParameter("nonce_str",order.getNonce_str());//随机字符串 nonce_str 是 String(32) 5K8264ILTKCH16CQ2502SI8ZNMTM67VS 随机字符串，长度要求在32位以内。推荐随机数生成算法 
		order.SetParameter("notify_url",order.getNotify_url());//"http://112.124.21.88:8081/payplat/wxNotify.action" 通知地址 notify_url 是 String(256) http://www.weixin.qq.com/wxpay/pay.php 异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。 
		order.SetParameter("out_trade_no", order.getOut_trade_no());//商户订单号 out_trade_no 是 String(32) 20150806125346 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。详见商户订单号 
		order.SetParameter("spbill_create_ip", order.getSpbill_create_ip());//终端IP spbill_create_ip 是 String(16) 123.12.12.123 APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。 
		order.SetParameter("total_fee", order.getTotal_fee() + "");//标价金额 total_fee 是 Int 88 订单总金额，单位为分，详见支付金额 
		order.SetParameter("trade_type", order.getTrade_type());//交易类型 trade_type 是 String(16) JSAPI 取值如下：JSAPI，NATIVE，APP等，说明详见参数规定 
		order.SetParameter("device_info", "20171130");//设备号 device_info 否 String(32) 013467007045764 自定义参数，可以为终端设备号(门店号或收银设备ID)，PC网页或公众号内支付可以传"WEB" 
		//order.SetParameter("sign_type", "HMAC-SHA256");//签名类型 sign_type 否 String(32) MD5 签名类型，默认为MD5，支持HMAC-SHA256和MD5。
		//order.SetParameter("detail", "test");//商品详情 detail 否 String(6000)   商品详细描述，对于使用单品优惠的商户，改字段必须按照规范上传，详见“单品优惠参数说明”
		//order.SetParameter("fee_type", "CNY");//标价币种 fee_type 否 String(16) CNY 符合ISO 4217标准的三位字母代码，默认人民币：CNY，详细列表请参见货币类型 
		order.SetParameter("time_start", DateUtils.DateNumberToString());//交易起始时间 time_start 否 String(14) 20091225091010 订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则 
		order.SetParameter("time_expire", DateUtils.DateNumberToString(minute));//交易结束时间 time_expire 否 String(14) 20091227091010 订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。其他详见时间规则 注意：最短失效时间间隔必须大于5分钟 
		//order.SetParameter("goods_tag", "WXG");//订单优惠标记 goods_tag 否 String(32) WXG 订单优惠标记，使用代金券或立减优惠功能时需要的参数，说明详见代金券或立减优惠 
		order.SetParameter("product_id", "test112233");//商品ID product_id 否 String(32) 12235413214070356458058 trade_type=NATIVE时（即扫码支付），此参数必传。此参数为二维码中包含的商品ID，商户自行定义。 
		//order.SetParameter("limit_pay", "no_credit");//指定支付方式 limit_pay 否 String(32) no_credit 上传此参数no_credit--可限制用户不能使用信用卡支付 
		//order.SetParameter("openid", "oUpF8uMuAJO_M2pxb1Q9zNjWeS6o");//用户标识 openid 否 String(128) oUpF8uMuAJO_M2pxb1Q9zNjWeS6o trade_type=JSAPI时（即公众号支付），此参数必传，此参数为微信用户在商户对应appid下的唯一标识。openid如何获取，可参考【获取openid】。企业号请使用【企业号OAuth2.0接口】获取企业号内成员userid，再调用【企业号userid转openid接口】进行转换 
		//order.SetParameter("sub_appid", order.getAppid());//子商户公众账号ID	sub_appid	否	String(32)	wx8888888888888888	微信分配的子商户公众账号ID，如需在支付完成后获取sub_openid则此参数必传。
		if(null!=vo.getSubMchId()&&!"".equals(vo.getSubMchId().trim())&&!order.getMch_id().equals(vo.getSubMchId().trim())){
			order.SetParameter("sub_mch_id", vo.getSubMchId().trim());//"1320645901");//子商户号	sub_mch_id	是	String(32)	1900000109	微信支付分配的子商户号
		}
		return order.CreateNativePackage();
	}
	
	private static String getJSAPIpackage(Order order,PayconfigWechat vo) throws SDKRuntimeException {		
		if(null==order||null==vo){ 
			return null;
		}
		int minute=30;
		try {
			Dict dict=(Dict) CacheUtils.get(CacheUtils.dictCache,"shop_limitTime");
			minute=Integer.parseInt(dict.getDictValue());
			if(minute<5||minute>60*24*2){
				minute=30;
			}
		} catch (Exception e) {
			minute=30;
		}
		order.setAppid(vo.getAppId());
		order.setAppKey(vo.getAppSecret());
		order.setBody(vo.getBody());// zhilai智莱 商品描述winXin
		order.setMch_id(vo.getContactCode());
		order.setDevice_info(vo.getDeviceInfo());
		order.setDetail(null);
		order.setNonce_str(Order.CreateNoncestr());
		order.setNotify_url(vo.getAuthBack());
		order.setOut_trade_no(vo.getCorpId() + System.nanoTime());
		order.setSpbill_create_ip(vo.getSpbillCreateIp());// 指用户浏览器端IP，丌是商户服务器IP，格式IPV4；
		order.setCurrency_type(vo.getFeeType());
		//order.setFee_type(vo.getFee_type());// 符合ISO 4217标准的三位字母代码，默认人民币：CNY
		order.setTrade_type(vo.getTradeType());
		if (null == order.getLimit_time() || "".equals(order.getLimit_time())) {
			order.setLimit_time(DateUtils.addMinute(minute));
		}
		// 先清空签名
		order.SetParameter("sign", null);//签名 sign 是 String(32) C380BEC2BFD727A4B6845133519F3AD6 通过签名算法计算得出的签名值，详见签名生成算法 
		// 设置请求package信息
		order.SetParameter("appid", order.getAppid());//公众账号ID appid 是 String(32) wxd678efh567hg6787 微信支付分配的公众账号ID（企业号corpid即为此appId） 
		order.SetParameter("attach", order.getAttach());//附加数据 attach 否 String(127) 深圳分店 附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用。
		order.SetParameter("body", order.getBody());//商品描述 body 是 String(128) 腾讯充值中心-QQ会员充值 商品简单描述，该字段请按照规范传递，具体请见参数规定
		order.SetParameter("mch_id", order.getMch_id());//商户号 mch_id 是 String(32) 1230000109 微信支付分配的商户号 
		order.SetParameter("nonce_str",order.getNonce_str());//随机字符串 nonce_str 是 String(32) 5K8264ILTKCH16CQ2502SI8ZNMTM67VS 随机字符串，长度要求在32位以内。推荐随机数生成算法 
		order.SetParameter("notify_url",order.getNotify_url());//"http://112.124.21.88:8081/payplat/wxNotify.action" 通知地址 notify_url 是 String(256) http://www.weixin.qq.com/wxpay/pay.php 异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。 
		order.SetParameter("out_trade_no", order.getOut_trade_no());//商户订单号 out_trade_no 是 String(32) 20150806125346 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。详见商户订单号 
		order.SetParameter("spbill_create_ip", order.getSpbill_create_ip());//终端IP spbill_create_ip 是 String(16) 123.12.12.123 APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。 
		order.SetParameter("total_fee", order.getTotal_fee() + "");//标价金额 total_fee 是 Int 88 订单总金额，单位为分，详见支付金额 
		order.SetParameter("trade_type", "JSAPI");//交易类型 trade_type 是 String(16) JSAPI 取值如下：JSAPI，NATIVE，APP等，说明详见参数规定 
		order.SetParameter("device_info","WEB");//设备号 device_info 否 String(32) 013467007045764 自定义参数，可以为终端设备号(门店号或收银设备ID)，PC网页或公众号内支付可以传"WEB" 
		//order.SetParameter("sign_type", "HMAC-SHA256");//签名类型 sign_type 否 String(32) MD5 签名类型，默认为MD5，支持HMAC-SHA256和MD5。
		//order.SetParameter("detail", "test");//商品详情 detail 否 String(6000)   商品详细描述，对于使用单品优惠的商户，改字段必须按照规范上传，详见“单品优惠参数说明”
		//order.SetParameter("fee_type", "CNY");//标价币种 fee_type 否 String(16) CNY 符合ISO 4217标准的三位字母代码，默认人民币：CNY，详细列表请参见货币类型 
		order.SetParameter("time_start", DateUtils.DateNumberToString());//交易起始时间 time_start 否 String(14) 20091225091010 订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则 
		order.SetParameter("time_expire", DateUtils.DateNumberToString(minute));//交易结束时间 time_expire 否 String(14) 20091227091010 订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。其他详见时间规则 注意：最短失效时间间隔必须大于5分钟 
		//order.SetParameter("goods_tag", "WXG");//订单优惠标记 goods_tag 否 String(32) WXG 订单优惠标记，使用代金券或立减优惠功能时需要的参数，说明详见代金券或立减优惠 
		//order.SetParameter("product_id", "test112233");//商品ID product_id 否 String(32) 12235413214070356458058 trade_type=NATIVE时（即扫码支付），此参数必传。此参数为二维码中包含的商品ID，商户自行定义。 
		//order.SetParameter("limit_pay", "no_credit");//指定支付方式 limit_pay 否 String(32) no_credit 上传此参数no_credit--可限制用户不能使用信用卡支付 
		order.SetParameter("openid", order.getOpenid());//用户标识 openid 否 String(128) oUpF8uMuAJO_M2pxb1Q9zNjWeS6o trade_type=JSAPI时（即公众号支付），此参数必传，此参数为微信用户在商户对应appid下的唯一标识。openid如何获取，可参考【获取openid】。企业号请使用【企业号OAuth2.0接口】获取企业号内成员userid，再调用【企业号userid转openid接口】进行转换 
		//order.SetParameter("sub_appid", order.getAppid());//子商户公众账号ID	sub_appid	否	String(32)	wx8888888888888888	微信分配的子商户公众账号ID，如需在支付完成后获取sub_openid则此参数必传。
		if(null!=vo.getSubMchId()&&!"".equals(vo.getSubMchId().trim())&&!order.getMch_id().equals(vo.getSubMchId().trim())){
			order.SetParameter("sub_mch_id", vo.getSubMchId().trim());//"1320645901");//子商户号	sub_mch_id	是	String(32)	1900000109	微信支付分配的子商户号
		}
		return order.CreateNativePackage();
	}
	
	private static String getJSAPIRepeatpackage(Order order,PayconfigWechat vo,OrderApply orderApply) throws SDKRuntimeException {		
		if(null==order||null==vo){ 
			return null;
		}
		int minute=30;
		try {
			Dict dict=(Dict) CacheUtils.get(CacheUtils.dictCache,"shop_limitTime");
			minute=Integer.parseInt(dict.getDictValue());
			if(minute<5||minute>60*24*2){
				minute=30;
			}
		} catch (Exception e) {
			minute=30;
		}
		order.setAppid(vo.getAppId());
		order.setAppKey(vo.getAppSecret());
		order.setBody(vo.getBody());// zhilai智莱 商品描述winXin
		order.setMch_id(vo.getContactCode());
		order.setDevice_info(vo.getDeviceInfo());
		order.setDetail(null);
		order.setNonce_str(Order.CreateNoncestr());
		order.setNotify_url(vo.getAuthBack());
		order.setOut_trade_no(orderApply.getpTradeNo());
		order.setSpbill_create_ip(vo.getSpbillCreateIp());// 指用户浏览器端IP，丌是商户服务器IP，格式IPV4；
		order.setCurrency_type(vo.getFeeType());
		//order.setFee_type(vo.getFee_type());// 符合ISO 4217标准的三位字母代码，默认人民币：CNY
		order.setTrade_type(vo.getTradeType());
		if (null == order.getLimit_time() || "".equals(order.getLimit_time())) {
			order.setLimit_time(DateUtils.addMinute(minute));
		}
		// 先清空签名
		order.SetParameter("sign", null);//签名 sign 是 String(32) C380BEC2BFD727A4B6845133519F3AD6 通过签名算法计算得出的签名值，详见签名生成算法 
		// 设置请求package信息
		order.SetParameter("appid", order.getAppid());//公众账号ID appid 是 String(32) wxd678efh567hg6787 微信支付分配的公众账号ID（企业号corpid即为此appId） 
		order.SetParameter("attach", order.getAttach());//附加数据 attach 否 String(127) 深圳分店 附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用。
		order.SetParameter("body", order.getBody());//商品描述 body 是 String(128) 腾讯充值中心-QQ会员充值 商品简单描述，该字段请按照规范传递，具体请见参数规定
		order.SetParameter("mch_id", order.getMch_id());//商户号 mch_id 是 String(32) 1230000109 微信支付分配的商户号 
		order.SetParameter("nonce_str",order.getNonce_str());//随机字符串 nonce_str 是 String(32) 5K8264ILTKCH16CQ2502SI8ZNMTM67VS 随机字符串，长度要求在32位以内。推荐随机数生成算法 
		order.SetParameter("notify_url",order.getNotify_url());//"http://112.124.21.88:8081/payplat/wxNotify.action" 通知地址 notify_url 是 String(256) http://www.weixin.qq.com/wxpay/pay.php 异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。 
		order.SetParameter("out_trade_no", order.getOut_trade_no());//商户订单号 out_trade_no 是 String(32) 20150806125346 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。详见商户订单号 
		order.SetParameter("spbill_create_ip", order.getSpbill_create_ip());//终端IP spbill_create_ip 是 String(16) 123.12.12.123 APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。 
		order.SetParameter("total_fee", order.getTotal_fee() + "");//标价金额 total_fee 是 Int 88 订单总金额，单位为分，详见支付金额 
		order.SetParameter("trade_type", "JSAPI");//交易类型 trade_type 是 String(16) JSAPI 取值如下：JSAPI，NATIVE，APP等，说明详见参数规定 
		order.SetParameter("device_info","WEB");//设备号 device_info 否 String(32) 013467007045764 自定义参数，可以为终端设备号(门店号或收银设备ID)，PC网页或公众号内支付可以传"WEB" 
		//order.SetParameter("sign_type", "HMAC-SHA256");//签名类型 sign_type 否 String(32) MD5 签名类型，默认为MD5，支持HMAC-SHA256和MD5。
		//order.SetParameter("detail", "test");//商品详情 detail 否 String(6000)   商品详细描述，对于使用单品优惠的商户，改字段必须按照规范上传，详见“单品优惠参数说明”
		//order.SetParameter("fee_type", "CNY");//标价币种 fee_type 否 String(16) CNY 符合ISO 4217标准的三位字母代码，默认人民币：CNY，详细列表请参见货币类型 
		order.SetParameter("time_start", DateUtils.DateNumberToString());//交易起始时间 time_start 否 String(14) 20091225091010 订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则 
		order.SetParameter("time_expire", DateUtils.DateNumberToString(minute));//交易结束时间 time_expire 否 String(14) 20091227091010 订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。其他详见时间规则 注意：最短失效时间间隔必须大于5分钟 
		//order.SetParameter("goods_tag", "WXG");//订单优惠标记 goods_tag 否 String(32) WXG 订单优惠标记，使用代金券或立减优惠功能时需要的参数，说明详见代金券或立减优惠 
		//order.SetParameter("product_id", "test112233");//商品ID product_id 否 String(32) 12235413214070356458058 trade_type=NATIVE时（即扫码支付），此参数必传。此参数为二维码中包含的商品ID，商户自行定义。 
		//order.SetParameter("limit_pay", "no_credit");//指定支付方式 limit_pay 否 String(32) no_credit 上传此参数no_credit--可限制用户不能使用信用卡支付 
		order.SetParameter("openid", order.getOpenid());//用户标识 openid 否 String(128) oUpF8uMuAJO_M2pxb1Q9zNjWeS6o trade_type=JSAPI时（即公众号支付），此参数必传，此参数为微信用户在商户对应appid下的唯一标识。openid如何获取，可参考【获取openid】。企业号请使用【企业号OAuth2.0接口】获取企业号内成员userid，再调用【企业号userid转openid接口】进行转换 
		//order.SetParameter("sub_appid", order.getAppid());//子商户公众账号ID	sub_appid	否	String(32)	wx8888888888888888	微信分配的子商户公众账号ID，如需在支付完成后获取sub_openid则此参数必传。
		if(null!=vo.getSubMchId()&&!"".equals(vo.getSubMchId().trim())&&!order.getMch_id().equals(vo.getSubMchId().trim())){
			order.SetParameter("sub_mch_id", vo.getSubMchId().trim());//"1320645901");//子商户号	sub_mch_id	是	String(32)	1900000109	微信支付分配的子商户号
		}
		return order.CreateNativePackage();
	}
	
	private static String setAppInfoToOrder(Order ro,PayconfigAlipay corpVo) {
		if (null == ro) {
			return "Order is null";
		}
		if(null==corpVo){
			return "corp not find or corpinfo error";
		}
		ro.setAppid(corpVo.getPid());
		ro.setAppKey(corpVo.getKey());
		ro.setSign_type(corpVo.getSignType());
		// 商户订单号 商户网站订单系统中唯一订单号，必填
		ro.setOut_trade_no(null==corpVo.getCorpId()?"":corpVo.getCorpId() + System.nanoTime());
		
		if(null!=ro.getSite_id()&&!"".equals(ro.getSite_id())){
			//ro.setOut_trade_no(ro.getSite_id()+""+System.nanoTime());
			ro.setOut_trade_no(null==corpVo.getCorpId()?"zzw"+ro.getSite_id()+""+System.nanoTime():corpVo.getCorpId()+ro.getSite_id()+""+System.nanoTime());
		}else{
			ro.setOut_trade_no(null==corpVo.getCorpId()?"zzw0001"+System.nanoTime():corpVo.getCorpId()+"0001" + System.nanoTime());
			//ro.setOut_trade_no(RandomDigital.randomNum(32));
		}
		
		ro.setTrade_type(corpVo.getTradeType());
		ro.setBody(corpVo.getBody());
		ro.setNotify_url(corpVo.getAuthBack());//GParameter.aliQrcode_notify_url
		ro.setReturn_url(corpVo.getReturnNotice());//GParameter.qrcode_return_url
		// 商品展示地址需以http://开头的完整路径，例如：http://www.xxx.com/myorder.html
		if (null == ro.getLimit_time() || "".equals(ro.getLimit_time())||!DateUtils.isValidSimpleDate(ro.getLimit_time())) {
			ro.setLimit_time(DateUtils.addMinute(60));
		}
		return null;
	}
	
	private static String createPre(Order ro,PayconfigAlipay corpVo){
		if(null==ro||null==corpVo){
			return null;
		}
		AlipayClient alipayClient =null;// new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", APP_ID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2"); //获得初始化的AlipayClient
		AlipayTradePrecreateRequest request = null;//new AlipayTradePrecreateRequest();创建API对应的request类
		AlipayTradePrecreateResponse response =null;
		String cont=null;
		 try {
			 alipayClient = new DefaultAlipayClient(corpVo.getAlipayApi(), corpVo.getPid(), corpVo.getPrivKey(), "json", "GBK", corpVo.getPublKey(), corpVo.getSignType()); //获得初始化的AlipayClient
				
			 
			/*if(null==alipayClient){
				 return null;
			 }*/
			/*支付渠道	支付渠道代码
			余额	balance
			余额宝	moneyFund
			网银	bankPay
			借记卡快捷	debitCardExpress
			信用卡快捷	creditCardExpress
			信用卡卡通	creditCardCartoon
			信用卡	creditCard
			卡通	cartoon
			花呗	pcredit
			花呗分期	pcreditpayInstallment
			信用支付类型（包含 信用卡卡通，信用卡快捷,花呗，花呗分期）	credit_group
			红包	coupon
			积分	point
			优惠（包含实时优惠+商户优惠）	promotion
			营销券	voucher
			商户优惠	mdiscount
			亲密付	honeyPay
			商户预存卡	mcard
			个人预存卡	pcard*/
			request = new AlipayTradePrecreateRequest();//创建API对应的request类
			cont="{" +"\"out_trade_no\":\"" + ro.getOut_trade_no()+ "\"," +
					" \"total_amount\":\"" + ro.getTotal_fee()*0.01+ "\"," +
					//" \"subject\":\"" +AlipayConfig.ali_title+ "\"," +
					" \"subject\":\"" +corpVo.getTitle()+ "\"," +
					//" \"disable_pay_channels\":\"balance,point,pcredit,moneyFund,coupon,creditCard,creditCardExpress,creditCardCartoon\"," +
					" \"store_id\":\"ZZW_001\"," +
					" \"timeout_express\":\"30m\"}";
			request.setBizContent(cont);//设置业务参数
			
			 // 使用SDK，构建群发请求模型  
	        request.setNotifyUrl(ro.getNotify_url() + "?orderId=" + ro.getOrder_id()  
	                + "&totalAmount=" + ro.getTotal_fee()*0.01 + "&outTradeNo="  
	                + ro.getOut_trade_no());  
			cont=null;
			response = alipayClient.execute(request);
			cont=getValueFromJsonForRSA(ro,response.getBody());
			if(null==cont){
				cont="https://mobilecodec.alipay.com/show.htm?code="+ro.getSource_qrcode().substring("https://qr.alipay.com/".length());
			}else{
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} 
		return cont;
		
	}
	
	private static String getValueFromJsonForRSA(Order vo, String jsonStr) {
		JSONObject resultJson = null;
		JSONObject respJson = null;
		try {
			resultJson = new JSONObject(jsonStr);
			if (null != resultJson) {
				if (!resultJson.isNull("sign")) {
					vo.setSign(resultJson.getString("sign"));
				}
				if (!resultJson.isNull("alipay_trade_precreate_response")) {
					respJson = resultJson.getJSONObject("alipay_trade_precreate_response");
				}
				if (null == respJson) {
					return "alipay_trade_precreate_response is null";
				}
				if (!respJson.isNull("code")) {
					vo.setReturn_code(respJson.getString("code"));
				}
				if (!respJson.isNull("msg")) {
					vo.setReturn_msg(respJson.getString("msg"));
				}		
				if (!respJson.isNull("out_trade_no")) {
					if (!respJson.getString("out_trade_no").equals(vo.getOut_trade_no())) {
						return vo.getOut_trade_no()	+ "out_trade_no not equals "+ respJson.getString("out_trade_no");
					}
				} else {
					return "out_trade_no is null";
				}
				if (!respJson.isNull("qr_code")) {
					jsonStr = respJson.getString("qr_code");//https://qr.alipay.com/bax02043wqw2uqfxcule00bf
					if(null!=jsonStr&&jsonStr.startsWith("https://qr.alipay.com/")){
						vo.setSource_qrcode(jsonStr);
					}					
				} else {
					return "qr_code is null";
				}				
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return null;
	}

	
	public static String errorInfo(String error_code, String err_info) {
		ErrorResp errorResp = new ErrorResp();
		errorResp.setRetcode(error_code);
		if ("0000".equals(error_code)) {
			errorResp.setRetmsg("success " + err_info);
			return errorResp.CreateXml();
		}
		if (CheckUtils.isNull(err_info)) {
			com.lmxf.post.entity.config.ErrorCode errorCode=(com.lmxf.post.entity.config.ErrorCode)CacheUtils.get(CacheUtils.errorCodeCache,error_code);
			if(errorCode!=null)
			err_info = errorCode.getRetDesc();
			if (null == err_info) {
				err_info = "No define error code";
			}
		}
		errorResp.setRetmsg(err_info);
		return errorResp.CreateXml();
	}
	
	private static void getResult(String xml, Order order) {
		log.debug(order.getOut_trade_no() + "httpResult:" + xml);
		if (null == xml || "".equals(xml) || null == order) {
			return;
		}
		order.setReturn_code(getField(xml, "return_code"));
		order.setReturn_msg(getField(xml, "return_msg"));
		order.setResult_code(getField(xml, "result_code"));
		order.setPrepay_id(getField(xml, "prepay_id"));
		order.setCode_url(getField(xml, "code_url"));
		if (null != order.getReturn_code()) {
			order.setReturn_code(order.getReturn_code().replace("<![CDATA[", "").replace("]]>", ""));
		}
		if (null != order.getReturn_msg()) {
			order.setReturn_msg(order.getReturn_msg().replace("<![CDATA[", "").replace("]]>", ""));
		}
		if (null != order.getResult_code()) {
			order.setResult_code(order.getResult_code().replace("<![CDATA[", "").replace("]]>", ""));
		}
		if (null != order.getPrepay_id()) {
			order.setPrepay_id(order.getPrepay_id().replace("<![CDATA[", "").replace("]]>", ""));
		}
		if (null != order.getCode_url()) {
			order.setCode_url(order.getCode_url().replace("<![CDATA[", "").replace("]]>", ""));
			if (null == order.getCode_url()) {
				order.setCode_url("");
			}
		}
	}
	
	public static String getField(String xml, String name) {
		int pos1 = xml.indexOf("<" + name + ">");
		if (pos1 < 0) {
			return null;
		}
		int pos2 = xml.indexOf("</" + name + ">", pos1);
		if (pos2 < 0) {
			return null;
		}
		return xml.substring(pos1 + ("<" + name + ">").length(), pos2);
	}
	
	// 创建响应报文
	private static String PreCreateJson(Order vo, String msg) {
		QrcodePayApplyResp resp = new QrcodePayApplyResp();
		resp.setOrder(vo);
		if (null == msg) {
			resp.setRetcode(Constants.SUCCESS_code);
			resp.setRetmsg(Constants.SUCCESS);
		} else {
			resp.setRetcode(Constants.FAIL_code);
			resp.setRetmsg(msg);
		}
		return resp.CreateJson();
	}

	// 保存图片
	public static void saveImage(Order order,String context) throws FileNotFoundException,IOException {
		byte[] qrcode = null;
		File file = null;
		OutputStream out = null;
		try {
			qrcode = getCodeImage(order.getCode_url());
			if (null == qrcode) {
				return;
			}
			if(null==pngPath||"".equals(pngPath.trim())){
				pngPath = new File("").getAbsolutePath().split("bin")[0] + "webapps"+context+"/qrcode/";
			}
			file = new File(pngPath + order.getOut_trade_no() + ".png");
			out = new FileOutputStream(file);
			out.write(qrcode);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			qrcode = null;
			file = null;
			out = null;
		}
	}
	
	/**
	 * 获取带参数的二维码图片
	 * 
	 * @return
	 * @throws IOException
	 */
	public static byte[] getCodeImage(String url) throws IOException {
		if (null == url || "".equals(url.trim())) {
			return null;
		}
		// 生成图片返回字节流
		TwoDimensionCode twoDimensionCode = new TwoDimensionCode();
		// 字节流
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		// 字节流
		twoDimensionCode.encoderQRCode(url, output, "png");
		// 转换
		return output.toByteArray();
	}

	private static String searchAliPlat(Order ro,PayconfigAlipay payconfigAlipay){
		if(null==ro||null==payconfigAlipay){
			return null;
		}
		AlipayClient client = null;
		AlipayTradeQueryRequest req = null;
		AlipayTradeQueryResponse resp = null;
		try {
			client = new DefaultAlipayClient(payconfigAlipay.getAlipayApi(), payconfigAlipay.getPid(), payconfigAlipay.getPrivKey(), "json", "GBK", payconfigAlipay.getPublKey(), payconfigAlipay.getSignType()); //获得初始化的AlipayClient
			req = new AlipayTradeQueryRequest();
			req.setBizContent("{\"out_trade_no\":\"" + ro.getOut_trade_no()+ "\"}");
			resp = client.execute(req);
			client = null;
			if (null != resp && null != resp.getBody()) {
				log.debug(resp.getBody());
				return getValueFromJson(ro, resp.getBody());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return null;
	}
	
	private static String getValueFromJson(Order vo, String jsonStr) {
		JSONObject resultJson = null;
		JSONObject respJson = null;
		try {
			resultJson = new JSONObject(jsonStr);
			if (null != resultJson) {
				if (!resultJson.isNull("sign")) {
					vo.setSign(resultJson.getString("sign"));
				}
				if (!resultJson.isNull("alipay_trade_query_response")) {
					respJson = resultJson.getJSONObject("alipay_trade_query_response");
				}
				if (null == respJson) {
					return "alipay_trade_query_response is null";
				}
				if (!respJson.isNull("code")) {
					vo.setReturn_code(respJson.getString("code"));
				}
				if (!respJson.isNull("msg")) {
					vo.setReturn_msg(respJson.getString("msg"));
				}
				if (!respJson.isNull("buyer_logon_id")) {
					vo.setUser_email(respJson.getString("buyer_logon_id"));
				}
				if (!respJson.isNull("buyer_user_id")) {
					vo.setDetail(respJson.getString("buyer_user_id"));
				}
				if (!respJson.isNull("out_trade_no")) {
					if (!respJson.getString("out_trade_no").equals(vo.getOut_trade_no())) {
						return vo.getOut_trade_no()	+ "out_trade_no not equals "+ respJson.getString("out_trade_no");
					}
				} else {
					return "out_trade_no is null";
				}
				if (!respJson.isNull("send_pay_date")) {
					vo.setPay_time(respJson.getString("send_pay_date"));
				}
				if (!respJson.isNull("total_amount")) {
					jsonStr = respJson.getString("total_amount");
					int mytotal_amount = (int) (Float.parseFloat(jsonStr) * 100);

					if (mytotal_amount != vo.getTotal_fee()) {
						return vo.getTotal_fee() + " total_amount not equals "+ respJson.getString("total_amount");
					}
				} else {
					return "total_amount is null";
				}
				if (respJson.isNull("trade_no")) {
					return "trade_no is null";
				}
				if (!respJson.isNull("trade_status")) {
					// 交易状态：WAIT_BUYER_PAY（交易创建，等待买家付款）、TRADE_CLOSED（未付款交易超时关闭，或支付完成后全额退款）、TRADE_SUCCESS（交易支付成功）、TRADE_FINISHED（交易结束，不可退款）
					if ("TRADE_SUCCESS".equals(respJson.getString("trade_status"))) {
						vo.setPay_state("01");
						vo.setPay_time(DateUtils.DateToString());
					} else if ("TRADE_CLOSED".equals(respJson.getString("trade_status"))) {
						vo.setPay_state("02");
						vo.setPay_time(DateUtils.DateToString());
					}
				} else {
					return "trade_status is null";
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return null;
	}
	
	private static void searchWXPlat(Order vo) throws SQLException, Exception {
		String pkg = null;
		try {
			pkg = getWxQueryPackage(vo);
		} catch (SDKRuntimeException e) {
			e.printStackTrace();
		}
		if (null == pkg || "".equals(pkg.trim())) {
			return;
		}
		log.debug(vo.getOut_trade_no() + "jsapi_package:" + pkg);

		String ret = PrintUtils.sendPost("https://api.mch.weixin.qq.com/pay/orderquery", pkg);
		getValueFromXml(vo, ret);
	}
	
	private static String getWxQueryPackage(Order order) throws SDKRuntimeException {
		// 先设置基本信息
		PayconfigWechatDao payconfigWechatDao = (PayconfigWechatDao)SpringContextUtil.getBean("payconfigWechatDao");
		PayconfigWechat payconfigWechat = payconfigWechatDao.findOneByCorpId(order.getCorp_id());
		if(null==payconfigWechat){
			return null;
		}
		order.setAppid(payconfigWechat.getAppId());
		order.setAppKey(payconfigWechat.getAppSecret());
		order.setMch_id(payconfigWechat.getContactCode());
		// 先清空签名
		order.SetParameter("sign", null);
		// 设置请求package信息
		order.SetParameter("appid", order.getAppid());
		order.SetParameter("mch_id", order.getMch_id());
		order.SetParameter("nonce_str", Order.CreateNoncestr());
		order.SetParameter("out_trade_no", order.getOut_trade_no());
		if(null!=payconfigWechat.getSubMchId()&&!"".equals(payconfigWechat.getSubMchId().trim())&&!order.getMch_id().equals(payconfigWechat.getSubMchId().trim())){
			order.SetParameter("sub_mch_id", payconfigWechat.getSubMchId().trim());//"1320645901");//子商户号	sub_mch_id	是	String(32)	1900000109	微信支付分配的子商户号
		}
		return order.CreateNativePackage();
	}
	
	private static void getValueFromXml(Order vo, String xml) {
		log.debug(vo.getOut_trade_no() + " httpResult:" + xml);
		if (null == xml || "".equals(xml) || null == vo) {
			return;
		}
		String msg = getField(xml, "out_trade_no");
		if (null != msg) {
			msg = (msg.replace("<![CDATA[", "").replace("]]>", ""));
		}
		if (!vo.getOut_trade_no().equals(msg)) {
			return;
		}
		msg = getField(xml, "trade_state");
		if (null != msg) {
			msg = (msg.replace("<![CDATA[", "").replace("]]>", ""));
		}
		if ("SUCCESS".equals(msg)) {
			vo.setPay_state("01");
			msg = getField(xml, "time_end");
			if (null != msg) {
				msg = (msg.replace("<![CDATA[", "").replace("]]>", ""));
			}
			vo.setPay_time(DateUtils.StringToDateStr(msg, DateUtils.dtLong,	DateUtils.simple));
		} else if ("REFUND".equals(msg)) {
			vo.setPay_state("04");
			msg = getField(xml, "time_end");
			if (null != msg) {
				msg = (msg.replace("<![CDATA[", "").replace("]]>", ""));
			}
			vo.setPay_time(DateUtils.StringToDateStr(msg, DateUtils.dtLong,	DateUtils.simple));
		}
		vo.setReturn_code(getField(xml, "return_code"));
		vo.setReturn_msg(getField(xml, "return_msg"));
		vo.setResult_code(getField(xml, "result_code"));
		if(null!=getField(xml, "openid")){
			vo.setOpenid(getField(xml, "openid"));
		}
		if(null!=getField(xml, "transaction_id")){
		  vo.setPrepay_id(getField(xml, "transaction_id"));
		}

		if (null != vo.getReturn_code()) {
			vo.setReturn_code(vo.getReturn_code().replace("<![CDATA[", "").replace("]]>", ""));
		}
		if (null != vo.getReturn_msg()) {
			vo.setReturn_msg(vo.getReturn_msg().replace("<![CDATA[", "").replace("]]>", ""));
		}
		if (null != vo.getResult_code()) {
			vo.setResult_code(vo.getResult_code().replace("<![CDATA[", "").replace("]]>", ""));
		}
		if (null != vo.getOpenid()) {
			vo.setOpenid(vo.getOpenid().replace("<![CDATA[", "").replace("]]>", ""));
		}
		if (null != vo.getPrepay_id()) {
			vo.setPrepay_id(vo.getPrepay_id().replace("<![CDATA[", "").replace("]]>", ""));
		}
	}
	
	// 创建响应报文
	private static String WxOrderPayXml(Order vo) {
		// 先设置基本信息
		StringBuffer b =new StringBuffer("<?xml version='1.0' encoding='utf-8'?>");
		b.append("<ZMSG><ZHEAD>");
		b.append("<retcode>0000</retcode>");
		b.append("<retmsg>"+getStateInfo(vo.getPay_state())+"</retmsg>");
		b.append("<totnum>1</totnum><curnum>1</curnum>");
		b.append("</ZHEAD><ZBODY>");
	/*	b.append("<appId>"+cp.getAppid()+"</appId>");
		b.append("<timeStamp>"+vo.getTime_end()+"</timeStamp>");
		b.append("<nonceStr>"+vo.getNonce_str()+"</nonceStr>");						
		*/
		b.append(vo.getPay_url());
		b.append("<pckage>prepay_id="+vo.getPrepay_id()+"</pckage>");
		b.append("<signType>MD5</signType>");
		b.append("<paySign>"+vo.getCode_url()+"</paySign>");
		b.append("<out_trade_no>"+vo.getOut_trade_no()+"</out_trade_no>");
		b.append("<prepay_id>"+vo.getPrepay_id()+"</prepay_id>");
		b.append("<limit_time>"+vo.getLimit_time()+"</limit_time>");
		b.append("<pay_state>"+vo.getPay_state()+"</pay_state>");
		b.append("<pay_time>"+vo.getPay_time()+"</pay_time>");
		b.append("<order_id>"+vo.getOrder_id()+"</order_id>");
		b.append("<openid>"+vo.getOpenid()+"</openid>");
		b.append("<limit_pay>"+vo.getLimit_pay()+"</limit_pay>");		
		b.append("<resp_time>"+DateUtils.DateToString()+"</resp_time>");
		b.append("</ZBODY>");
		b.append("</ZMSG>");			 
		return b.toString();
	}
	
	/**支付状态00等待支付01支付成功02支付失效03部分退款04全部退款05支付结算完成*/
	private static String getStateInfo(String pay_state) {
		if(null==pay_state||"".equals(pay_state.trim())){
			return "状态为空";
		}else if("00".equals(pay_state.trim())){
			return "未支付";
		}else if("01".equals(pay_state.trim())){
			return "已支付";
		}else if("02".equals(pay_state.trim())){
			return "支付失效";
		}else if("03".equals(pay_state.trim())){
			return "部分退款";
		}else if("04".equals(pay_state.trim())){
			return "全部退款";
		}else if("05".equals(pay_state.trim())){
			return "支付结算完成";
		}
		return "状态未知";
	}

	public static void main(String args[]) throws Exception{
		OrderApply orderApply = new OrderApply();
		orderApply.setPayType("02");
		tradeHand(orderApply);
	}
	

}
