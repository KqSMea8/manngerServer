package com.lmxf.post.repository.config;
import com.lmxf.post.entity.config.Corp;

import org.apache.commons.putils.repository.BaseDao;

public class CorpDao extends BaseDao{
	
	public Corp findOne(String corpId){
		Corp corp = this.getSqlSession().selectOne("Corp.findOne", corpId);
		return corp;
	}

}
