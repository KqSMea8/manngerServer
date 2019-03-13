package com.lmxf.post.repository.statement;

import java.util.List;

import org.apache.commons.putils.repository.BaseDao;

import com.lmxf.post.entity.statement.StatementSupply;

public class StatementSupplyDao extends BaseDao {
	
	public List<StatementSupply> findByInfo(StatementSupply statementSupply) {
		List<StatementSupply> list = this.getSqlSession().selectList("StatementSupply.findByInfo",statementSupply);
		return list;
	}
	
	public int insert(StatementSupply statementSupply) {
		return super.getSqlSession().insert("StatementSupply.insert", statementSupply);
	}
	
	public int update(StatementSupply statementSupply) {
		return super.getSqlSession().update("StatementSupply.update", statementSupply);
	}
	
	public StatementSupply findOne(StatementSupply statementSupply) {
		StatementSupply statementSupplyR = super.getSqlSession().selectOne("StatementSupply.findOne", statementSupply);
		return statementSupplyR;
	}
	
	public int updateUnder(StatementSupply statementSupply) {
		return super.getSqlSession().update("StatementSupply.updateUnder", statementSupply);
	}
	
	
}
