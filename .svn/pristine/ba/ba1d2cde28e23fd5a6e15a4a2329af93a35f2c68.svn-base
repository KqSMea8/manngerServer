package com.lmxf.post.repository.product;

import java.util.List;

import org.apache.commons.putils.repository.BaseDao;
import org.apache.commons.putils.web.Page;

import com.lmxf.post.entity.product.ProductInfo;

public class ProductInfoDao extends BaseDao {

	public ProductInfo findByProductId(String productId) {
		return this.getSqlSession().selectOne("ProductInfo.findByProductId", productId);
	}
	
	public ProductInfo findProductInfo(ProductInfo productInfo) {
		return super.getSqlSession().selectOne("ProductInfo.findProductInfo", productInfo);
	}
	@SuppressWarnings("unchecked")
	public List<ProductInfo> findAll(ProductInfo productInfo, Page page) {
		List<ProductInfo> list = super.findDataPage("ProductInfo.findDataPage",productInfo, page);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<ProductInfo> findAll() {
		List<ProductInfo> list = this.getSqlSession().selectList("ProductInfo.findDataPage");
		return list;
	}
	
	public ProductInfo findByProductCode(String productCode) {
		return this.getSqlSession().selectOne("ProductInfo.findByProductCode", productCode);
	}
}
