package com.lmxf.post.repository.increment;

import java.util.List;

import org.apache.commons.putils.repository.BaseDao;
import org.apache.commons.putils.web.Page;

import com.lmxf.post.entity.increment.AdvertStrategy;

public class AdvertStrategyDao extends BaseDao {

	public AdvertStrategy findOne(AdvertStrategy advertStrategy) {
		return super.getSqlSession().selectOne("AdvertStrategy.findOne", advertStrategy);
	}

	public List<AdvertStrategy> findAll(AdvertStrategy advertStrategy, Page page) {
		return super.findDataPage("AdvertStrategy.findAll", advertStrategy, page);
	}

	public List<AdvertStrategy> findAll(AdvertStrategy advertStrategy) {
		return super.getSqlSession().selectList("AdvertStrategy.findAll", advertStrategy);
	}
}
