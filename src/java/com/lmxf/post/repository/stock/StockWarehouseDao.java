package com.lmxf.post.repository.stock;

import com.lmxf.post.entity.stock.StockWarehouse;

import org.apache.commons.putils.repository.BaseDao;

public class StockWarehouseDao extends BaseDao {
	
	public int update(StockWarehouse stockWarehouse) {
		return super.getSqlSession().update("StockWarehouse.update", stockWarehouse);
	}

	public StockWarehouse findByProductId(StockWarehouse stockWarehouse) {
		return super.getSqlSession().selectOne("StockWarehouse.findByProductId",stockWarehouse);
		
	}
	
	public int updateAddStock(StockWarehouse stockWarehouse) {
		return super.getSqlSession().update("StockWarehouse.updateAddStock", stockWarehouse);
	}

	
}
