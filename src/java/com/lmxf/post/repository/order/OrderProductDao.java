package com.lmxf.post.repository.order;

import java.util.List;

import org.apache.commons.putils.repository.BaseDao;
import org.apache.commons.putils.web.Page;

import com.lmxf.post.entity.order.OrderProduct;

/**
 * 
 * @author wsj
 * 
 */
public class OrderProductDao extends BaseDao {

	@SuppressWarnings("unchecked")
	public List<OrderProduct> findAll(OrderProduct orderProduct, Page page) {
		List<OrderProduct> orderProductList = super.findDataPage("OrderProduct.findAll", orderProduct, page);
		return orderProductList;
	}

	public List<OrderProduct> findAll(OrderProduct orderProduct) {
		List<OrderProduct> orderProductList = this.getSqlSession().selectList("OrderProduct.findAll", orderProduct);
		return orderProductList;
	}

	public OrderProduct findOne(OrderProduct orderProduct) {
		OrderProduct error = super.getSqlSession().selectOne("OrderProduct.findOne", orderProduct);
		return error;
	}

	public int insert(OrderProduct orderProduct) {
		int count = super.getSqlSession().insert("OrderProduct.insert", orderProduct);
		return count;
	}

	public int update(OrderProduct orderProduct) {
		int count = super.getSqlSession().update("OrderProduct.update", orderProduct);
		return count;
	}

	public int delete(OrderProduct orderProduct) {
		int count = super.getSqlSession().delete("OrderProduct.delete", orderProduct);
		return count;
	}

}
