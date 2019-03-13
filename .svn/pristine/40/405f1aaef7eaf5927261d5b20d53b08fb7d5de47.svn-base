package com.lmxf.post.repository.order;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.putils.repository.BaseDao;
import org.apache.commons.putils.web.Page;

import com.lmxf.post.entity.order.OrderBox;

/**
 * 
 * @author wsj
 * 
 */
public class OrderBoxDao extends BaseDao {

	@SuppressWarnings("unchecked")
	public List<OrderBox> findAll(OrderBox orderBox, Page page) {
		List<OrderBox> orderBoxList = super.findDataPage("OrderBox.findAll", orderBox, page);
		return orderBoxList;
	}

	public List<OrderBox> findAll(OrderBox orderBox) {
		List<OrderBox> orderBoxList = this.getSqlSession().selectList("OrderBox.findAll", orderBox);
		return orderBoxList;
	}
	
	public List<OrderBox> findNum(OrderBox orderBox) {
		List<OrderBox> orderBoxList = this.getSqlSession().selectList("OrderBox.findNum", orderBox);
		return orderBoxList;
	}

	public OrderBox findOne(OrderBox orderBox) {
		OrderBox error = super.getSqlSession().selectOne("OrderBox.findOne", orderBox);
		return error;
	}
	public OrderBox findOneByProBoxId(OrderBox orderBox) {
		OrderBox error = super.getSqlSession().selectOne("OrderBox.findOneByProBoxId", orderBox);
		return error;
	}
	public OrderBox findBoxOpenState(OrderBox orderBox) {
		OrderBox error = super.getSqlSession().selectOne("OrderBox.findBoxOpenState", orderBox);
		return error;
	}

	public int insert(OrderBox orderBox) {
		int count = super.getSqlSession().insert("OrderBox.insert", orderBox);
		return count;
	}

	public int update(OrderBox orderBox) {
		int count = super.getSqlSession().update("OrderBox.update", orderBox);
		return count;
	}

	public int delete(OrderBox orderBox) {
		int count = super.getSqlSession().delete("OrderBox.delete", orderBox);
		return count;
	}
	
	public int findOutNum(OrderBox orderBox) {
		int id = super.getSqlSession().selectOne("OrderBox.findOutNum", orderBox);
		return id;
	}

	public OrderBox findOrderBox(OrderBox orderBox) {
		OrderBox error = super.getSqlSession().selectOne("OrderBox.findOrderBox", orderBox);
		return error;
	}
}
