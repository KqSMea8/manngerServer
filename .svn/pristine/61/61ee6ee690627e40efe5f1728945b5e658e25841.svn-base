package com.lmxf.post.repository.vending;

import com.lmxf.post.entity.vending.VendingLine;

import org.apache.commons.putils.repository.BaseDao;

public class VendingLineDao extends BaseDao {

	public VendingLine findByLineId(String lineId) {
		return this.getSqlSession().selectOne("VendingLine.findByLineId", lineId);
	}

}
