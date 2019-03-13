package com.lmxf.post.repository.supply;

import java.util.List;

import org.apache.commons.putils.repository.BaseDao;
import org.apache.commons.putils.web.Page;

import com.lmxf.post.entity.supply.SupplyConfig;
import com.lmxf.post.entity.supply.SupplyOrder;

public class SupplyConfigDao extends BaseDao {

	public List<SupplyConfig> findAll(SupplyConfig supplyConfig) {
		List<SupplyConfig> list = this.getSqlSession().selectList("SupplyConfig.findAll",supplyConfig);
		return list;
	}
	
	public SupplyConfig findOneBySupplyId(String supplyId) {
		SupplyConfig supplyConfig = super.getSqlSession().selectOne("SupplyConfig.findOneBySupplyId", supplyId);
		return supplyConfig;
	}
	
	public int update(SupplyConfig supplyConfig) {
		return super.getSqlSession().update("SupplyConfig.update", supplyConfig);
	}
	
	@SuppressWarnings("unchecked")
	public List<SupplyConfig> findSupplyConfig(SupplyConfig supplyConfig, Page page) {
		List<SupplyConfig> list = super.findDataPage("SupplyConfig.findSupplyConfig",supplyConfig, page);
		return list;
	}
}
