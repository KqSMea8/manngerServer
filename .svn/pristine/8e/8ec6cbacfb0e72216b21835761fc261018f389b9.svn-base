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
package com.lmxf.post.core.utils.pay.wechat;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.net.ssl.SSLContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.apache.commons.logging.Log;
import com.lmxf.post.core.utils.SpringContextUtil;
import com.lmxf.post.entity.order.OrderApply;
import com.lmxf.post.entity.pay.Order;
import com.lmxf.post.entity.pay.PayconfigWechat;
import com.lmxf.post.repository.pay.PayconfigWechatDao;

/**
 * 2018.12.19
 * 
 * @author 思杰
 * 
 */
public class WeixinPayRefundUtils {
	private static final String refund_url = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	private final static Log log = LogFactory.getLog(WeixinPayRefundUtils.class);
	//证书内容
	private static Map<String, KeyStore> weixinLinceMap = new HashMap();
	//证书刷新时间
	private static Map<String, String> weixinLincRefresheMap = new HashMap();

	public static Map<String, String> orderRefund(OrderApply orderApply) {
		Map<String, String> resultMap = new HashMap();
		String xml = null;
		try {
			// 获取微信支付配置信息
			PayconfigWechatDao payconfigWechatDao = (PayconfigWechatDao) SpringContextUtil.getBean("payconfigWechatDao");
			PayconfigWechat vo = payconfigWechatDao.findOneByCorpId(orderApply.getCorpId());
			if (null == vo || null == vo.getContactCode()) {
				resultMap.put("code", "1");
				log.error("订单号:" + orderApply.getOrderId() + " 公司编号:" + orderApply.getCorpId() + " 获取支付配置失败.");
				return resultMap;
			}
			// 获取证书信息
			KeyStore keyStore =getCert(vo);
			if(keyStore==null){
				resultMap.put("code", "1");
				return resultMap;
			}
			// 封装退款xml请求内容
			Order order = new Order();
			order.setCorp_id(orderApply.getCorpId());
			order.setOut_trade_no(orderApply.getpTradeNo());
			order.setFee("" + orderApply.getPayPrice());
			order.setTotal_fee("" + orderApply.getPayPrice());

			order.setAppid(vo.getAppId());
			order.setAppKey(vo.getAppSecret());
			order.setMch_id(vo.getContactCode());
			// 先清空签名
			order.SetParameter("sign", null);
			// 设置请求package信息
			order.SetParameter("appid", order.getAppid());
			order.SetParameter("mch_id", order.getMch_id());
			order.SetParameter("nonce_str", Order.CreateNoncestr());
			order.SetParameter("out_refund_no", "zw" + System.nanoTime());
			order.SetParameter("out_trade_no", order.getOut_trade_no());
			order.SetParameter("refund_fee", order.getTotal_fee() + "");
			order.SetParameter("total_fee", order.getTotal_fee() + "");
			order.SetParameter("transaction_id", "");
			xml = order.CreateNativePackage();
			if (null == xml || "".equals(xml)) {
				resultMap.put("code", "1");
				log.error("订单号:" + orderApply.getOrderId() + " 公司编号:" + orderApply.getCorpId() + " 获取签名出错:" + xml);
				return resultMap;
			}
			StringEntity se = new StringEntity(xml);
			// Trust own CA and all self-signed certs
			SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, vo.getContactCode().toCharArray()).build();
			// Allow TLSv1 protocol only
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
			CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			try {
				HttpPost httppost = new HttpPost(refund_url);
				httppost.setEntity(se);
				log.info("executing request" + httppost.getRequestLine());
				CloseableHttpResponse response = httpclient.execute(httppost);
				try {
					HttpEntity entity = response.getEntity();
					log.debug("----------------------------------------" + response.getStatusLine());
					if (entity != null) {
						log.info("Response content length: " + entity.getContentLength());
						BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
						String text;
						StringBuffer b = new StringBuffer();
						while ((text = bufferedReader.readLine()) != null) {
							b.append(text);
							System.out.println(b.toString());
						}
						xml = b.toString();
					}
					EntityUtils.consume(entity);
				} finally {
					response.close();
				}
			} finally {
				httpclient.close();
			}
		} catch (Exception e) {
			log.error("订单号:" + orderApply.getOrderId() + " 退款异常:" + e.getMessage());
			resultMap.put("code", "1");
			return resultMap;
		}
		String returnCode = "";
		String refundId = "";
		SAXReader reader = new SAXReader();
		try {
			// 通过reader对象的read方法加载books.xml文件,获取docuemnt对象。
			Document document = DocumentHelper.parseText(xml);
			// 通过document对象获取根节点bookstore
			Element bookStore = document.getRootElement();
			// 通过element对象的elementIterator方法获取迭代器
			Iterator it = bookStore.elementIterator();
			// 遍历迭代器，获取根节点中的信息（书籍）
			while (it.hasNext()) {
				Element book = (Element) it.next();
				if ("return_code".equals(book.getName())) {
					returnCode = book.getText();
				}
				if ("refund_id".equals(book.getName())) {
					refundId = book.getText();
				}
			}
			resultMap.put("code", returnCode);
			resultMap.put("rTradeNo", refundId);
			log.info("订单号:" + orderApply.getOrderId() + " 退款状态:" + returnCode + "  退款流水号:" + refundId);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			log.error("订单号:" + orderApply.getOrderId() + " 微信反馈Xml解析出错:" + e.getMessage());
		}
		return resultMap;
	}
    public static KeyStore getCert(PayconfigWechat payconfigWechat){
    	KeyStore keyStoreLocal = weixinLinceMap.get(payconfigWechat.getCorpId()+"_keyStore");
		KeyStore keyStore = null;
		if (keyStoreLocal != null && payconfigWechat.getCreateTime().equals(weixinLinceMap.get(payconfigWechat.getCorpId()+"_refresh"))) {
			log.debug("获取缓存KeyStore"+" 公司编号:"+payconfigWechat.getCorpId());
			keyStore = keyStoreLocal;
		} else {
			log.debug("获取Http KeyStore"+" 公司编号:"+payconfigWechat.getCorpId());
			String dir = "";
			try {
				dir = WeixinPayRefundUtils.class.getClassLoader().getResource("").toURI().getPath() + "cert/";
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String fileName = payconfigWechat.getCorpId() + "_weChat.p12";
			File dirTemp = new File(dir);
			if (!dirTemp.exists()) {
				dirTemp.mkdir();
			}
			try {
				FileUtils.copyURLToFile(new URL(payconfigWechat.getLicense()), new File(dir, fileName));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				log.error("公司编号:"+payconfigWechat.getCorpId()+" 获取云端证书文件出错:"+e.getMessage()+" 地址:"+payconfigWechat.getLicense());
				return null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.error("公司编号:"+payconfigWechat.getCorpId()+" 获取云端证书文件出错:"+e.getMessage()+" 地址:"+payconfigWechat.getLicense());
				return null;
			}
			// 获取本地文件加载
			try {
				keyStore = KeyStore.getInstance("PKCS12");
			} catch (KeyStoreException e) {
				// TODO Auto-generated catch block
				log.error("公司编号:"+payconfigWechat.getCorpId()+" 初始化PKCS12钥匙出错:"+e.getMessage()+" 地址:"+payconfigWechat.getLicense());
			}
			FileInputStream instream = null;
			try {
				instream = new FileInputStream(new File(dir + fileName));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				log.error("公司编号:"+payconfigWechat.getCorpId()+" 获取本地证书文件流出错:"+e.getMessage()+" 地址:"+payconfigWechat.getLicense());
				return null;
			}
			try {
				keyStore.load(instream, "1371982002".toCharArray());
				weixinLinceMap.put(payconfigWechat.getCorpId()+"_keyStore", keyStore);
				weixinLincRefresheMap.put(payconfigWechat.getCorpId()+"_refresh", payconfigWechat.getCreateTime());
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				log.error("公司编号:"+payconfigWechat.getCorpId()+" 获取本地证书文件出错:"+e.getMessage()+" 地址:"+payconfigWechat.getLicense());
				return null;
			} catch (CertificateException e) {
				// TODO Auto-generated catch block
				log.error("公司编号:"+payconfigWechat.getCorpId()+" 获取本地证书文件出错:"+e.getMessage()+" 地址:"+payconfigWechat.getLicense());
				return null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.error("公司编号:"+payconfigWechat.getCorpId()+" 获取本地证书文件出错:"+e.getMessage()+" 地址:"+payconfigWechat.getLicense());
				return null;
			}
		}
		return keyStore;
    }
}
