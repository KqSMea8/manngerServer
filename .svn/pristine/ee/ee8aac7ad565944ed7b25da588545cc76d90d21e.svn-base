package com.lmxf.post.repository.product;

import java.util.List;
import org.apache.commons.putils.repository.BaseDao;
import org.apache.commons.putils.web.Page;

import com.lmxf.post.entity.product.ProductVUnder;

public class ProductVUnderDao extends BaseDao {
	
	public List<ProductVUnder> findAll(ProductVUnder productUnder) {
		List<ProductVUnder> list = super.getSqlSession().selectList("ProductVUnder.findAll",productUnder);
		return list;
	
	}
	public List<ProductVUnder> findPageGroupLine(ProductVUnder productUnder) {
		List<ProductVUnder> list = super.getSqlSession().selectList("ProductVUnder.findPageGroupLine",productUnder);
		return list;
	}
	
	public int update(ProductVUnder productVUnder) {
		return super.getSqlSession().update("ProductVUnder.update", productVUnder);
	}
	public ProductVUnder findOneByVUnderId(String vUnderId) {
		return super.getSqlSession().selectOne("ProductVUnder.findOneByVUnderId", vUnderId);
	}
	
}
