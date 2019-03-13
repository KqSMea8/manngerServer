package com.lmxf.post.repository.supply;

import java.util.List;
import org.apache.commons.putils.repository.BaseDao;
import com.lmxf.post.entity.supply.SupplyVOrder;
public class SupplyVOrderDao extends BaseDao {

	public List<SupplyVOrder> findAll(SupplyVOrder supplyVOrder) {
		List<SupplyVOrder> list = this.getSqlSession().selectList("SupplyVOrder.findAll",supplyVOrder);
		return list;
	}
	
	public SupplyVOrder findOneByVOrderId(String vOrderId) {
		SupplyVOrder supplyVOrderR = super.getSqlSession().selectOne("SupplyVOrder.findOneByVOrderId", vOrderId);
		return supplyVOrderR;
	}
	
	public int update(SupplyVOrder supplyVOrder) {
		return super.getSqlSession().update("SupplyVOrder.update", supplyVOrder);
	}
	
}
