package com.alipay.api.domain;

import com.alipay.api.AlipayObject;
import com.alipay.api.internal.mapping.ApiField;

/**
 * 根据标签圈人的过滤器
 *
 * @author auto create
 * @since 1.0, 2016-07-27 19:38:37
 */
public class Filter extends AlipayObject {

	private static final long serialVersionUID = 4668137753437346352L;

	/**
	 * 标签组发圈人条件
	 */
	@ApiField("context")
	private LabelContext context;

	/**
	 * 过滤器模板，${a}是一个变量，会被context参数中的a参数替换，从而展开为最终的表达式，template最多支持两个参数，支持and及or连接符。
and：同时满足条件；
or：只需满足其中一个条件
	 */
	@ApiField("template")
	private String template;

	public LabelContext getContext() {
		return this.context;
	}
	public void setContext(LabelContext context) {
		this.context = context;
	}

	public String getTemplate() {
		return this.template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}

}