package com.lmxf.post.repository.product;

import org.apache.commons.putils.repository.BaseDao;

import com.lmxf.post.entity.product.ProductOnline;

public class ProductOnlineDao extends BaseDao {

	public ProductOnline findByProductId(String productId) {
		return this.getSqlSession().selectOne("ProductOnline.findByProductId", productId);
	}
}
