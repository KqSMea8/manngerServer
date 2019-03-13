package com.lmxf.post.repository.emp;

import java.util.List;

import org.apache.commons.putils.repository.BaseDao;

import com.lmxf.post.entity.emp.Emp;

public class EmpDao extends BaseDao {

	public List<Emp> findAll(Emp emp){
		List<Emp> list = this.getSqlSession().selectList("Emp.findAll",emp);
		return list;
	}
	
	public Emp findOne(Emp emp) {
		return super.getSqlSession().selectOne("Emp.findOne", emp);
	}
	
	public Emp findEmp(Emp emp) {
		return super.getSqlSession().selectOne("Emp.findEmp", emp);
	}
	public int updateAccessCode(Emp emp) {
		return super.getSqlSession().update("Emp.updateAccessCode", emp);
	}
	
	public int insert(Emp emp) {
		return super.getSqlSession().insert("Emp.insert", emp);
	}
	public int update(Emp emp) {
		return super.getSqlSession().update("Emp.update", emp);
	}
	public int delete(Emp emp) {
		return super.getSqlSession().delete("Emp.delete", emp);
	}
}
