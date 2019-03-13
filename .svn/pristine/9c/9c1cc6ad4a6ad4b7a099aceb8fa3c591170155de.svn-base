package com.lmxf.post.service.core;

import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.putils.service.ServiceException;

import com.lmxf.post.core.utils.*;
import com.lmxf.post.repository.config.ErrorCodeDao;

public abstract class TradeProcess {
	private final static Log log = LogFactory.getLog(TradeProcess.class);
	private String ret_code = "0000";
	protected ErrorCodeDao errorCodeDao;
	protected XmlValidator xmlValidator;

	public String tradePrepare(String apply_xml) {
		return tradeProcess(apply_xml);
	}

	public abstract String tradeProcess(String apply_xml);

	public String getRet_code() {
		return ret_code;
	}

	public void setRet_code(String ret_code) {
		this.ret_code = ret_code;
	}

	public XmlValidator getXmlValidator() {
		return xmlValidator;
	}

	public void setXmlValidator(XmlValidator xmlValidator) {
		this.xmlValidator = xmlValidator;
	}

	/**
	 * 校验null,"",及"null"
	 * 
	 * @param str
	 * @return
	 */
	public boolean validateEmpty(String str) {
		if (null == str || "".equals(str.trim()) || "null".equalsIgnoreCase(str.trim()))
			return true;
		else
			return false;
	}

	public void setErrorCodeDao(ErrorCodeDao errorCodeDao) {
		this.errorCodeDao = errorCodeDao;
	}

	public String getGenPriKey(int id) {
		int len = 7;
		StringBuffer buf = new StringBuffer();
		String tmp = "" + id;
		for (int i = 0; i < len - tmp.length(); i++)
			buf.append("0");
		buf.append(tmp);
		return buf.toString();
	}

}
