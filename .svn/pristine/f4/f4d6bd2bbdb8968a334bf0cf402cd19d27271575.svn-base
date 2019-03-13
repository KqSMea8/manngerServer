package com.lmxf.post.repository.parameter;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.putils.repository.BaseDao;
import org.apache.commons.putils.web.Page;

import com.lmxf.post.entity.parameter.NsParameter;

public class NsParameterDao extends BaseDao {
	public int update(NsParameter obj) throws SQLException, Exception {
		int count = super.getSqlSession().update("NsParameter.update", obj);
		return count;
	}

	public List<NsParameter> findOrderUsage(NsParameter np) {
		return super.getSqlSession().selectList("NsParameter.findOrderUsage", np);
	}
	
	public NsParameter findOne(NsParameter np) {
		return super.getSqlSession().selectOne("NsParameter.findOne", np);
	}

	public int updateForOrderUsage(NsParameter np) {
		return super.getSqlSession().update("NsParameter.updateForOrderUsage", np);
	}

	public List<NsParameter> findDataPage(NsParameter nsParameter, Page page) {
		return super.findDataPage("NsParameter.findDataPage", nsParameter, page);
	}
}
