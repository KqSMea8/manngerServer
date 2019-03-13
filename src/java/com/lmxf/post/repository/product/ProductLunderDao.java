package com.lmxf.post.repository.product;

import java.util.List;

import org.apache.commons.putils.repository.BaseDao;

import com.lmxf.post.entity.product.ProductLunder;

public class ProductLunderDao extends BaseDao {

	public List<ProductLunder> findAll(ProductLunder productLunder) {
		List<ProductLunder> list = this.getSqlSession().selectList("ProductLunder.findAll",productLunder);
		return list;
	}

	public ProductLunder findOne(ProductLunder productLunder) {
		ProductLunder productLunderR = super.getSqlSession().selectOne("ProductLunder.findOne", productLunder);
		return productLunderR;
	}
	
	public int insert(ProductLunder productLunder) {
		return super.getSqlSession().insert("ProductLunder.insert", productLunder);
	}
	public int update(ProductLunder productLunder) {
		return super.getSqlSession().update("ProductLunder.update", productLunder);
	}
}
