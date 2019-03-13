package com.lmxf.post.repository.vending;

import java.util.List;

import com.lmxf.post.entity.vending.VendingLsdiffer;

import org.apache.commons.putils.repository.BaseDao;
public class VendingLsdifferDao extends BaseDao {

	public List<VendingLsdiffer> findAll(VendingLsdiffer vendingLsdiffer) {
		List<VendingLsdiffer> list = this.getSqlSession().selectList("VendingLsdiffer.findAll", vendingLsdiffer);
		return list;
	}
	
	public VendingLsdiffer update(VendingLsdiffer vendingLsdiffer) {
		VendingLsdiffer list = this.getSqlSession().selectOne("VendingLsdiffer.update", vendingLsdiffer);
		return list;
	}
	
	public VendingLsdiffer findOne(VendingLsdiffer vendingLsdiffer) {
		VendingLsdiffer list = this.getSqlSession().selectOne("VendingLsdiffer.findOne", vendingLsdiffer);
		return list;
	}

	public int insert(VendingLsdiffer vendingLsdiffer) {
		return super.getSqlSession().update("VendingLsdiffer.insert", vendingLsdiffer);
	}

}
