package com.lmxf.post.repository.product;

import java.util.List;

import org.apache.commons.putils.repository.BaseDao;
import org.apache.commons.putils.web.Page;

import com.lmxf.post.entity.product.ProductUnder;
import com.lmxf.post.entity.supply.SupplyOrder;

public class ProductUnderDao extends BaseDao {

	@SuppressWarnings("unchecked")
	public List<ProductUnder> findAll(ProductUnder productUnder, Page page) {
		List<ProductUnder> list = super.findDataPage("ProductUnder.findAll",productUnder, page);
		return list;
	}
	
	public List<ProductUnder> findAll(ProductUnder productUnder) {
		List<ProductUnder> list = super.getSqlSession().selectList("ProductUnder.findAll",productUnder);
		return list;
	}
	
	public ProductUnder findOneByUnderId(String underId) {
		ProductUnder productUnder = super.getSqlSession().selectOne("ProductUnder.findOneByUnderId", underId);
		return productUnder;
	}
	
	public int update(ProductUnder productUnder) {
		return super.getSqlSession().update("ProductUnder.update", productUnder);
	}
	
	public List<ProductUnder> findByLineId(ProductUnder productUnder) {
		List<ProductUnder> list = super.getSqlSession().selectList("ProductUnder.findByLineId",productUnder);
		return list;
	}
}
