package com.lmxf.post.webservice.impl;

import javax.jws.WebService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lmxf.post.core.utils.CxfClient;
import com.lmxf.post.core.utils.GParameter;
import com.lmxf.post.core.utils.SpringContextUtil;
import com.lmxf.post.webservice.SiteEntry;
import com.lmxf.post.webservice.WsConstants;

/**
 * 上报接口
 * @author Administrator
 *
 */
@WebService(serviceName = "SiteEntry", endpointInterface = "com.lmxf.post.webservice.SiteEntry", targetNamespace = WsConstants.NS)
public class SiteEntryImpl implements SiteEntry {
	private final static Log log = LogFactory.getLog(SiteEntryImpl.class);
	private SiteProcess siteProcess;
	private CxfClient cxfClient;

	private SpringContextUtil springContextUtil;

	public String report(String report_xml) {
		// CxfClient cxfClient = new CxfClient();
		// ======================报文解密开始====================

		cxfClient.getClientInfo();
		siteProcess = (SiteProcess) springContextUtil.getBean("siteProcess");
		siteProcess.setRemoteAddress(cxfClient.getHostnname(), cxfClient.getIp(), cxfClient.getPort(), GParameter.commProtocal_soap);
		String resp_report_xml = siteProcess.report(report_xml);

		return resp_report_xml;
		// =========================报文解密开始=========================
	}

	public String errorXML(String errorCode) {
		StringBuffer xml = new StringBuffer();
		xml.append("<ZMSG>");
		xml.append("<ZHEAD>");
		xml.append("<retcode>" + errorCode + "</retcode>");
		xml.append("<retmsg></retmsg>");
		xml.append("<totnum>1</totnum>");
		xml.append("<curnum>1</curnum>");
		xml.append("</ZHEAD>");
		xml.append("<ZBODY></ZBODY>");
		xml.append("</ZMSG>");
		return xml.toString();
	}
	
	public SiteProcess getSiteProcess() {
		return siteProcess;
	}

	public void setSiteProcess(SiteProcess siteProcess) {
		this.siteProcess = siteProcess;
	}

	public CxfClient getCxfClient() {
		return cxfClient;
	}

	public void setCxfClient(CxfClient cxfClient) {
		this.cxfClient = cxfClient;
	}

	public SpringContextUtil getSpringContextUtil() {
		return springContextUtil;
	}

	public void setSpringContextUtil(SpringContextUtil springContextUtil) {
		this.springContextUtil = springContextUtil;
	}
}
