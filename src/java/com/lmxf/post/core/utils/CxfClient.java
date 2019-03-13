package com.lmxf.post.core.utils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lmxf.post.entity.pay.PayconfigAlipay;
import com.lmxf.post.entity.pay.PayconfigWechat;

public class CxfClient {
	private final static Log log = LogFactory.getLog(CxfClient.class);
	private String hostnname = "localhost";
	private String ip = "127.0.0.1";
	private int port;
	@Resource
	WebServiceContext wsContext;

	public void getClientInfo() {

		try {
			javax.xml.ws.handler.MessageContext ctx = wsContext.getMessageContext();
			HttpServletRequest request = (HttpServletRequest) ctx.get(AbstractHTTPDestination.HTTP_REQUEST);
			if (null != request.getRemoteAddr())
				this.ip = request.getRemoteAddr();
			if (null != request.getRemoteHost())
				this.hostnname = request.getRemoteHost();
			this.port = request.getRemotePort();
			
		} catch (Exception e) {
			log.error("Can not get remote client IP," + e.getMessage());

		}
	}
	
	public void getClientInfo(PayconfigWechat payconfigWechat,PayconfigAlipay payconfigAlipay) {
		
		Message message = PhaseInterceptorChain.getCurrentMessage();
		HttpServletRequest request=null;
		if(null==message){
			request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                    .getRequest();
			if(request==null)
        	return;
        }
		HttpServletRequest req=null;
		if(request==null){
			req = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
		}
		if(null==req){
			req=request;
			if(req==null)
        	return;
        }		
		if(payconfigWechat != null){
			payconfigWechat.setContextPath(req.getContextPath());
			payconfigWechat.setPath(req.getRequestURL().toString().replace(req.getRequestURI(), "")+req.getContextPath());
		}
		if(payconfigAlipay != null){
			payconfigAlipay.setContextPath(req.getContextPath());
			payconfigAlipay.setPath(req.getRequestURL().toString().replace(req.getRequestURI(), "")+req.getContextPath());
		}
	}
	
	public String getPathInfo() {
		
		Message message = PhaseInterceptorChain.getCurrentMessage();
		HttpServletRequest request=null;
		if(null==message){
			request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                    .getRequest();
			if(request==null)
        	return "";
        }
		HttpServletRequest req=null;
		if(request==null){
			req = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
		}
		if(null==req){
			req=request;
			if(req==null)
        	return "";
        }	
		return req.getRequestURL().toString().replace(req.getRequestURI(), "")+req.getContextPath();
	}

	public String getHostnname() {
		return hostnname;
	}

	public void setHostnname(String hostnname) {
		this.hostnname = hostnname;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
