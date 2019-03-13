package com.alipay.api.response;

import com.alipay.api.internal.mapping.ApiField;

import com.alipay.api.AlipayResponse;

/**
 * ALIPAY API: alipay.open.app.codetesttest response.
 * 
 * @author auto create
 * @since 1.0, 2016-05-11 19:52:52
 */
public class AlipayOpenAppCodetesttestResponse extends AlipayResponse {

	private static final long serialVersionUID = 5757711396928654742L;

	/** 
	 * 测试测试测试
	 */
	@ApiField("testtesttest")
	private String testtesttest;

	public void setTesttesttest(String testtesttest) {
		this.testtesttest = testtesttest;
	}
	public String getTesttesttest( ) {
		return this.testtesttest;
	}

}
