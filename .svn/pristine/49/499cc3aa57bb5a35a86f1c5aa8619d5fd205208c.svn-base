package com.lmxf.post.repository.increment;

import java.util.List;

import org.apache.commons.putils.repository.BaseDao;
import org.apache.commons.putils.web.Page;

import com.lmxf.post.entity.increment.AdvertConfig;

public class AdvertConfigDao extends BaseDao {

	public AdvertConfig findOne(AdvertConfig advertConfig) {
		return super.getSqlSession().selectOne("AdvertConfig.findOne", advertConfig);
	}

	public List<AdvertConfig> findAll(AdvertConfig advertConfig, Page page) {
		return super.findDataPage("AdvertConfig.findAll", advertConfig, page);
	}

	public List<AdvertConfig> findAll(AdvertConfig advertConfig) {
		return super.getSqlSession().selectList("AdvertConfig.findAll", advertConfig);
	}
}
