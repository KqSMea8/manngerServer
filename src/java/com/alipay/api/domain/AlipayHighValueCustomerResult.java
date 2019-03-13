package com.alipay.api.domain;

import com.alipay.api.AlipayObject;
import com.alipay.api.internal.mapping.ApiField;

/**
 * 活跃高价值用户返回
 *
 * @author auto create
 * @since 1.0, 2016-06-02 14:27:08
 */
public class AlipayHighValueCustomerResult extends AlipayObject {

	private static final long serialVersionUID = 6446972776197645271L;

	/**
	 * Z0-Z7
	 */
	@ApiField("level")
	private String level;

	public String getLevel() {
		return this.level;
	}
	public void setLevel(String level) {
		this.level = level;
	}

}