package com.alipay.api.domain;

import java.util.Date;

import com.alipay.api.AlipayObject;
import com.alipay.api.internal.mapping.ApiField;

/**
 * 支用放款流水
 *
 * @author auto create
 * @since 1.0, 2016-03-29 11:33:16
 */
public class LendingRecords extends AlipayObject {

	private static final long serialVersionUID = 7735666757849395148L;

	/**
	 * 放款时间，精确到天
	 */
	@ApiField("date")
	private Date date;

	/**
	 * 放款流水描述
	 */
	@ApiField("remark")
	private String remark;

	/**
	 * 放款额度，精确到小数点2位，单位（元）
	 */
	@ApiField("total_amount")
	private String totalAmount;

	public Date getDate() {
		return this.date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	public String getRemark() {
		return this.remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTotalAmount() {
		return this.totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

}
