package com.lmxf.post.core.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.log4j.Logger;
import com.lmxf.post.entity.parameter.NsParameter;
import com.lmxf.post.repository.parameter.NsParameterDao;

public class MsgPushUtils {

	private static Logger log = Logger.getLogger(MsgPushUtils.class);
	private static Map<String, String> mapUrl = new HashMap();

	public static int push(String content, String deviceId, String msgId) {
		try {
			int reuslt = 0;
			NsParameter nsParameterP = new NsParameter();
			nsParameterP.setName("msgServer_url");
			NsParameterDao nsParameterDao = (NsParameterDao) SpringContextUtil.getBean("nsParameterDao");
			NsParameter nsParameter = nsParameterDao.findOne(nsParameterP);
			if (nsParameter != null && nsParameter.getValue() != null && !"".equals(nsParameter.getValue())) {
				String query_string = "content=" + URLEncoder.encode(content) + "&deviceId=" + deviceId + "&msgId=" + msgId;
				String urlStr = nsParameter.getValue() + "?" + query_string;
				log.error("向消息服务器发送推送协议:" + urlStr);
				// 发送请求
				String resultInfo = doGet(urlStr);
				log.error("消息服务器响应结果协议:" + resultInfo);
				if (null == resultInfo || !"0000".equals(resultInfo)) {
					return GConstent.Response_MsgServer_URL_Error;
				}
			} else {
				return GConstent.Config_MsgServer_URL_Error;
			}
			return reuslt;
		} catch (Exception e) {
			log.error("向终端编号:" + deviceId + " 推送报文出错:" + content);
		}
		return 0;
	}

	public static String pushVendingServer(String content) {
		try {
			String vendingServer_url = mapUrl.get("vendingServer_url");
			if (vendingServer_url == null || "".equals(vendingServer_url)) {
				NsParameter nsParameterP = new NsParameter();
				nsParameterP.setName("vendingServer_url");
				NsParameterDao nsParameterDao = (NsParameterDao) SpringContextUtil.getBean("nsParameterDao");
				NsParameter nsParameter = nsParameterDao.findOne(nsParameterP);
				if (nsParameter != null && nsParameter.getValue() != null && !"".equals(nsParameter.getValue())) {
					mapUrl.put("vendingServer_url", nsParameter.getValue());
					vendingServer_url = nsParameter.getValue();
				}
			}
			if (vendingServer_url == null || "".equals(vendingServer_url)) {
				log.error("核心服务器获取访问地址出错");
				return "";
			}
			String query_string = "content=" + URLEncoder.encode(content);
			String urlStr = vendingServer_url + "?" + query_string;
			log.error("向业务服务器发送协议:" + urlStr);
			// 发送请求
			String resultInfo = doGet(urlStr);
			log.error("业务服务器响应结果协议:" + resultInfo);
			if (null == resultInfo) {
				log.error("向业务服务器发送协议出错:" + GConstent.Response_MsgServer_URL_Error);
			}
			return resultInfo;
		} catch (Exception e) {
			log.error("1111" + e.getCause());
		}
		return "";
	}

	private static RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(50000).setConnectTimeout(50000).setConnectionRequestTimeout(50000).build();
	private static CloseableHttpClient client = null;
	static {
		client = HttpClients.createDefault();
	}

	public static String doGet(String url) {
		CloseableHttpResponse response = null;
		HttpGet get = new HttpGet(url);
		try {
			get.setConfig(requestConfig);
			response = client.execute(get);
			if (response.getStatusLine().getStatusCode() == 200) {
				String message = null;
				try {
					InputStream in = response.getEntity().getContent();
					message = IOUtils.toString(in, "utf-8");
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
					log.error("http响应失败");
				}
				log.info("向地址:" + url + " 发送请求正常->" + message);
				return message;
			} else {
				log.info("向地址:" + url + " 发送请求失败");
				return "";
			}
		} catch (IOException e) {
			log.error("向地址:" + url + "发送请求失败->" + e.getMessage());
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				if (get != null) {
					get.abort();
				}
			} catch (IOException ex) {
				log.warn("关闭HTTP流通道出现失败");
			}
		}
		return "";
	}
}
