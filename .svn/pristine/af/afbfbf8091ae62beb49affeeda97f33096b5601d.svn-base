package com.lmxf.post.core.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.lmxf.post.core.utils.CacheUtils;
import com.lmxf.post.entity.parameter.Dict;

/**
 * 
 * @Title: TurboApiClient.java
 * @Package com.lmxf.post.core.utils.tuibomail
 * @Description: Turbo Mail Send Utils
 * @author wsj
 * @date 2016-5-19
 */
public class TurboClient {
	private static Logger log = Logger.getLogger(TurboClient.class);
	private static final String SERVER_URL = "https://api.turbo-smtp.com/api/mail/send";
	public static final String FROM_MAIL_FIELD = "smartedeliver@smartecarte.com";

	private static TurboClient instance;

	private TurboClient() {
	}

	public static synchronized TurboClient getInstance() {
		if (instance == null) {
			instance = new TurboClient();
		}
		return instance;
	}

	/**
	 * 发送国际邮件
	 * @param toMail 收件人邮件
	 * @param subject　主题
	 * @param content　内容
	 * @return
	 */
	public static boolean sendMail(String toMail, String subject, String content){
		Dict dict=(Dict) CacheUtils.get(CacheUtils.dictCache,"turboMail_username");
		Dict dict1=(Dict) CacheUtils.get(CacheUtils.dictCache,"turboMail_password");
		String USERNAME =dict.getDictValue();
		String PASSWORD =dict.getDictValue();
		if (USERNAME == null || "".equals(USERNAME)) {
			USERNAME = "smartedeliver@smartecarte.com";
			PASSWORD = "smarte1234";
			
			USERNAME = "wusijie@chinawebox.com";
			PASSWORD = "yclj4zxX";
		}
		Mail mail = new Mail();
		mail.setFrom(FROM_MAIL_FIELD);
		mail.setTo(toMail);
		mail.setSubject(subject);
		mail.setHtml_content(content);
		//mail.setBcc("376973701@qq.com");
		mail.setCc("376973701@qq.com");
		//mail.setCustom_headers("1");
		//mail.setContent("content");
       //mail.setMime_raw("20");
		/*mail.setContent("text/html;charset=UTF-8");
		mail.setCustom_headers("Base64");*/
		// message.setContent("这里是正文区域", "text/html;charset=UTF-8");// 发送邮件
		Gson jsonConverter = new Gson();
		String jsonMailString = jsonConverter.toJson(mail);
		//String jsonMailString ="";// jsonConverter.toJson(mail);
		log.info(jsonMailString);
		StringBuffer serverResponse = new StringBuffer();
		HttpURLConnection connection = null;
		try {
			URL url = new URL(SERVER_URL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("content-type", "application/json;charset=UTF-8;");
			//connection.setRequestProperty("Content-Type", "text/html; charset=utf-8");
			connection.setRequestProperty("authuser", USERNAME);
			connection.setRequestProperty("authpass", PASSWORD);
			//System.out.println("格式："+connection.getContentType());//格式：text/html; charset=utf-8
			OutputStream os = connection.getOutputStream();
			
			//jsonMailString=URLEncoder.encode(jsonMailString, "utf-8");
			os.write(jsonMailString.getBytes());
			os.flush();
			BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
			String responsePart = null;
			while ((responsePart = br.readLine()) != null) {
				serverResponse.append(responsePart);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			log.error("URL malformato!! \n", e);
			//throw e;
		} catch (IOException e) {
			e.printStackTrace();
			log.error("Errore lato Server: \n", e);
			//throw e;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Errore lato Server: \n", e);
			//throw e;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		log.info(serverResponse.toString());
		if ("{\"message\":\"OK\"}".equals(serverResponse.toString())) {
			return true;
		}
		return false;
	}

	
	public static void main(String[] args) {
		TurboClient.sendMail("462408346@qq.com", "hellow", "hello wangzhen 20161124  郑and王   five six seven eight");
   }
}
