package com.lmxf.post.repository.vending;

import java.util.List;

import org.apache.commons.putils.repository.BaseDao;
import org.apache.commons.putils.web.Page;

import com.lmxf.post.entity.vending.VendingPerson;

public class VendingPersonDao extends BaseDao {
	
	public List<VendingPerson> findAll(VendingPerson vendingPerson) {
		List<VendingPerson> list = this.getSqlSession().selectList("VendingPerson.findAll",vendingPerson);
		return list;
	}

	public List<VendingPerson> findAllIssuedAll(VendingPerson vendingPerson,Page page) {
		List<VendingPerson> list = super.findDataPage("VendingPerson.findAllIssuedAll", vendingPerson,page);
		return list;
	}
	
	public int update(VendingPerson vendingPerson) {
		return super.getSqlSession().update("VendingPerson.update", vendingPerson);
	}
	
	public VendingPerson findOne(VendingPerson vendingPerson) {
		VendingPerson vendingPersonR = super.getSqlSession().selectOne("VendingPerson.findOne", vendingPerson);
		return vendingPersonR;
	}
	
	public int updateDownTime(VendingPerson vendingPerson) {
		int count = super.getSqlSession().update("VendingPerson.updateDownTime", vendingPerson);
		return count;
	}
	
	public int insert(VendingPerson vendingPerson) {
		return super.getSqlSession().insert("VendingPerson.insert", vendingPerson);
	}
}
