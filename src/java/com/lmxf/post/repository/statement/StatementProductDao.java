package com.lmxf.post.repository.statement;

import java.util.List;

import org.apache.commons.putils.repository.BaseDao;

import com.lmxf.post.entity.statement.StatementProduct;

public class StatementProductDao extends BaseDao {
	
	public List<StatementProduct> findAll(StatementProduct statementProduct) {
		List<StatementProduct> list = this.getSqlSession().selectList("StatementProduct.findAll",statementProduct);
		return list;
	}

	
	public int insert(StatementProduct statementProduct) {
		return super.getSqlSession().insert("StatementProduct.insert", statementProduct);
	}
	
	public StatementProduct findOne(StatementProduct statementProduct) {
		StatementProduct statementProductR = super.getSqlSession().selectOne("StatementProduct.findOne", statementProduct);
		return statementProductR;
	}
	
	public StatementProduct findOneOut(StatementProduct statementProduct) {
		StatementProduct statementProductR = super.getSqlSession().selectOne("StatementProduct.findOneOut", statementProduct);
		return statementProductR;
	}
	
	public int update(StatementProduct statementProduct) {
		return super.getSqlSession().update("StatementProduct.update", statementProduct);
	}
	
	public int updateOutProduct(StatementProduct statementProduct) {
		return super.getSqlSession().update("StatementProduct.updateOutProduct", statementProduct);
	}
	
	
}
