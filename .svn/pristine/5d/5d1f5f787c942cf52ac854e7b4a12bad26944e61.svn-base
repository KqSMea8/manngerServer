package com.lmxf.post.repository.vending;

import java.util.List;

import com.lmxf.post.entity.vending.Vending;

import org.apache.commons.putils.repository.BaseDao;
import org.apache.commons.putils.web.Page;

public class VendingDao extends BaseDao {

	public List<Vending> findAll(Vending vending) {
		List<Vending> list = this.getSqlSession().selectList("Vending.findAll",vending);
		return list;
	}
	
	public List<Vending> findAll(Vending vending,Page page) {
		List<Vending> list = super.findDataPage("Vending.findAll",vending,page);
		return list;
	}
	public List<Vending> findAllLimit(Vending vending) {
		List<Vending> list = this.getSqlSession().selectList("Vending.findAllLimit",vending);
		return list;
	}
	
	
	public Vending findBySiteId(String siteId) {
		return this.getSqlSession().selectOne("Vending.findBySiteId", siteId);
	}
	public Vending findNetState(Vending vending) {
		return this.getSqlSession().selectOne("Vending.findNetState", vending);
	}
	
	public int update(Vending vending) {
		return super.getSqlSession().update("Vending.update", vending);
	}

}
