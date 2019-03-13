package com.lmxf.post.service.core;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;


import org.apache.commons.putils.interceptor.OperationDescription;
import org.apache.commons.putils.service.ServiceException;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lmxf.post.core.utils.Constants;
import com.lmxf.post.core.utils.CacheUtils;

import org.apache.commons.putils.web.Page;

import com.lmxf.post.entity.config.ErrorCode;
import com.lmxf.post.entity.parameter.Dict;
import com.lmxf.post.repository.config.ErrorCodeDao;
import com.lmxf.post.repository.parameter.DictDao;

/**
 * 数据字典业务管理层
 * 
 * @author jitiali
 * 
 */
public class DictService {

	private DictDao dictDao;
	private static Logger log = Logger.getLogger(DictService.class);
	private ErrorCodeDao errorCodeDao;

	public void setDictDao(DictDao dictDao) {
		this.dictDao = dictDao;
	}

	public void setErrorCodeDao(ErrorCodeDao errorCodeDao) {
		this.errorCodeDao = errorCodeDao;
	}

	public List<Dict> findAll(Page pager) {
		List<Dict> dList = null;
		try {
			dList = dictDao.findAll(pager);
		} catch (SQLException e) {
			log.error(e.getMessage());
			throw new ServiceException("获取用户信息出错！", e);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ServiceException("获取用户信息出错！", e);
		}
		return dList;
	}

	public List<Dict> findAll() {
		List<Dict> dList = null;
		dList = dictDao.findAll();
		return dList;
	}

	public Dict findOne(String logid) {
		Dict dict = null;
		try {
			dict = dictDao.findOne(logid);
		} catch (SQLException e) {
			log.error(e.getMessage());
			throw new ServiceException("获取用户信息出错！请检查数据库连接是否正确", e);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ServiceException("获取用户信息出错！请检查数据库连接是否正确", e);
		}
		return dict;
	}


	@OperationDescription(type = Constants.LOG_UPDATE, entityType = "Dict", description = "修改数据字典")
	public int update(Dict dict) {
		try {
			return dictDao.update(dict);
		} catch (SQLException e) {
			log.error(e.getMessage());
			throw new ServiceException("修改数据字典信息出错", e);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ServiceException("修改数据字典信息出错", e);
		}
	}

	@OperationDescription(type = Constants.LOG_DELETE, entityType = "Dict", description = "删除数据字典")
	public int delete(String[] logids) {
		int count = 0;
		try {
			for (int i = 0; i < logids.length; i++) {
				count = dictDao.delete(logids[i]);
			}
		} catch (SQLException e) {
			log.error(e.getMessage());
			throw new ServiceException("删除数据字典信息出错", e);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ServiceException("删除数据字典信息出错", e);
		}
		return count;
	}

	
	
	
	/**
	 * 数据字典刷新
	 * 
	 * @throws CloneNotSupportedException
	 */
	public void refresh() throws CloneNotSupportedException {
		
	}
}
