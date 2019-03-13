package com.lmxf.post.core.utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

public class HttpUtils {
	private static Logger log = Logger.getLogger(HttpUtils.class);
	
	public static Map<String,Object> sendPost(String param, String url) {
		PrintWriter out = null;
		BufferedReader in = null;
		Map<String,Object> result = new HashMap();
		result.put("code", -1);
		String BOUNDARY = "---------------------------123821742118716"; //boundary就是request头和上传文件内容的分隔符    
		try {
			log.info("Url request:"+url);
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");  
            conn.setRequestProperty("Content-Type", "text/html; charset=utf-8; boundary=" + BOUNDARY);  
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setConnectTimeout(10 * 1000);
			conn.setReadTimeout(10 * 1000);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
//			out.print(param);
			// flush输出流的缓冲
			out.flush();
			int responseCode=conn.getResponseCode();
			result.put("code", responseCode);
			String responseCont=null;
			// 定义BufferedReader输入流来读取URL的响应
			if (responseCode== 200) {
				in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
				responseCont = "";
				String line;
				while ((line = in.readLine()) != null) {
					responseCont += line;
				}
				result.put("cont", responseCont);
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			log.error("发送 POST 请求出现异常！ url:[" + url + "] param:" + param, e);
		}finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	
	 
    public static String sendData(String urlStr, Map<String, String> textMap, Map<String, InputStream> fileMap) {  
        String res = "";  
        HttpURLConnection conn = null;  
        String BOUNDARY = "---------------------------123821742118716"; //boundary就是request头和上传文件内容的分隔符    
        try {  
            URL url = new URL(urlStr);  
            conn = (HttpURLConnection) url.openConnection();  
            conn.setConnectTimeout(5000);  
            conn.setReadTimeout(30000);  
            conn.setDoOutput(true);  
            conn.setDoInput(true);  
            conn.setUseCaches(false);  
            conn.setRequestMethod("POST");  
            conn.setRequestProperty("Connection", "Keep-Alive");  
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");  
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);  
  
            OutputStream out = new DataOutputStream(conn.getOutputStream());  
            // text    
            if (textMap != null) {  
                StringBuffer strBuf = new StringBuffer();  
                Iterator<Map.Entry<String, String>> iter = textMap.entrySet().iterator();  
                while (iter.hasNext()) {  
                    Map.Entry<String, String> entry = iter.next();  
                    String inputName = (String) entry.getKey();  
                    String inputValue = (String) entry.getValue();  
                    if (inputValue == null) {  
                        continue;  
                    }  
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");  
                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");  
                    strBuf.append(inputValue);  
                }  
                out.write(strBuf.toString().getBytes());  
            }  
  
            // file    
            if (fileMap != null) {  
                Iterator<Map.Entry<String, InputStream>> iter = fileMap.entrySet().iterator();  
                while (iter.hasNext()) {  
                    Map.Entry<String, InputStream> entry = iter.next();  
                    String inputName = (String) entry.getKey();  
                   
                    FileInputStream inputValue =   (FileInputStream) entry.getValue();  
                    if (inputValue == null) {  
                        continue;  
                    }  
                    
                    String filename = System.currentTimeMillis()+".png";  
                    String contentType = "image/png";  
                
                    StringBuffer strBuf = new StringBuffer();  
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");  
                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"; filename=\"" + filename + "\"\r\n");  
                    strBuf.append("Content-Type:" + contentType + "\r\n\r\n");  
                    System.out.println(strBuf.toString());
  
                    out.write(strBuf.toString().getBytes());  
  
                    
                    DataInputStream in = new DataInputStream(inputValue);  
                    int bytes = 0;  
                    byte[] bufferOut = new byte[1024];  
                    while ((bytes = in.read(bufferOut)) != -1) {  
                        out.write(bufferOut, 0, bytes);  
                    }  
                    in.close();  
                }  
            }  
  
            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();  
            out.write(endData);  
            out.flush();  
            out.close();  
  
            // 读取返回数据    
            StringBuffer strBuf = new StringBuffer();  
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));  
            String line = null;  
            while ((line = reader.readLine()) != null) {  
                strBuf.append(line).append("\n");  
            }  
            res = strBuf.toString();  
            reader.close();  
            reader = null;  
        } catch (Exception e) {  
            System.out.println("发送POST请求出错。" + urlStr);  
            res = "1";
            e.printStackTrace();  
        } finally {  
            if (conn != null) {  
                conn.disconnect();  
                conn = null;  
            }  
        }  
        return res;  
    }
    
    public static void main(String[] args){
    	String content = "";
		try {
			content = URLEncoder.encode("{\"ZMSG\":{\"ZBODY\":{\"ClassifyId\":\"\",\"ProductName\":\"农\",\"SiteId\":\"8888-0001\"},\"ZHEAD\":{\"BCode\":\"04\",\"IEnd\":\"1\",\"IFlag\":\"1\",\"IStart\":\"1\",\"TCode\":\"1002\"}}}","UTF-8");
		
//			content = URLEncoder.encode("{\"ZMSG\":{\"ZHEAD\":{\"BCode\":\"04\",\"TCode\":\"1003\",\"IEnd\":\"1\",\"IFlag\":\"1\",\"IStart\":\"1\"},\"ZBODY\":{\"SiteId\":\"8888-0001\",\"LoginId\":\"17680727228\",\"LoginName\":\"17680727228\",\"ApplyTime\":\"2018-09-04 18:55:31\",\"PayType\":\"02\",\"ProductInfo\":[{\"ProductId\":\"8888-0000001\",\"Price\":\"0.03\",\"Num\":3}],\"WechatPayInfo\":[{\"OpenId\":\"oj0_Gt2vn6YDVwVBnYc-olyaOvFE\"}]}}}","UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, Object> result = HttpUtils.sendPost(null, "http://127.0.0.1:8080/vending_server/api?content="+content);
//    	Map<String, Object> result = HttpUtils.sendPost(null, "http://127.0.0.1:8080/vending_server/wxNotify");
    	String jsonR=(String) result.get("cont");
    	System.out.println(jsonR);
    }
  
}
