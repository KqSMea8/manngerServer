package com.lmxf.post.repository.order;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.putils.repository.BaseDao;
import org.apache.commons.putils.web.Page;

import com.lmxf.post.entity.order.OrderApply;

public class OrderApplyDao extends BaseDao {
	@SuppressWarnings("unchecked")
	public List<OrderApply> findAll(Page page, OrderApply nsOrderApply){
		List<OrderApply> list = super.findDataPage("OrderApply.findDataPage", nsOrderApply, page);
		return list;
	}
	public List<OrderApply> findAll(OrderApply nsOrderApply){
		List<OrderApply> list = super.getSqlSession().selectList("OrderApply.findDataPage", nsOrderApply);
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<OrderApply> findCustomerOrder(Page page,OrderApply nsOrderApply){
		List<OrderApply> list = super.findDataPage("OrderApply.findCustomerOrder", nsOrderApply,page);
		return list;
	}
	
	public OrderApply findOneByOrderId(OrderApply orderApplyP) {
		OrderApply orderApply = super.getSqlSession().selectOne("OrderApply.findOneByOrderId", orderApplyP);
		return orderApply;
	}
	
	public OrderApply findOneByTradeNo(OrderApply orderApplyP) {
		OrderApply orderApply = super.getSqlSession().selectOne("OrderApply.findOneByTradeNo", orderApplyP);
		return orderApply;
	}
	
	public OrderApply findCountByDate(OrderApply orderApplyP) {
		OrderApply orderApply = super.getSqlSession().selectOne("OrderApply.findCountByDate", orderApplyP);
		return orderApply;
	}
	
	
	
	
	
	public List<OrderApply> findByCreateTime(OrderApply obj){
		return super.getSqlSession().selectList("OrderApply.findByCreateTime", obj);
	}
	
	public OrderApply findOne(OrderApply orderApply) {
		OrderApply orderApplyR = super.getSqlSession().selectOne("OrderApply.findOne", orderApply);
		return orderApplyR;
	}
	
	public OrderApply findBoxOpenState(OrderApply orderApply) {
		OrderApply orderApplyR = super.getSqlSession().selectOne("OrderApply.findBoxOpenState", orderApply);
		return orderApplyR;
	}
	
	public OrderApply findOneByTorderId(OrderApply orderApply) {
		OrderApply orderApplyR = super.getSqlSession().selectOne("OrderApply.findOneByTorderId", orderApply);
		return orderApplyR;
	}
	
	public int insert(OrderApply orderApply) {
		return super.getSqlSession().insert("OrderApply.insert", orderApply);
	}
	
	public int update(OrderApply orderApply) {
		return super.getSqlSession().update("OrderApply.update", orderApply);
	}
	
	public int updateDownTime(OrderApply orderApply) {
		int count = super.getSqlSession().update("OrderApply.updateDownTime", orderApply);
		return count;
	}
	
	public int updateByNotify(OrderApply vo)  {
		int count = super.getSqlSession().update("OrderApply.updateByNotify", vo);
		return count;
	}
	public int updateByNotifyRefund(OrderApply vo)  {
		int count = super.getSqlSession().update("OrderApply.updateByNotifyRefund", vo);
		return count;
	}
	
}
