package com.lmxf.post.repository.supply;

import java.util.List;

import org.apache.commons.putils.repository.BaseDao;

import com.lmxf.post.entity.supply.SupplyProduct;

public class SupplyProductDao extends BaseDao {

	public List<SupplyProduct> findAll(SupplyProduct supplyProduct) {
		List<SupplyProduct> list = this.getSqlSession().selectList("SupplyProduct.findAll",supplyProduct);
		return list;
	}
	
	public SupplyProduct findOne(SupplyProduct supplyProduct) {
		SupplyProduct supplyProductR = super.getSqlSession().selectOne("SupplyProduct.findOne", supplyProduct);
		return supplyProductR;
	}
	
	public int update(SupplyProduct supplyProduct) {
		return super.getSqlSession().update("SupplyProduct.update", supplyProduct);
	}
	
	public int insert(SupplyProduct supplyProduct) {
		return super.getSqlSession().insert("SupplyProduct.insert", supplyProduct);
	}
}
