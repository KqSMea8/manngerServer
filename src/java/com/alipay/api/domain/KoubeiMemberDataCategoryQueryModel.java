package com.alipay.api.domain;

import com.alipay.api.AlipayObject;
import com.alipay.api.internal.mapping.ApiField;

/**
 * 品类查询接口
 *
 * @author auto create
 * @since 1.0, 2016-07-06 10:47:52
 */
public class KoubeiMemberDataCategoryQueryModel extends AlipayObject {

	private static final long serialVersionUID = 1644567255257558627L;

	/**
	 * 父品类编码. (查询顶级类目时值传0)
	 */
	@ApiField("parent_id")
	private String parentId;

	public String getParentId() {
		return this.parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

}
