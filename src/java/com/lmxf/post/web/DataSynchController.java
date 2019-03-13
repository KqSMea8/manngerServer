package com.lmxf.post.web;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

import com.lmxf.post.core.utils.GParameter;
import com.lmxf.post.core.web.BaseController;
import com.lmxf.post.service.core.ErrorCodeService;
import com.lmxf.post.webservice.impl.DataSynchProcess;

public class DataSynchController extends BaseController {
	private final static Log log = LogFactory.getLog(DataSynchController.class);
	private DataSynchProcess dataSynchProcess;
	private ErrorCodeService errorCodeService;

	public ModelAndView list(HttpServletRequest req, HttpServletResponse resp) {
		return new ModelAndView("success").addObject("resp_xml", "test XML");
	}

	public void report(HttpServletRequest req, HttpServletResponse resp) throws IllegalAccessException, InvocationTargetException {

		String xml;
		String report_xml = req.getParameter("report_xml");
		log.info("report_xml:" + report_xml);
		if (null == report_xml) {
			xml = errorCodeService.getErrorRespJson(1007);
			log.error("report_xml  is null!");
		}
		String clientIp = "127.0.0.1";
		String clientHostname = "localhost";
		if (null != req.getRemoteAddr())
			clientIp = req.getRemoteAddr();
		if (null != req.getRemoteHost())
			clientHostname = req.getRemoteHost();
		int clientPort = req.getRemotePort();
		dataSynchProcess.setRemoteAddress(clientHostname, clientIp, clientPort, GParameter.commProtocal_http);
		xml = dataSynchProcess.report(report_xml);
		if (null == xml) {
			xml = errorCodeService.getErrorRespJson(1007);
			log.error("dataSynchProcess.report is null!");
		}
		try {
			resp.setContentType("application/xml");
			resp.getWriter().write(xml);
		} catch (Exception e) {
			log.error("resp set is error!");
		}
		return;
	}

	public DataSynchProcess getDataSynchProcess() {
		return dataSynchProcess;
	}

	public void setDataSynchProcess(DataSynchProcess dataSynchProcess) {
		this.dataSynchProcess = dataSynchProcess;
	}

	public ErrorCodeService getErrorCodeService() {
		return errorCodeService;
	}

	public void setErrorCodeService(ErrorCodeService errorCodeService) {
		this.errorCodeService = errorCodeService;
	}
}
