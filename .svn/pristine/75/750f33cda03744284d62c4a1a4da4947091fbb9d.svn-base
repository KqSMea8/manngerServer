package com.lmxf.post.repository.increment;

import java.util.List;

import org.apache.commons.putils.repository.BaseDao;
import org.apache.commons.putils.web.Page;

import com.lmxf.post.entity.increment.AdvertDevice;

public class AdvertDeviceDao extends BaseDao {

	public AdvertDevice findOne(AdvertDevice advertDevice) {
		return super.getSqlSession().selectOne("AdvertDevice.findOne", advertDevice);
	}

	public List<AdvertDevice> findAll(AdvertDevice advertDevice, Page page) {
		return super.findDataPage("AdvertDevice.findAll", advertDevice, page);
	}

	public List<AdvertDevice> findAll(AdvertDevice advertDevice) {
		return super.getSqlSession().selectList("AdvertDevice.findAll", advertDevice);
	}
}
