package com.lmxf.post.core.utils;

import java.io.IOException;
import org.apache.commons.net.smtp.SMTPClient;
import org.apache.commons.net.smtp.SMTPReply;
import org.xbill.DNS.*;

public class EmailUtil {
	
	/**
	 *  
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email) {
		boolean ckResult=false;
		if (!email.matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+")) {
			return ckResult;
		}
		String host =null;
		String hostName = null;
		Record[] result = null;
		SMTPClient client = null;
		Lookup lookup =null;
		try {
			hostName= email.split("@")[1];			
			// 查找MX记录
			lookup = new Lookup(hostName, Type.MX);
			lookup.run();
			if (lookup.getResult() != Lookup.SUCCESSFUL) {
				return ckResult;
			} else {
				result = lookup.getAnswers();
			}
			client = new SMTPClient();
			// 连接到邮箱服务器
			for (int i = 0; i < result.length; i++) {
				host = result[i].getAdditionalName().toString();
				client.connect(host);
				if (!SMTPReply.isPositiveCompletion(client.getReplyCode())) {
					client.disconnect();
					continue;
				} else {
					break;
				}
			}
			if(email.endsWith("163.com")){
				client.login("qq.com");
				client.setSender("376973701@qq.com");
			}else if(!email.endsWith("163.com")){
				client.login("163.com");
				client.setSender("wu20071116@163.com");
			}else{
				client.login("163.com");
				client.setSender("wu20071116@163.com");
			}
			
			client.addRecipient(email);
			if (250 == client.getReplyCode()) {
				ckResult=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(null!=client){
					client.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			lookup =null;
			host =null;
			hostName = null;
			result = null;
			client = null;
			System.err.println(email+" "+ckResult);
		}
		return ckResult;
	}
	
	public static boolean checkEmail_bak(String email) {
		if (!email.matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+")) {
			//System.err.println("Format error");
			return false;
		}

		String log = "";
		String host = "";
		String hostName = email.split("@")[1];
		Record[] result = null;
		SMTPClient client = new SMTPClient();

		try {
			// 查找MX记录
			Lookup lookup = new Lookup(hostName, Type.MX);
			lookup.run();
			if (lookup.getResult() != Lookup.SUCCESSFUL) {
				log += "找不到MX记录\n";
				return false;
			} else {
				result = lookup.getAnswers();
			}

			// 连接到邮箱服务器
			for (int i = 0; i < result.length; i++) {
				host = result[i].getAdditionalName().toString();
				client.connect(host);
				if (!SMTPReply.isPositiveCompletion(client.getReplyCode())) {
					client.disconnect();
					continue;
				} else {
					log += "MX record about " + hostName + " exists.\n";
					log += "Connection succeeded to " + host + "\n";
					break;
				}
			}
			log += client.getReplyString();

			// HELO cyou-inc.com
			client.login("163.com");
			log += ">HELO 163.com\n";
			log += "=" + client.getReplyString();

			// MAIL FROM: <zhaojinglun@cyou-inc.com>
			client.setSender("wu20071116@163.com");
			log += ">MAIL FROM: <wu20071116@163.com>\n";
			log += "=" + client.getReplyString();
			
			// RCPT TO: <$email>
			client.addRecipient(email);
			log += ">RCPT TO: <" + email + ">\n";
			log += "=" + client.getReplyString();

			if (250 == client.getReplyCode()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				client.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 打印日志
			System.err.println(log);
		}
		return false;
	}
	
	
	
	/**
	 * 校验null,"",及"null"
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (null == str || "".equals(str.trim())|| "null".equalsIgnoreCase(str.trim()))
			return true;
		else
			return false;
	}

	public static void main(String[] args) {
		System.err.println("Outcome: "	+ EmailUtil.checkEmail_bak("zhengzhiwu1111@chinawebox.com"));
	
	}
}
