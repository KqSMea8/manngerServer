package com.alipay.api.domain;

import com.alipay.api.AlipayObject;
import com.alipay.api.internal.mapping.ApiField;

/**
 * 优惠活动查看
 *
 * @author auto create
 * @since 1.0, 2016-08-01 14:25:48
 */
public class AlipayMarketingCampaignDstcampQueryModel extends AlipayObject {

	private static final long serialVersionUID = 7893253252519333578L;

	/**
	 * 活动id
	 */
	@ApiField("camp_id")
	private String campId;

	public String getCampId() {
		return this.campId;
	}
	public void setCampId(String campId) {
		this.campId = campId;
	}

}
