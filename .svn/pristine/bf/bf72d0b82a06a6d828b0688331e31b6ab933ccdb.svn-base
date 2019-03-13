package com.lmxf.post.repository.vending;

import java.util.List;

import com.lmxf.post.entity.vending.Vending;
import com.lmxf.post.entity.vending.VendingStock;

import org.apache.commons.putils.repository.BaseDao;
import org.apache.commons.putils.web.Page;

public class VendingStockDao extends BaseDao {

	@SuppressWarnings("unchecked")
	public List<VendingStock> findAll(VendingStock vendingStock, Page page) {
		List<VendingStock> list = super.findDataPage("VendingStock.findAll",vendingStock, page);
		return list;
	}
	
	public List<VendingStock> findAll(VendingStock vendingStock) {
		List<VendingStock> list =super.getSqlSession().selectList("VendingStock.findAll",vendingStock);
		return list;
	}

	public VendingStock findOne(VendingStock vendingStock) {
		VendingStock error = super.getSqlSession().selectOne("VendingStock.findOne", vendingStock);
		return error;
	}
	
	public int update(VendingStock vendingStock) {
		return super.getSqlSession().update("VendingStock.update", vendingStock);
	}
	
	public int insert(VendingStock vendingStock) {
		return super.getSqlSession().insert("VendingStock.insert", vendingStock);
	}
	
	public int delete(VendingStock vendingStock) {
		return super.getSqlSession().insert("VendingStock.delete", vendingStock);
	}
	
	public List<VendingStock> findBySiteId(String siteId) {
		List<VendingStock> list =super.getSqlSession().selectList("VendingStock.findBySiteId",siteId);
		return list;
	}
}
