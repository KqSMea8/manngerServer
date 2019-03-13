package com.lmxf.post.repository.product;

import java.util.List;

import org.apache.commons.putils.repository.BaseDao;
import org.apache.commons.putils.web.Page;

import com.lmxf.post.entity.product.ProductClassify;
import com.lmxf.post.entity.product.ProductInfo;
import com.lmxf.post.entity.vending.VendingStock;

public class ProductClassifyDao extends BaseDao{

	public ProductClassify findOne(ProductClassify productClassify) {
		return super.getSqlSession().selectOne("ProductClassify.findOne", productClassify);
	}
	@SuppressWarnings("unchecked")
	public List<ProductClassify> findAll(ProductClassify productClassify, Page page) {
		List<ProductClassify> list = super.findDataPage("ProductClassify.findDataPage",productClassify, page);
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<ProductClassify> findAll() {
		List<ProductClassify> list =this.getSqlSession().selectList("ProductClassify.findDataPage");
		return list;
	}
	
}
