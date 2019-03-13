package com.lmxf.post.repository.vending;

import java.util.List;

import com.lmxf.post.entity.vending.VendingLsdhandle;

import org.apache.commons.putils.repository.BaseDao;

public class VendingLsdhandleDao extends BaseDao {

	public List<VendingLsdhandle> findAll(VendingLsdhandle vendingLsdhandle) {
		List<VendingLsdhandle> list = this.getSqlSession().selectList("VendingLsdhandle.findAll", vendingLsdhandle);
		return list;
	}

	public int insert(VendingLsdhandle vendingLsdhandle) {
		return super.getSqlSession().update("VendingLsdhandle.insert", vendingLsdhandle);
	}

}
