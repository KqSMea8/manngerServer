package com.lmxf.post.webservice;

import javax.jws.WebService;

/**
 * 开放给第三方购买系统的接口
 * @author Administrator
 *
 */
@WebService(name = "PartnerEntry", targetNamespace = WsConstants.NS)
public interface PartnerEntry {
	public String report(String apply_xml);
}
