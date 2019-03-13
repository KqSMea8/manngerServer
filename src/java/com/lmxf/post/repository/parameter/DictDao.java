package com.lmxf.post.repository.parameter;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.putils.repository.BaseDao;
import org.apache.commons.putils.web.Page;
import com.lmxf.post.entity.parameter.Dict;

/**
 * 数据字典dao
 * 
 * @author jitiali
 * 
 */
public class DictDao extends BaseDao {

	@SuppressWarnings("unchecked")
	public List<Dict> findAll(Dict dict,Page pager) throws SQLException, Exception {
		return super.findDataPage("Dict.findAll", dict, pager);
	}
	
	@SuppressWarnings("unchecked")
	public List<Dict> findAll(Page pager) throws SQLException, Exception {
		return super.findDataPage("Dict.findAll", null, pager);
	}

	public List<Dict> findAll()  {
		return super.getSqlSession().selectList("Dict.findAll");
	}

	public Dict findOne(String logid) throws SQLException, Exception {
		return super.getSqlSession().selectOne("Dict.findOne", logid);
	}

	public Dict findOneByDictKey(String dict_key) throws SQLException, Exception {
		return super.getSqlSession().selectOne("Dict.findOneByDictKey", dict_key);
	}

	public int delete(String logid) throws SQLException, Exception {
		return super.getSqlSession().delete("Dict.delete", logid);
	}

	public int update(Dict dict) throws SQLException, Exception {
		return super.getSqlSession().update("Dict.update", dict);
	}

}
