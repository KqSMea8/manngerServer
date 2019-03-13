package com.lmxf.post.core.utils;

/**
 * @Dscription TDD
 * @Author 邬思杰
 * @Version 1.0
 * @Date 2018-08-27$ 10:00:00$
 **/
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.lmxf.post.service.order.TradeOrderOutService;

import java.io.*;
import java.net.URLEncoder;

public class HttpClient {
	private final static Log log = LogFactory.getLog(HttpClient.class);
    public static String doGet(String url) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(5000)
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .build();
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        get.setConfig(requestConfig);
        HttpResponse response = null;
        try {
            response = client.execute(get);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity resEntity = response.getEntity();
                String message = null;
                try {
                    message = EntityUtils.toString(resEntity, "utf-8");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                log.info("向地址:"+url+"发送请求正常->"+message);
                return message;
            } else {
                return "";
            }
        } catch (IOException e) {
            log.error("向地址:"+url+"发送请求异常->"+e.getMessage());
        }finally {
            try {
                if (response != null) {
                    ((CloseableHttpResponse) response).close();
                }
                if (client != null) {
                    client.close();
                }
            }catch (IOException ex){
                log.warn("关闭HTTP流通道出现异常");
            }
        }
        return "";
    }
    public static void main(String[] str){
        System.out.println(doGet("http://localhost:8087/msg/send?content="+URLEncoder.encode("{'fd':'d'}")));
    }
}
