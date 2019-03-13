package com.lmxf.post.repository.order;

import org.apache.commons.putils.repository.BaseDao;
import org.apache.commons.putils.web.Page;
import com.lmxf.post.entity.order.OrderChange;
import java.util.*;

public class OrderChangeDao extends BaseDao {
	public int insert(OrderChange orderChange) {
		return super.getSqlSession().insert("OrderChange.insert", orderChange);
	}

	@SuppressWarnings("unchecked")
	public List<OrderChange> findDataPage(OrderChange orderChange, Page pager) {
		return super.findDataPage("OrderChange.findDataPage", orderChange, pager);
	}

	public List<OrderChange> findAll(OrderChange orderChange) {
		return super.getSqlSession().selectList("OrderChange.findAll", orderChange);
	}

	public List<OrderChange> findByOperTime(OrderChange bg) {
		return super.getSqlSession().selectList("OrderChange.findByOperTime", bg);
	}
}
