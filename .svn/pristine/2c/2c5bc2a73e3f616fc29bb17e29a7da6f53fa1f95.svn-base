package com.lmxf.post.repository.pay;

import org.apache.commons.putils.repository.BaseDao;

import com.lmxf.post.entity.pay.PayconfigAlipay;

public class PayconfigAlipayDao extends BaseDao {

	public PayconfigAlipay findOneByCorpId(String corpId) {
		return this.getSqlSession().selectOne("PayconfigAlipay.findOneByCorpId", corpId);
	}
}
