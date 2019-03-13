package com.lmxf.post.repository.vending;

import com.lmxf.post.entity.vending.VendingDistrict;

import org.apache.commons.putils.repository.BaseDao;

public class VendingDistrictDao extends BaseDao {

	public VendingDistrict findByDistrictId(String districtId) {
		return this.getSqlSession().selectOne("VendingDistrict.findByDistrictId", districtId);
	}

}
