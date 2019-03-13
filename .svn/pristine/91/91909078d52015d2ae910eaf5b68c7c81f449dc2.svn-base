/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */
package com.lmxf.post.core.utils.pay.alipay;
import java.util.HashMap;
import java.util.Map;
import net.sf.json.JSONObject;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.lmxf.post.core.utils.SpringContextUtil;
import com.lmxf.post.entity.order.OrderApply;
import com.lmxf.post.entity.pay.PayconfigAlipay;
import com.lmxf.post.repository.pay.PayconfigAlipayDao;

/**
 * 支付宝退款工具类
 * 2018.12.19
 * @author 思杰
 *
 */
public class AliPayRefundUtils {
	private static final String refund_url = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	private final static Log log = LogFactory.getLog(AliPayRefundUtils.class);

	public static Map<String, String> orderRefund(OrderApply orderApply) {
		Map<String, String> resultMap = new HashMap<String, String>();
		String xml = null;
		PayconfigAlipayDao payconfigAlipayDao = (PayconfigAlipayDao) SpringContextUtil.getBean("payconfigAlipayDao");
		PayconfigAlipay payconfigAlipay = payconfigAlipayDao.findOneByCorpId(orderApply.getCorpId());
		if (payconfigAlipay == null) {
			resultMap.put("code", "1");
			return resultMap;
		}
		AlipayClient client = new DefaultAlipayClient(payconfigAlipay.getAlipayApi(), payconfigAlipay.getPid(), payconfigAlipay.getPrivKey(), "json",
				"GBK", payconfigAlipay.getPublKey(), payconfigAlipay.getSignType()); // 获得初始化的AlipayClient
		AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
		request.setBizContent("{" + "\"out_trade_no\":\"" + orderApply.getpTradeNo() + "\"," + "\"trade_no\":\"\"," + "\"refund_amount\":" + orderApply.getPayPrice()
				+ "," + "\"refund_reason\":\"正常退款\"," + "\"out_request_no\":\"ZW_" + System.nanoTime() + "\"," + "\"operator_id\":\"ZW001OP\","
				+ "\"store_id\":\"SZ_ZW_001\"," + "\"terminal_id\":\"SZ_ZW_1001\"" + "  }");
		AlipayTradeRefundResponse response = null;
		try {
			response = client.execute(request);
		} catch (AlipayApiException e) {
			resultMap.put("code", "2");
			return resultMap;
		}
		if (response.isSuccess()) {
			log.debug("支付宝退款成功:" + response.getBody());
			//{"alipay_trade_refund_response":{"code":"10000","msg":"Success","buyer_logon_id":"152****0715","buyer_user_id":"2088902981908310","fund_change":"Y","gmt_refund_pay":"2018-01-24 15:01:52","out_trade_no":"16389900508981170","refund_detail_item_list":[{"amount":"0.01","fund_channel":"ALIPAYACCOUNT"}],"refund_fee":"0.01","send_back_fee":"0.01","trade_no":"2018012321001004310251362658"},"sign":"gUvDDAjTtcDahc1KCFZVvFJUkRILW2XuQFShGhrKs/363d0j5C8LbEF66w8APUywsDognhpvntdy/4jxPwv5yWwVbutfUTdYVLLpv5zVTYpSMnrz4hGQej3mkNawC0EBlUqH1RHlEPP8o0GEE0eQkrec4/AIdT7jLL3cNnmaXFI="}
			xml = response.getBody();
		}
        JSONObject jsonObject=JSONObject.fromObject(xml);
        if(jsonObject.containsKey("alipay_trade_refund_response")){
        	JSONObject responseJson=jsonObject.getJSONObject("alipay_trade_refund_response");
        	if(responseJson.containsKey("code")){
        		if(responseJson.getString("code").equals("10000")){
        			resultMap.put("code", "SUCCESS");
        			resultMap.put("rTradeNo", responseJson.getString("trade_no"));
        		}else{
        			resultMap.put("code", "3");
        		}
        	}else{
    			resultMap.put("code", "4");
    		}
        }
		return resultMap;
	}

}
