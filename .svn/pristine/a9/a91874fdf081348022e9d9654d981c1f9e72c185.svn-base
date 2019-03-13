package com.lmxf.post.repository.increment;

import java.util.List;

import org.apache.commons.putils.repository.BaseDao;
import org.apache.commons.putils.web.Page;

import com.lmxf.post.entity.increment.AdvertMStrategy;

public class AdvertMStrategyDao extends BaseDao {

	public AdvertMStrategy findOne(AdvertMStrategy advertMStrategy) {
		return super.getSqlSession().selectOne("AdvertMStrategy.findOne", advertMStrategy);
	}

	public List<AdvertMStrategy> findAll(AdvertMStrategy advertMStrategy, Page page) {
		return super.findDataPage("AdvertMStrategy.findAll", advertMStrategy, page);
	}

	public List<AdvertMStrategy> findAll(AdvertMStrategy advertMStrategy) {
		return super.getSqlSession().selectList("AdvertMStrategy.findAll", advertMStrategy);
	}
}
