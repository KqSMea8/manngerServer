package com.lmxf.post.repository.vending;

import org.apache.commons.putils.repository.BaseDao;

import com.lmxf.post.entity.vending.VendingCmd;

public class VendingCmdDao extends BaseDao {
	
	public VendingCmd findOne(VendingCmd vendingCmd) {
		VendingCmd vendingCmdR = super.getSqlSession().selectOne("VendingCmd.findOne", vendingCmd);
		return vendingCmdR;
	}
	
	public int insert(VendingCmd vendingCmd) {
		return super.getSqlSession().update("VendingCmd.insert", vendingCmd);
	}
	public int update(VendingCmd vendingCmd) {
		return super.getSqlSession().update("VendingCmd.update", vendingCmd);
	}
	public VendingCmd findByCmdId(String cmdId) {
		return this.getSqlSession().selectOne("VendingCmd.findByCmdId", cmdId);
	}
	public VendingCmd findOneByObject(VendingCmd vendingCmd) {
		return this.getSqlSession().selectOne("VendingCmd.findOneByObject", vendingCmd);
	}
	
}
