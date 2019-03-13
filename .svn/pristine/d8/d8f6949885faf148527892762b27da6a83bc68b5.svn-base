package com.lmxf.post.core.utils;

import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * 国际化资源文件调用
 * @author Administrator
 *
 */
public class MessageSourceHelper {

	private static MessageSource messageSource;

	public void setMessageSource(ReloadableResourceBundleMessageSource messageSource) {
		MessageSourceHelper.messageSource = messageSource;
	}
	
	public static MessageSource getMessageSource() {
		return messageSource;
	}
	
	public static String getMessage(int code,int language) {
		String msg = messageSource.getMessage(code+"", null, "",currentLocale(language));
		return msg != null&&!"".equals(msg.trim()) ? msg.trim() : "";
	}
	
	public static String getMessage(String code,int languege) {
		String msg = messageSource.getMessage(code, null, "",currentLocale(languege));
		return msg != null&&!"".equals(msg.trim()) ? msg.trim() : "";
	}
	
	public static String getMessageValue(String code,int languege) {
		String msg = messageSource.getMessage(code, null, "",currentLocale(languege));
		return msg != null&&!"".equals(msg.trim()) ? msg.trim() : code;
	}
	
	public static String getMessageValue(String code) {
		String msg = null;
		if (messageSource != null) {
			msg = messageSource.getMessage(code, null, "", currentLocale());
		}else{
			messageSource = messageSource();
			msg = messageSource.getMessage(code, null, "", currentLocale());
		}
		return (msg != null) && (!"".equals(msg.trim())) ? msg.trim() : code;
	}
	public static WebApplicationContext messageSource() {
		return RequestContextUtils.getWebApplicationContext(((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest());
	}
	public static Locale currentLocale() {
		return org.springframework.context.i18n.LocaleContextHolder.getLocale();
	}
	
	public static String getMsgValue(String code,String value,int languege) {
		String msg = messageSource.getMessage(code, null, "",currentLocale(languege));
		if (null!=msg&&!"".equals(msg.trim())) {
			return msg.trim();
		}
		if (null!=value&&!"".equals(value.trim())) {
			return value.trim();
		}
		if (null!=code&&!"".equals(code.trim())) {
			return code.trim();
		}
		return code;
	}
	
	/**
	 * 语言标识,默认是0,0中文简体1英语2中文香港繁体3中文台湾4俄文5法文
	 * @param i
	 * @return
	 */
	public static Locale currentLocale(int i) {
		 if (i==0) {
			return Locale.CHINA;
		}else if (i==1) {
			return Locale.US;
		}else if (i==2) {
			return Locale.TAIWAN;
		}else if (i==3) {
			return Locale.TAIWAN;
		}else if (i==4) {
			return Locale.UK;
		}else if (i==5) {
			return Locale.FRANCE;
		}
		return Locale.CHINA;
	 }
	 
}
