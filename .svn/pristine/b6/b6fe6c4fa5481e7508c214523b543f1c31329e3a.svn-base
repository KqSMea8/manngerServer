package com.lmxf.post.entity.config;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Description
 * @author 许凯勋
 * @date 2016年10月14日 下午4:04:11
 */
/**
 * 分装一个http请求的工具类
 *
 * @author 顾炜【guwei】 on 14-4-22.下午3:17
 */
public class HttpClientUtil {

    private static final Logger log = LoggerFactory.getLogger(HttpClientUtil.class);

    private static HttpClientUtil httpClientUtil=null;

    public static HttpClientUtil getInstance(){
          if(httpClientUtil==null){
              httpClientUtil=new HttpClientUtil();
          }
          return httpClientUtil;
    }

    /**
     * 初始化HttpClient
     */
    private CloseableHttpClient httpClient = HttpClients.createDefault();

    /**
     * POST方式调用
     *
     * @param url
     * @param params 参数为NameValuePair键值对对象
     * @return 响应字符串
     * @throws java.io.UnsupportedEncodingException
     */
    public String executeByPOST(String url, List<NameValuePair> params) {
        HttpPost post = new HttpPost(url);
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String responseJson = null;
        try {
            if (params != null) {
                post.setEntity(new UrlEncodedFormEntity(params));
            }
            responseJson = httpClient.execute(post, responseHandler);
            log.info("HttpClient POST请求结果：" + responseJson);
        }catch (Exception e) {
            log.info("HttpClient POST请求异常：" + e.getMessage());
        }finally {
            httpClient.getConnectionManager().closeExpiredConnections();
            httpClient.getConnectionManager().closeIdleConnections(30, TimeUnit.SECONDS);
        }
        return responseJson;
    }

    /**
     * Get方式请求
     *
     * @param url 带参数占位符的URL，例：http://ip/User/user/center.aspx?_action=GetSimpleUserInfo&codes={0}&email={1}
     * @param params 参数值数组，需要与url中占位符顺序对应
     * @return 响应字符串
     * @throws java.io.UnsupportedEncodingException
     */
    public String executeByGET(String url, Object[] params) {
        try {
            String messages = MessageFormat.format(url, params);
            HttpGet get = new HttpGet(messages);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String responseJson = null;
            responseJson = httpClient.execute(get, responseHandler);
            log.info("HttpClient GET请求结果：" + responseJson);
            return responseJson;
        }catch (Exception e) {
            log.error("HttpClient GET请求异常：" + e.getMessage());
        }
        return "";
    }

    /**
     * @param url
     * @return
     */
    public String executeByGET(String url) {
        HttpGet get = new HttpGet(url);
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String responseJson = null;
        try {
            responseJson = httpClient.execute(get, responseHandler);
            log.info("HttpClient GET请求结果：" + responseJson);
        }
        catch (ClientProtocolException e) {
            e.printStackTrace();
            log.error("HttpClient GET请求异常：" + e.getMessage());
        }
        catch (IOException e) {
            e.printStackTrace();
            log.error("HttpClient GET请求异常：" + e.getMessage());
        }
        finally {
            httpClient.getConnectionManager().closeExpiredConnections();
            httpClient.getConnectionManager().closeIdleConnections(30, TimeUnit.SECONDS);
        }
        return responseJson;
    }

}
