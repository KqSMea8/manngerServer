package com.lmxf.post.repository.vending;

import java.util.List;

import org.apache.commons.putils.repository.BaseDao;

import com.lmxf.post.entity.vending.VendingLane;

public class VendingLaneDao extends BaseDao {

	public VendingLane findOne(VendingLane vendingLane) {
		VendingLane vendingLaneR = super.getSqlSession().selectOne("VendingLane.findOne", vendingLane);
		return vendingLaneR;
	}
	
	public int insert(VendingLane vendingLane) {
		return super.getSqlSession().update("VendingLane.insert", vendingLane);
	}
	
	public List<VendingLane> findAll(VendingLane vendingLane) {
		List<VendingLane> vendingLaneList = this.getSqlSession().selectList("VendingLane.findAll", vendingLane);
		return vendingLaneList;
	}
	
	public int update(VendingLane vendingLane) {
		return super.getSqlSession().update("VendingLane.update", vendingLane);
	}
	
}
