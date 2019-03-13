package com.lmxf.post.repository.supply;

import java.util.List;

import org.apache.commons.putils.repository.BaseDao;

import com.lmxf.post.entity.supply.SupplyVending;

public class SupplyVendingDao extends BaseDao {

	public List<SupplyVending> findAll(SupplyVending supplyVending) {
		List<SupplyVending> list = this.getSqlSession().selectList("SupplyVending.findAll",supplyVending);
		return list;
	}
	
	public SupplyVending findOne(SupplyVending supplyVending) {
		SupplyVending supplyVendingR = super.getSqlSession().selectOne("SupplyVending.findOne", supplyVending);
		return supplyVendingR;
	}
	
	public int update(SupplyVending supplyVending) {
		return super.getSqlSession().update("SupplyVending.update", supplyVending);
	}
}
