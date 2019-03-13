package com.lmxf.post.service.core;

import org.apache.commons.putils.service.ServiceException;

import com.lmxf.post.entity.pay.PayconfigWechat;
import com.lmxf.post.repository.pay.PayconfigWechatDao;

public class PayconfigWechatService {
	private PayconfigWechatDao payconfigWechatDao;

	public void setPayconfigWechatDao(PayconfigWechatDao payconfigWechatDao) {
		this.payconfigWechatDao = payconfigWechatDao;
	}

	public PayconfigWechat findOneByCorpId(String corpId) {
		PayconfigWechat payconfigWechat;
		try {
			payconfigWechat = payconfigWechatDao.findOneByCorpId(corpId);
		} catch (Exception e) {
			throw new ServiceException("根据条件查找下单信息出错！", e);
		}
		return payconfigWechat;
	}
}
