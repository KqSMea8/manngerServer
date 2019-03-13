package com.lmxf.post.repository.vending;

import java.util.List;

import com.lmxf.post.entity.vending.VendingLanep;
import com.lmxf.post.entity.vending.VendingStock;

import org.apache.commons.putils.repository.BaseDao;

public class VendingLanepDao extends BaseDao {

	public List<VendingLanep> findAll(VendingLanep vendingLanep) {
		List<VendingLanep> list = this.getSqlSession().selectList("VendingLanep.findAll",vendingLanep);
		return list;
	}
	
	public VendingLanep findOne(VendingLanep vendingLanep) {
		VendingLanep error = super.getSqlSession().selectOne("VendingLanep.findOne", vendingLanep);
		return error;
	}
	
	public VendingLanep findOneCabinet(VendingLanep vendingLanep) {
		VendingLanep error = super.getSqlSession().selectOne("VendingLanep.findOneCabinet", vendingLanep);
		return error;
	}
	
	public VendingLanep findOneSCM(VendingLanep vendingLanep) {
		VendingLanep error = super.getSqlSession().selectOne("VendingLanep.findOneSCM", vendingLanep);
		return error;
	}
	
	public int update(VendingLanep vendingLanep) {
		return super.getSqlSession().update("VendingLanep.update", vendingLanep);
	}
	public int updateUnder(VendingLanep vendingLanep) {
		return super.getSqlSession().update("VendingLanep.updateUnder", vendingLanep);
	}
	
	public List<VendingLanep> findAllCabinet(VendingLanep vendingLanep) {
		List<VendingLanep> list = this.getSqlSession().selectList("VendingLanep.findAllCabinet",vendingLanep);
		return list;
	}
	public List<VendingLanep> findBySiteId(String siteId) {
		List<VendingLanep> list =super.getSqlSession().selectList("VendingLanep.findBySiteId",siteId);
		return list;
	}
	public List<VendingLanep> findByProductId(String siteId) {
		List<VendingLanep> list =super.getSqlSession().selectList("VendingLanep.findBySiteId",siteId);
		return list;
	}
}
