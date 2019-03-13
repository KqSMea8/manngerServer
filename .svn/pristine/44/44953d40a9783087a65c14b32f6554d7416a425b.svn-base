package com.lmxf.post.repository.pay;

import org.apache.commons.putils.repository.BaseDao;

import com.lmxf.post.entity.pay.PayconfigWechat;

public class PayconfigWechatDao extends BaseDao {

	public PayconfigWechat findOneByCorpId(String corpId) {
		return this.getSqlSession().selectOne("PayconfigWechat.findOneByCorpId", corpId);
	}
}
