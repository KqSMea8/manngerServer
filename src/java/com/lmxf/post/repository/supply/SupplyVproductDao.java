package com.lmxf.post.repository.supply;

import java.util.List;

import org.apache.commons.putils.repository.BaseDao;

import com.lmxf.post.entity.supply.SupplyVproduct;

public class SupplyVproductDao extends BaseDao {

	public List<SupplyVproduct> findAll(SupplyVproduct supplyVproduct) {
		List<SupplyVproduct> list = this.getSqlSession().selectList("SupplyVproduct.findAll",supplyVproduct);
		return list;
	}

	public SupplyVproduct findOne(SupplyVproduct supplyVproduct) {
		SupplyVproduct supplyVproductR = super.getSqlSession().selectOne("SupplyVproduct.findOne", supplyVproduct);
		return supplyVproductR;
	}
	
	public SupplyVproduct findSupplyInfo(SupplyVproduct supplyVproduct) {
		SupplyVproduct supplyVproductR = super.getSqlSession().selectOne("SupplyVproduct.findSupplyInfo", supplyVproduct);
		return supplyVproductR;
	}
	
	public List<SupplyVproduct> findSiteInfoBySOrderId(String sOrderId) {
		List<SupplyVproduct> list = this.getSqlSession().selectList("SupplyVproduct.findSiteInfoBySOrderId",sOrderId);
		return list;
	}
	
	public List<SupplyVproduct> findProductBySOrderId(String sOrderId) {
		List<SupplyVproduct> list = this.getSqlSession().selectList("SupplyVproduct.findProductBySOrderId",sOrderId);
		return list;
	}
	
	public List<SupplyVproduct> findNoOutVproduct(SupplyVproduct supplyVproduct) {
		List<SupplyVproduct> list = this.getSqlSession().selectList("SupplyVproduct.findNoOutVproduct",supplyVproduct);
		return list;
	}
	
	public List<SupplyVproduct> findNoOutVproductAll(SupplyVproduct supplyVproduct) {
		List<SupplyVproduct> list = this.getSqlSession().selectList("SupplyVproduct.findNoOutVproductAll",supplyVproduct);
		return list;
	}
	
	
	public int update(SupplyVproduct supplyVproduct) {
		return super.getSqlSession().update("SupplyVproduct.update", supplyVproduct);
	}
	
	public SupplyVproduct findSupplyTotalBySiteId(String siteId) {
		SupplyVproduct supplyVproductR = super.getSqlSession().selectOne("SupplyVproduct.findSupplyTotalBySiteId", siteId);
		return supplyVproductR;
	}
	
	public int insert(SupplyVproduct supplyVproduct) {
		return super.getSqlSession().insert("SupplyVproduct.insert", supplyVproduct);
	}
	
	public List<SupplyVproduct> findSupplyVproduct(SupplyVproduct supplyVproduct) {
		List<SupplyVproduct> supplyVproductR = super.getSqlSession().selectList("SupplyVproduct.findSupplyVproduct", supplyVproduct);
		return supplyVproductR;
	}
	
	public List<SupplyVproduct> findSupplyVproductByLane(SupplyVproduct supplyVproduct) {
		List<SupplyVproduct> supplyVproductR = super.getSqlSession().selectList("SupplyVproduct.findSupplyVproductByLane", supplyVproduct);
		return supplyVproductR;
	}
	
	
}
