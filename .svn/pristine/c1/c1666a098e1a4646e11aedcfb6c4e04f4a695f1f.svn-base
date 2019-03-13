package com.lmxf.post.repository.emp;


import java.util.List;

import org.apache.commons.putils.repository.BaseDao;
import org.apache.commons.putils.web.Page;

import com.lmxf.post.entity.emp.User;


public class UserDao extends BaseDao {

	public User findOne(User User) {
		return super.getSqlSession().selectOne("User.findOne", User);
	}
	public List<User> findAll(User User,Page page) {
		return super.findDataPage("User.findAll",User,page);
	}
	
}
