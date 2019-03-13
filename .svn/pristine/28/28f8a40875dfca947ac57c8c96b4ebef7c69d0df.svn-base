package com.lmxf.post.repository.vending;

import org.apache.commons.putils.repository.BaseDao;

import com.lmxf.post.entity.vending.VendingEvent;

public class VendingEventDao extends BaseDao {
	
	public VendingEvent findOne(VendingEvent vendingEvent) {
		VendingEvent vendingEventR = super.getSqlSession().selectOne("VendingEvent.findOne", vendingEvent);
		return vendingEventR;
	}
	
	public int insert(VendingEvent vendingEvent) {
		return super.getSqlSession().update("VendingEvent.insert", vendingEvent);
	}
}
