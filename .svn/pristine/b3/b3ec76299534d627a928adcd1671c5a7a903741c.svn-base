package com.lmxf.post.repository.supply;

import java.util.List;

import org.apache.commons.putils.repository.BaseDao;
import org.apache.commons.putils.web.Page;

import com.lmxf.post.entity.supply.SupplyOrder;

public class SupplyOrderDao extends BaseDao {

	@SuppressWarnings("unchecked")
	public List<SupplyOrder> findAll(SupplyOrder supplyOrder, Page page) {
		List<SupplyOrder> list = super.findDataPage("SupplyOrder.findAll",supplyOrder, page);
		return list;
	}
	
	public List<SupplyOrder> findAll(SupplyOrder supplyOrder) {
		List<SupplyOrder> list = super.getSqlSession().selectList("SupplyOrder.findAll",supplyOrder);
		return list;
	}
	
	public SupplyOrder findOneBySOrderId(String sOrderId) {
		SupplyOrder supplyOrder = super.getSqlSession().selectOne("SupplyOrder.findOneBySOrderId", sOrderId);
		return supplyOrder;
	}
	
	public int update(SupplyOrder supplyOrder) {
		return super.getSqlSession().update("SupplyOrder.update", supplyOrder);
	}
	
	public int insert(SupplyOrder supplyOrder) {
		return super.getSqlSession().insert("SupplyOrder.insert", supplyOrder);
	}
}
