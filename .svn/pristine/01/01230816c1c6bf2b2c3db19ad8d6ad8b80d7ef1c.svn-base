package com.lmxf.post.repository.vending;

import org.apache.commons.putils.repository.BaseDao;

import com.lmxf.post.entity.vending.VendingState;

public class VendingStateDao extends BaseDao {

	public VendingState findBySiteId(String siteId) {
		return this.getSqlSession().selectOne("VendingState.findBySiteId", siteId);
	}
	
	public int update(VendingState vendingState) {
		return super.getSqlSession().update("VendingState.update", vendingState);
	}
	
	public int insert(VendingState vendingState) {
		return super.getSqlSession().update("VendingState.insert", vendingState);
	}
	
	public int insertvendingState(VendingState vendingState) {
		return super.getSqlSession().update("VendingState.insertvendingState", vendingState);
	}
}
