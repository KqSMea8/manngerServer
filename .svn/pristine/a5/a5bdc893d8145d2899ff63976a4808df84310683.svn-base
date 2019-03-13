package com.lmxf.post.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.putils.mail.util.MailSenderInfo;
import org.apache.commons.putils.mail.util.SimpleMailSender;
import org.apache.commons.putils.utils.CPropertyUtil;

public class SendEmail {

	/**
	 * 发送邮件
	 * 
	 * @param mailServerHost
	 * @param mailServerPort
	 * @param userName
	 * @param password
	 * @param fromAddress
	 * @param subject
	 * @param email
	 * @param content
	 * @return
	 */
	public static Boolean emailTo(String subject, String email, String content) {
		if (!isEmail(email)) {
			return false;
		}

		// 获取配置文件中选择的服务器编码
		String email_send_server = null;
		String mailServerHost = null;
		String mailServerPort = null;
		String userName = null;
		String password = null;
		String fromAddress = null;
		boolean isSuccess = false;
		MailSenderInfo mailInfo = null;
		try {
			/*
			 * mailServerHost =CacheUtils.getKeyValue("EmailSendConfig",
			 * "EmailSendConfig_HostName"); mailServerPort =
			 * CacheUtils.getKeyValue("EmailSendConfig",
			 * "EmailSendConfig_SmtpPort"); userName =
			 * CacheUtils.getKeyValue("EmailSendConfig",
			 * "EmailSendConfig_UserName"); password =
			 * CacheUtils.getKeyValue("EmailSendConfig",
			 * "EmailSendConfig_PassWord"); fromAddress
			 * =CacheUtils.getKeyValue("EmailSendConfig",
			 * "EmailSendConfig_Accout");
			 */
			/*
			 * mailServerHost ="192.168.1.125"; mailServerPort ="25"; userName
			 * ="yangfang@test.com"; password ="123456"; fromAddress
			 * ="yangfang@test.com";
			 */
			// 获取配置文件中选择的服务器编码
			email_send_server = (String) CPropertyUtil.getContextProperty("email_send_server");
			// 获取服务器地址
			mailServerHost = (String) CPropertyUtil.getContextProperty("email_serverHost_" + email_send_server);
			// 获取服务器地址
			mailServerPort = (String) CPropertyUtil.getContextProperty("email_serverPort_" + email_send_server);
			// 获取账户名
			userName = (String) CPropertyUtil.getContextProperty("email_userName_" + email_send_server);
			// 获取密码
			password = (String) CPropertyUtil.getContextProperty("email_password_" + email_send_server);
			// 发件人邮箱号码
			fromAddress = (String) CPropertyUtil.getContextProperty("email_fromAddress_"+ email_send_server);

			mailInfo = new MailSenderInfo();
			mailInfo.setMailServerHost(mailServerHost);
			mailInfo.setMailServerPort(mailServerPort);
			mailInfo.setValidate(true);
			mailInfo.setUserName(userName);
			mailInfo.setPassword(password);// 您的邮箱密码
			mailInfo.setFromAddress(fromAddress);
			mailInfo.setToAddress(email);
			mailInfo.setSubject(subject);
			mailInfo.setContent(content);

			isSuccess = SimpleMailSender.sendHtmlMail(mailInfo);
		} catch (Exception e) {
			if (e.getMessage().contains(
					"550 Delivery is not allowed to this address")) {
				System.out.println(email + "地址不存在！");
			} else {
				e.printStackTrace();
			}
			isSuccess = false;
		} finally {
			saveSendEmailLog(mailInfo);
			mailServerHost = null;
			mailServerPort = null;
			userName = null;
			password = null;
			fromAddress = null;
			email_send_server = null;
			mailInfo = null;
		}
		return isSuccess;
	}

	private static void saveSendEmailLog(MailSenderInfo mailInfo) {

	}

	/**之前： \\w+@(\\w+.)+[a-z]{2,3}
	 * 验证电子邮箱地址  true有效邮件地址 false非法邮件地址Invalid email address
	 * 现在：  ^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		Pattern p = Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
		Matcher m = p.matcher(email);
		if (m.matches()) {
			return true;
		}
		return false;
	}

	/**
	 * 邮件通知
	 * 
	 * @param msg
	 * @param email
	 * @return
	 */
	public static boolean emailNotify(String msg, String email, String title) {
		String subject = "Request payment notice";
		if (!CheckUtils.isNull(title)) {
			subject = title;
		}
		/** String emailToUser = "wu20071116@163.com"; String emailToAdmin ="zhengzhiwu@chinawebox.com"; */

		// 获取配置文件中选择的服务器编码
		String email_send_server = (String) CPropertyUtil.getContextProperty("email_send_server");
		// 默认的收件人邮箱号码
		// String emailToUser = "zhengzhiwu@test.com";
		String emailToUser = (String) CPropertyUtil.getContextProperty("email_emailToUser_" + email_send_server);

		// 获取管理员邮箱号码
		// String emailToAdmin = "zhengzhiwu@test.com";
		String emailToAdmin = (String) CPropertyUtil.getContextProperty("email_emailToAdmin_" + email_send_server);

		String content = msg;
		if ("".equals(email) || email == null) {

		} else {
			emailToUser = email;
		}

		boolean emailToUserTag = false;
		boolean emailToAdminTag = false;

		try {
			emailToUserTag = SendEmail.emailTo(subject, emailToUser, content);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			emailToAdminTag = SendEmail.emailTo(subject, emailToAdmin, content);
		} catch (Exception e) {
			e.printStackTrace();
		}

		subject = null;
		emailToUser = null;
		emailToAdmin = null;
		content = null;

		if (emailToUserTag && emailToAdminTag) {
			return true;
		}
		return false;
	}

	// 邮件模板
	public static String EmailModel(String msg) {
		if (msg == null) {
			return null;
		}
		return null;
	}
}
