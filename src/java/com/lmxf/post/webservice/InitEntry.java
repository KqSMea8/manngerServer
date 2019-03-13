package com.lmxf.post.webservice;

import javax.jws.WebService;

/**
 * 售货机初始化接口
 * @author Administrator
 *
 */
@WebService(name = "InitEntry", targetNamespace = WsConstants.NS)
public interface InitEntry {
	public String report(String apply_xml);
}
