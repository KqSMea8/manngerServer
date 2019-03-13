package com.lmxf.post.repository.stock;

import com.lmxf.post.entity.stock.StockProduct;

import org.apache.commons.putils.repository.BaseDao;

public class StockProductDao extends BaseDao {
	
	public int update(StockProduct stockProduct) {
		return super.getSqlSession().update("StockProduct.update", stockProduct);
	}

	public StockProduct findByProductId(String productId) {
		return this.getSqlSession().selectOne("StockProduct.findByProductId",productId);
	}
	
	public int updateAddStock(StockProduct stockProduct) {
		return super.getSqlSession().update("StockProduct.updateAddStock", stockProduct);
	}
	
}
